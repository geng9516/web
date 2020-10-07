package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.admin.usermanager.form.UserManagerEditStartForm;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditStartLogic;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.util.PsConst;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userManagerEditStartLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerEditStartLogicImpl implements UserManagerEditStartLogic {

   private final ScCacheUtil cacheUtil;
   private final IMastAccountService mastAccountService;
   private final UserManagerEditCommonLogic userManagerEditCommonLogic;
   private final PsSearchCompanyUtil searchCompanyUtil;
   private final IMastEmployeesService mastEmployeesService;

    @Override
    public Map<String,Object> showChangeStartDate(ShowLimitDateForm form) {
        List<UserManagerDTO> list = searchForStartDate("01",form.getUserIds(),"ja",form.getSearchType());
        return MapUtil.<String,Object>builder()
                .put("list",list)
                .put(UserManagerEditCommonLogic.PROP_PW_MAX_LEN,cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN))
                .put(UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT,cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT))
                .put(UserManagerEditCommonLogic.PROP_PW_MIN_LEN,cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MIN_LEN))
                .put("defaultStartDate", SysUtil.transDateToString(DateUtil.date())).build();
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public Map<String,String> changeStartDate(UserManagerEditStartForm form) {
        Date startDate;
        Date dateNow = DateUtil.date();
        Date endDate = SysUtil.transStringToDate(PsConst.MAXDATE);
        String customerId = "01";
        List<UserManagerDTO> checkListOld;
        String currentUserId = SecurityUtil.getUserId();
        //------------------------------------------------------------------------------
        // 利用開始日更新(「入社日を設定する」のときに一括更新できないので１件ずつ更新)
        //------------------------------------------------------------------------------
        List<UserManagerDTO> originalList = form.getOriginalUserList();
        for (UserManagerDTO userManagerDTO : originalList) {
            String sUserid  =  userManagerDTO.getMeCuserid();
            String sAccount =  userManagerDTO.getMeCcompanyid() + "_" +  userManagerDTO.getMeCemployeeidCk();
            //========================================================
            //既存アカウントチェック(1)
            //========================================================
            //アカウント発行区分 == true かつアカウント未発行
            if (userManagerDTO.getMaId() == null) {
                List<UserManagerDTO> checkList =
                        mastAccountService.selectStartCheckAccount(
                                customerId,
                                sUserid,
                                sAccount);
                if (CollUtil.isNotEmpty(checkList)) {
                    String name = userManagerDTO.getMeCname();
                    throw new GlobalException(UserManagerEditCommonLogic.MESSAGE_ID_DUPLICATE_AUTO+"【"+name+":"+sAccount+"】");
                }
            }
            //=========================================================
            //過去アカウントチェック(1)
            //=========================================================
            checkListOld = mastAccountService.selectPersonalCheckAccountOld(customerId, sAccount);
            //========================================================
            //アカウント発行処理(1)-2
            //========================================================
            if (form.getUseEntranceDate()) {
                //「入社日を設定する」のときは入社日を設定する
                if (userManagerDTO.getMeDdateofemployement() == null) {
                    //入社日がnullのときはデータ開始日を設定する
                    startDate = userManagerDTO.getMeDstartdate();
                } else {
                    startDate = userManagerDTO.getMeDdateofemployement();
                }
            } else {
                //利用開始日入力のときは入力値を設定する
                startDate = form.getStartDate();
            }
            //=========================================================
            //過去アカウントチェック用(2)
            //=========================================================
            UserManagerDTO dto = mastAccountService.selectPersonalCheckUserid(sUserid);
            //------------------------------------------------------
            //アカウント追加
            //------------------------------------------------------
            MastAccountDO accountDto = new MastAccountDO();
            accountDto.setMaId(userManagerDTO.getMaId());
            accountDto.setMaDstart(startDate);
            accountDto.setMaDend(endDate);
            accountDto.setMaNretrycounter(0);
            accountDto.setMaNpasswordlock(0);
            accountDto.setMaCadminuser("0");
            accountDto.setMaCmodifieruserid(currentUserId);
            accountDto.setMaDmodifieddate(dateNow);
            // 過去アカウントが存在した場合は新たに登録する
            if (userManagerDTO.getMaId() == null || CollUtil.isNotEmpty(checkListOld)) {
                // ユーザＩＤチェック(一件も取得出来ない場合)
                if (dto == null){
                    // Insert処理
                    userManagerEditCommonLogic.accountInsert(accountDto, checkListOld, customerId, sAccount, sUserid,dateNow);
                } else {
                    //過去に登録されていたデータかチェック
                    if (dto.getMaId() == null){
                        // Insert処理
                        userManagerEditCommonLogic.accountInsert(accountDto, checkListOld, customerId, sAccount, sUserid,dateNow);
                    } else {
                        mastAccountService.updateById(accountDto);
                    }
                }
            } else {
                mastAccountService.updateById(accountDto);
            }
        }
        //------------------------------------------------------
        //パスワード更新
        //------------------------------------------------------
        Map<String, String> passwordMap = MapUtil.newHashMap();
        Integer passwordType = form.getPasswordType();
        if (passwordType != null
                && ( passwordType.equals(2)
                || ( passwordType.equals(1)
                &&  StrUtil.isNotBlank(form.getPassword())))) {
            passwordMap = userManagerEditCommonLogic.updatePassword(
                    customerId,
                    currentUserId, "ja"
                    , form.getUserIds()
                    , passwordType
                    , form.getPassword()
                    , form.getForceChangePassword());
        }
        return passwordMap;
    }

    /**
     * 検索.
     * @param sCustomerid 顧客コード
     * @param userIds ユーザID
     * @param sLanguage 言語
     * @param searchType 絞り込み条件
     * @return  UserManagerEditStartDto
     */
    private List<UserManagerDTO> searchForStartDate(
            String sCustomerid, List<String> userIds, String sLanguage, int searchType) {
        // 法人検索対象範囲取得
        List<String> lComps = searchCompanyUtil.getCompList(DateUtil.date());
        List<UserManagerDTO> userManagerDtoList = mastEmployeesService.selectStartList(
                sCustomerid,
                userIds,
                sLanguage,
                searchType,
                lComps);

        for (UserManagerDTO dto : userManagerDtoList) {
            // 表示用：MAXDATEを空にする
            if (dto.getMaDend() != null && StrUtil.equals(PsConst.MAXDATE,SysUtil.transDateToString(dto.getMaDend()))) {
                dto.setMaDend(null);
            }
            String sStatus = userManagerEditCommonLogic.getStatus(dto);
            dto.setStatus(sStatus);
        }
        return userManagerDtoList;
    }

}
