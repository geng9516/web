package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerEditEndDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.util.PageQuery;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userManagerMainLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerMainLogicImpl implements UserManagerMainLogic {

    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastEmployeesService mastEmployeesService;
    private final IMastAccountService mastAccountService;
    private final IConfSyscontrolService confSyscontrolService;

    /**
     * 法人リスト取得
     * @param conditions 検索条件
     * @return PageUtil 検索結果
     */
    @Override
     public PageUtil search(Map<String,Object> conditions) {
         Date date = DateUtil.date();
         String custId = "01";
         String language = "ja";
         List<String> companyList = searchCompanyUtil.getCompList(date);

         // 设置默认查询参数
         String sSearchType = (String)conditions.get("searchType");
         String companyId = (String)conditions.get("companyId");
         String empId = (String)conditions.get("empId");
         String sectionId = (String)conditions.get("sectionId");
         String sectionCompanyId = (String)conditions.get("sectionCompanyId");
         String empName = (String)conditions.get("empName");
         int searchType = 0;
         if (StrUtil.isNotBlank(sSearchType)) {
            searchType = Integer.parseInt(sSearchType);
         }
         if (StrUtil.isBlank(companyId)) {
             companyId = "01";
         }

         IPage<UserManagerListDTO> pageResult;
         IPage<UserManagerListDTO> pageQuery = new PageQuery<UserManagerListDTO>().getPage(conditions);
         switch(searchType) {
             case 1: // 全て
                 pageResult = mastEmployeesService.selectMainAllList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 2: // ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 3: // 現在有効なユーザ
                 pageResult = mastEmployeesService.selectMainValidList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 4: // 入社前
                 pageResult = mastEmployeesService.selectMainBeforeJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 5: //入社後未登録
                 pageResult = mastEmployeesService.selectMainAfterJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 6: // 退職後未削除
                 pageResult = mastEmployeesService.selectMainAfterRetireList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 7: // 社員番号
                 pageResult = mastEmployeesService.selectMainEmpIdList(pageQuery,custId,language,companyId,companyList,empId);
                 break;
             case 8: // 部署
                 pageResult = mastEmployeesService.selectMainSectionList(pageQuery,custId,language,companyList,sectionCompanyId,sectionId);
                 break;
             case 9: // 氏名
                 pageResult = mastEmployeesService.selectMainEmpNameList(pageQuery,custId,language,companyId,companyList,empName);
                 break;
             case 10: // 管理ツールユーザ
                 pageResult = mastEmployeesService.selectMainAdminList(pageQuery,custId,language,companyId,companyList);
             default: // 默认查询ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
         }
         return new PageUtil(pageResult);
     }

     @Override
     @Transactional
     public void unLock(List<String> userIds) {
        List<MastAccountDO> accountList = mastAccountService.list(new QueryWrapper<MastAccountDO>().in("MA_CUSERID",userIds));
        String userId = SecurityUtil.getUserId();
        Date date = DateUtil.date();
        accountList.forEach(account-> {
            account.setMaNpasswordlock(0);
            account.setMaNretrycounter(0);
            account.setMaCmodifieruserid(userId);
            account.setMaDmodifieddate(date);
        });
        mastAccountService.updateBatchById(accountList);
    }


    // 定数：有効期間（日数）
    private static final String PROP_PW_VALID_PERIOD = "PasswordValidPeriod";
    // 定数：最小文字数
    private static final String PROP_PW_MIN_LEN = "PASSWORD_MIN_LEN";
    // 定数：最大文字数
    private static final String PROP_PW_MAX_LEN = "PASSWORD_MAX_LEN";
    // 定数：最大リトライ回数
    private static final String PROP_LOGIN_RETRY = "LoginRetry";
    // 定数：再利用禁止回数
    private static final String PROP_CHANGE_PW_LIMITATION_COUNT = "ChangePasswordLimitationCount";

    @Override
    public List<ConfSyscontrolDO> passwordPolicy() {
        List<String> props = CollUtil.newArrayList(PROP_PW_VALID_PERIOD,PROP_PW_MAX_LEN,PROP_PW_MIN_LEN,PROP_LOGIN_RETRY,PROP_CHANGE_PW_LIMITATION_COUNT);
        return confSyscontrolService.list(new QueryWrapper<ConfSyscontrolDO>().in("CS_CPROPERTYNAME", props).orderByDesc("CS_CPROPERTYNAME"));
    }

    @Override
    @Transactional
    public void updatePolicy(List<ConfSyscontrolDO> controlList) {
        confSyscontrolService.updateBatchById(controlList);
    }

    @Override
    public void updateEndDate(UserManagerEditEndDTO dto) {
        List<UserManagerListDTO> accountDTOList = dto.getList();
        String userId = SecurityUtil.getUserId();
        Date date = DateUtil.date();
        accountDTOList.forEach(account -> {
//            account.setMaCmodifieruserid(userId);
//            account.setMaDmodifieddate(date);
//            if (StrUtil.isNotBlank(account.getMaCuserid())) {
//                //「退職日を設定する」のとき
//               if (dto.getUseRetireDate()) {
//                   if (account.getMeDdateofretirement() == null) {
//                       // 退職日がNULLのときはそのまま
//                       if (userManagerEditEndDto.getEndDate() != null) {
//                           accountDto.setMaDend(accountDto.getMaDend());
//                       } else {
//                           // 入力値がなかったらシステム日付
//                           accountDto.setMaDend(new Timestamp(new Date().getTime()));
//                       }
//                   } else {
//                       // 退職日を設定
//                       accountDto.setMaDend(dto.getMeDdateofretirement());
//                   }
//               } else {
//
//               }
//            }
        });
    }



    /**
     * 初期値表示日付取得
     */
    public Date getDefaultDate() {
        // 利用終了日(初期値設定)
        Calendar cal = (Calendar) Calendar.getInstance().clone();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new Timestamp(cal.getTime().getTime());
    }

}
