package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.usermanager.dto.PersonalInfoDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.form.UserManagerEditPersonalForm;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditPersonalLogic;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.List;

@Service("userManagerEditPersonalLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerEditPersonalLogicImpl implements UserManagerEditPersonalLogic {

    private final IMastAccountService accountService;
    private final IMastEmployeesService employeesService;
    private final IMastPasswordService passwordService;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final UserManagerEditCommonLogic userManagerEditCommonLogic;
    private final ScCacheUtil cacheUtil;
    private String passwordMinLen;
    private String passwordMaxLen;
    private String changeLimitCount;


    @Override
    public Map<String,Object> display(String userId) {
         Date baseDate = DateUtil.date();
         PersonalInfoDTO accountDetails = employeesService.selectPersonalInfo(baseDate,"01","ja",userId);
         MastAccountDO account = accountService.getByUsername(userId);
         // 表示用：MAXDATEを空にする
         if (account.getMaDend() != null
                && DateUtil.isSameDay(SysUtil.getMaxDateObject(),account.getMaDend())) {
            account.setMaDend(null);
         }
         passwordMinLen = cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MIN_LEN);
         passwordMaxLen = cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN);
         changeLimitCount = cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT);
         int count= employeesService.count(new QueryWrapper<MastEmployeesDO>()
                 .eq("ME_CCUSTOMERID_CK","01")
                 .eq("ME_CUSERID",userId)
                 .le("ME_DSTARTDATE", baseDate)
                 .ge("ME_DSTARTDATE",baseDate));
         boolean isValid = count > 0;

         Map<String,Object> userInfoMap = MapUtil.newHashMap();
         userInfoMap.put("sectionName",accountDetails.getSectionName());
         userInfoMap.put("postName",accountDetails.getPostName());
         userInfoMap.put("kanaName",accountDetails.getKanaName());
         userInfoMap.put("empId",accountDetails.getEmployeeId());
         userInfoMap.put("kanjiName",accountDetails.getKanjiName());
         userInfoMap.put("isOut",accountDetails.getIsOut());
         //画面表示情報取得
         return MapUtil.<String,Object>builder().put("userInfo",userInfoMap)
                 .put("accountInfo",account)
                 .put("baseDate",SysUtil.transDateToString(baseDate))
                 .put("passwordMaxLen",Integer.parseInt(passwordMaxLen))
                 .put("passwordMinLen",Integer.parseInt(passwordMinLen))
                 .put("changeLimitCount",Integer.parseInt(changeLimitCount))
                 .put("isValid",isValid)
                 .build();
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public Map<String,Object> updatePersonal(UserManagerEditPersonalForm form) {
        passwordMaxLen = cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN);
        MastAccountDO accountDO = new MastAccountDO();
        BeanUtil.copyProperties(form.getAccount(),accountDO);
        String userId = form.getUserId();
        String customerId =  "01";
        String language = "ja";
        String accountId = accountDO.getMaCaccount();
        Date now = DateUtil.date();
        String loginUserId = SecurityUtil.getUserId();
        List<String> companyList =  searchCompanyUtil.getCompList(now);
        if (StrUtil.isNotBlank(accountId)) {
            //=========================================================
            //既存アカウントチェック(1)
            //=========================================================
            List<UserManagerDTO> checkList = accountService.selectStartCheckAccount(customerId,userId,accountId);
            if (CollUtil.isNotEmpty(checkList)) {
                UserManagerDTO userManagerDTO = employeesService.selectPersonalName(customerId,userId,language,companyList);
                throw new GlobalException("アカウントは既に存在します【"+userManagerDTO.getMeCname()+"】");
            }
            //------------------------------------------------------
            //アカウント更新用情報設定
            //------------------------------------------------------
            int iLock = 0;
            if (accountDO.getMaNpasswordlock() != null
                    && accountDO.getMaNpasswordlock() == 1) {
                iLock = 1;
            }
            //=========================================================
            //過去アカウントチェック(1)
            //=========================================================
            List<UserManagerDTO> checkListOld =accountService.selectPersonalCheckAccountOld(customerId, accountId);
            //=========================================================
            //過去アカウント削除(1)
            //=========================================================
            if (CollUtil.isNotEmpty(checkListOld)) {
                // アカウント削除
                Map<String,Object> accountParams = MapUtil.newHashMap();
                accountParams.put("MA_CCUSTOMERID",customerId);
                accountParams.put("MA_CACCOUNT",accountId);
                accountService.removeByMap(accountParams);
                // パスワード削除
                Map<String,Object> passwordParams = MapUtil.newHashMap();
                passwordParams.put("accountInsert",checkListOld.get(0).getMaCuserid());
                passwordService.removeByMap(passwordParams);
            }
            //=========================================================
            //過去アカウントチェック用(2)
            //=========================================================
            UserManagerDTO userManagerDTO = accountService.selectPersonalCheckUserid(userId);
            //=========================================================
            //アカウント追加(1)-2
            //=========================================================
            accountDO.setMaCaccount(accountId);
            if (accountDO.getMaDend() != null) {
                accountDO.setMaDend(accountDO.getMaDend());
            } else {
                accountDO.setMaDend(SysUtil.getMaxDateObject());
            }
            accountDO.setMaNretrycounter(0);
            accountDO.setMaNpasswordlock(iLock);
            accountDO.setMaCmodifieruserid(loginUserId);
            accountDO.setMaDmodifieddate(now);
            //アカウントチェック
            if (accountDO.getMaId() == null) {
                //アカウント未発行のユーザなら追加
                accountDO.setMaCcustomerid(customerId);
                accountDO.setMaCuserid(userId);
                // 管理ツールユーザフラグ：非管理ユーザ
                accountDO.setMaCadminuser("0");
                accountDO.setMaDcreate(now);
                // 	ユーザＩＤチェック(一件も取得出来ない場合)
                if (userManagerDTO == null) {
                    accountService.save(accountDO);
                } else {
                    accountDO.setMaId(userManagerDTO.getMaId());
                    accountService.updateById(accountDO);
                }
            } else {
                accountService.updateById(accountDO);
            }
        }
        //------------------------------------------------------
        //パスワード更新
        //------------------------------------------------------
        Map <String,Object> passwordMap = MapUtil.newHashMap();
        Integer passwordType = form.getPasswordType();
        String password = form.getPassword();
        if (passwordType==2 || (passwordType == 1 && StrUtil.isNotBlank(password))) {
            passwordMap = userManagerEditCommonLogic.updatePassword(customerId,loginUserId,language,CollUtil.newArrayList(userId),passwordType,password,form.getForceChangePassword());
        }
        return passwordMap;
    }

}
