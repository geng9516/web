package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.form.UserManagerEditEndForm;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.util.*;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.util.PsConst;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ScCacheUtil cacheUtil;

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
    public static final String PROP_PW_VALID_PERIOD = "PasswordValidPeriod";
    // 定数：最小文字数
    public static final String PROP_PW_MIN_LEN = "PASSWORD_MIN_LEN";
    // 定数：最大文字数
    public static final String PROP_PW_MAX_LEN = "PASSWORD_MAX_LEN";
    // 定数：最大リトライ回数
    public static final String PROP_LOGIN_RETRY = "LoginRetry";
    // 定数：再利用禁止回数
    public static final String PROP_CHANGE_PW_LIMITATION_COUNT = "ChangePasswordLimitationCount";

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
    public Map<String,Object> showChangeEndDate(ShowLimitDateForm form) {
        List<UserManagerDTO> list = searchForEndDate("01",form.getUserIds(),"ja",form.getSearchType());
        return MapUtil.<String,Object>builder()
                .put("defaultEndDate",getDefaultDate())
                .put("list",list)
                .build();
    }

    @Override
    @Transactional
    public void changeEndDate(UserManagerEditEndForm dto) {
        List<UserManagerListDTO> accountDTOList = dto.getList();
        String userId = SecurityUtil.getUserId();
        Date date = DateUtil.date();
        List<MastAccountDO> accountList = CollUtil.newArrayList();
        accountDTOList.forEach(accountDTO -> {
            MastAccountDO account = new MastAccountDO();
            account.setMaCmodifieruserid(userId);
            account.setMaDmodifieddate(date);

            if (StrUtil.isNotBlank(account.getMaCuserid())) {
                //「退職日を設定する」のとき
               if (dto.getUseRetireDate()) {
                   Date retirementDate = accountDTO.getMeDdateofretirement();
                   if (retirementDate == null) {
                       // 退職日がNULLのときはそのまま
                       if (accountDTO.getMaDend() != null) {
                           account.setMaDend(accountDTO.getMaDend());
                       } else {
                           // 入力値がなかったらシステム日付
                           account.setMaDend(dto.getEndDate());
                       }
                   } else {
                       // 退職日を設定
                       account.setMaDend(retirementDate);
                   }
               } else {
                   if (accountDTO.getMaDend() != null) {
                       account.setMaDend(accountDTO.getMaDend());
                   } else {
                       // 入力値がなかったらシステム日付
                       account.setMaDend(dto.getEndDate());
                   }
               }
                accountList.add(account);
            }
        });
        if (CollUtil.isNotEmpty(accountList)) {
            mastAccountService.updateBatchById(accountList);
        }
    }

    @Override
    public Map<String,Object> showChangeStartDate(ShowLimitDateForm form) {
        List<UserManagerDTO> list = searchForStartDate("01",form.getUserIds(),"ja",form.getSearchType());
        return MapUtil.<String,Object>builder()
                .put("list",list)
                .put(PROP_PW_MAX_LEN,cacheUtil.getSystemProperty(PROP_PW_MAX_LEN))
                .put(PROP_CHANGE_PW_LIMITATION_COUNT,cacheUtil.getSystemProperty(PROP_CHANGE_PW_LIMITATION_COUNT))
                .put(PROP_PW_MIN_LEN,cacheUtil.getSystemProperty(PROP_PW_MIN_LEN))
                .put("defaultStartDate", SysUtil.transDateToString(DateUtil.date())).build();
    }






    private List<UserManagerDTO> searchForEndDate(
            String sCustomerid, List<String> userIds, String sLanguage, int searchType) {
        // 法人検索対象範囲取得
        List<String> lComps = searchCompanyUtil.getCompList(DateUtil.date());
        List<UserManagerDTO> userManagerDtoList = mastEmployeesService.selectEndList(
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
            String sStatus = getSatus(dto);
            dto.setStatus(sStatus);
        }
        return userManagerDtoList;
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
            String sStatus = getSatus(dto);
            dto.setStatus(sStatus);
        }
        return userManagerDtoList;
    }

    /**
     * ステータス取得
     * @param poUserManagerDto ユーザDtoデータ
     * @return sValue
     */
    private String getSatus(UserManagerListDTO poUserManagerDto) {
        String sStatus = "";
        Calendar calSys = (Calendar) Calendar.getInstance().clone();
        calSys.set(Calendar.HOUR, 0);
        calSys.set(Calendar.MINUTE, 0);
        calSys.set(Calendar.SECOND, 0);
        Calendar calResult = (Calendar) Calendar.getInstance().clone();

        // 入社年月日がnullの時は基本情報のデータ開始日付を使用
        if (poUserManagerDto.getMeDdateofemployement() == null) {
            calResult.setTime(poUserManagerDto.getMeDstartdate());
        } else {
            calResult.setTime(poUserManagerDto.getMeDdateofemployement());
        }

        calResult.set(Calendar.HOUR, 0);
        calResult.set(Calendar.MINUTE, 0);
        calResult.set(Calendar.SECOND, 0);

        if (poUserManagerDto.getMaId() == null) {

            // アカウント情報なし
            if (calResult.compareTo(calSys) <= 0) {
                // 入社年月日<=システム日付のとき"入社後未登録"
                sStatus = STATUS_AFTER_ENTRANCE;
            } else if (calResult.compareTo(calSys) > 0) {
                // 入社年月日>システム日付のとき"入社前"(アカウント情報有無は無関係)
                sStatus = STATUS_BEFORE_ENTRANCE;
            }
        } else {

            // アカウント情報あり
            if (poUserManagerDto.getMeDdateofretirement() != null
                    && poUserManagerDto.getMeDdateofretirement().before(calSys.getTime())
                    && poUserManagerDto.getMaDend() != null
                    && poUserManagerDto.getMaDend().compareTo(calSys.getTime()) >= 0) {
                // 退職後未削除
                sStatus = STATUS_AFTER_RETIRE;
            } else if (poUserManagerDto.getMaDend() != null
                    && poUserManagerDto.getMaDend().before(calSys.getTime())) {
                // 無効
                sStatus = STATUS_INVALID;
            } else if (poUserManagerDto.getMaNpasswordlock() != null
                    && poUserManagerDto.getMaNpasswordlock() == 1) {
                // ロックアウト
                sStatus = STATUS_LOCKOUT;
            } else if (calResult.compareTo(calSys) > 0) {
                // 入社年月日>システム日付のとき"入社前"(アカウント情報有無は無関係)
                sStatus = STATUS_BEFORE_ENTRANCE;
            }
        }
        //スタータスがnullの場合
        if (StrUtil.isBlank(sStatus)) {
            return "";
        }
        return getStatusText(sStatus);
    }

    private String getStatusText(String status) {
        if(StrUtil.equals(STATUS_BEFORE_ENTRANCE,status)) {
            return STATUS_BEFORE_ENTRANCE_TEXT;
        }
        if (StrUtil.equals(STATUS_AFTER_ENTRANCE,status)) {
            return STATUS_AFTER_ENTRANCE_TEXT;
        }
        if (StrUtil.equals(STATUS_LOCKOUT,status)) {
            return STATUS_LOCKOUT_TEXT;
        }
        if (StrUtil.equals(STATUS_INVALID,status)) {
            return STATUS_INVALID_TEXT;
        }
        if (StrUtil.equals(STATUS_AFTER_RETIRE,status)) {
            return STATUS_AFTER_RETIRE_TEXT;
        }
        return "";
    }

    /**定数：ステータス　入社前*/
    private static final String STATUS_BEFORE_ENTRANCE = "STATUS_BEFORE_ENTRANCE";
    /**定数：ステータス　入社後未登録*/
    private static final String STATUS_AFTER_ENTRANCE = "STATUS_AFTER_ENTRANCE";
    /**定数：ステータス　ロックアウト*/
    private static final String STATUS_LOCKOUT = "STATUS_LOCKOUT";
    /**定数：ステータス　無効*/
    private static final String STATUS_INVALID = "STATUS_INVALID";
    /**定数：ステータス　退職後未削除*/
    private static final String STATUS_AFTER_RETIRE = "STATUS_AFTER_RETIRE";


    private final static String STATUS_AFTER_ENTRANCE_TEXT="入社後未登録";
    private final static String STATUS_AFTER_RETIRE_TEXT="退職後未削除";
    private final static String STATUS_BEFORE_ENTRANCE_TEXT="入社前";
    private final static String STATUS_INVALID_TEXT="無効";
    private final static String STATUS_LOCKOUT_TEXT="ロックアウト";

    /**
     * 前端实现，终了日比当前日期小一天
     * 初期値表示日付取得
     */
    private Date getDefaultDate() {
        // 利用終了日(初期値設定)
        Calendar cal = (Calendar) Calendar.getInstance().clone();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(cal.getTime().getTime());
    }

}
