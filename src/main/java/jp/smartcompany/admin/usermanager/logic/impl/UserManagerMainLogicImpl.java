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
import jp.smartcompany.admin.usermanager.form.UserManagerEditStartForm;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.*;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.modules.core.util.PsConst;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userManagerMainLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerMainLogicImpl implements UserManagerMainLogic {

    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastEmployeesService mastEmployeesService;
    private final IMastAccountService mastAccountService;
    private final IConfSyscontrolService confSyscontrolService;
    private final ScCacheUtil cacheUtil;
    private final UserManagerEditCommonLogic userManagerEditCommonLogic;
    private final IMastPasswordService passwordService;

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
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=1
             case 1: // 全て
                 pageResult = mastEmployeesService.selectMainAllList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=2
             case 2: // ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=3
             case 3: // 現在有効なユーザ
                 pageResult = mastEmployeesService.selectMainValidList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=4
             case 4: // 入社前
                 pageResult = mastEmployeesService.selectMainBeforeJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=5&companyId=01
             case 5: //入社後未登録
                 pageResult = mastEmployeesService.selectMainAfterJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=6&companyId=01
             case 6: // 退職後未削除
                 pageResult = mastEmployeesService.selectMainAfterRetireList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=7&companyId=01&empId=4640
             case 7: // 社員番号
                 pageResult = mastEmployeesService.selectMainEmpIdList(pageQuery,custId,language,companyId,companyList,empId);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=8&sectionCompanyId=01&sectionId=201000000000
             case 8: // 部署
                 pageResult = mastEmployeesService.selectMainSectionList(pageQuery,custId,language,companyList,sectionCompanyId,sectionId);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=9&companyId=01&empName=464
             case 9: // 氏名
                 pageResult = mastEmployeesService.selectMainEmpNameList(pageQuery,custId,language,companyId,companyList,empName);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=10&companyId=01
             case 10: // 管理ツールユーザ
                 pageResult = mastEmployeesService.selectMainAdminList(pageQuery,custId,language,companyId,companyList);
             default: // 默认查询ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
         }
         pageResult.getRecords().forEach(item -> {
             item.setStatus(getStatus(item));
         });
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

    @Override
    public List<ConfSyscontrolDO> passwordPolicy() {
        List<String> props = CollUtil.newArrayList(UserManagerEditCommonLogic.PROP_PW_VALID_PERIOD,
                UserManagerEditCommonLogic.PROP_PW_MAX_LEN,
                UserManagerEditCommonLogic.PROP_PW_MIN_LEN,
                UserManagerEditCommonLogic.PROP_LOGIN_RETRY,
                UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT);
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
                    accountInsert(accountDto, checkListOld, customerId, sAccount, sUserid,dateNow);
                } else {
                    //過去に登録されていたデータかチェック
                    if (dto.getMaId() == null){
                        // Insert処理
                        accountInsert(accountDto, checkListOld, customerId, sAccount, sUserid,dateNow);
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
     * Account登録処理
     * @param accountDto
     * @param checkListOld
     * @param psCustomerid
     * @param psAccount
     * @param psUserid
     * @param now
     */
    public void accountInsert(
            MastAccountDO accountDto,
            List<UserManagerDTO> checkListOld,
            String psCustomerid,
            String psAccount,
            String psUserid,
            Date now){

        //=========================================================
        //過去アカウント削除(1)
        //=========================================================
        if(CollUtil.isNotEmpty(checkListOld)){
            // アカウント削除
            Map<String,Object> accountParams = MapUtil.newHashMap();
            accountParams.put("MA_CCUSTOMERID",psCustomerid);
            accountParams.put("MA_CACCOUNT",psAccount);
            mastAccountService.removeByMap(accountParams);
            // パスワード削除
           Map<String,Object> passwordParams = MapUtil.newHashMap();
           passwordParams.put("accountInsert",checkListOld.get(0).getMaCuserid());
           passwordService.removeByMap(passwordParams);
        }
        //=========================================================
        //登録前処理
        //=========================================================
        accountDto.setMaCcustomerid(psCustomerid);
        accountDto.setMaCaccount(psAccount);
        accountDto.setMaCuserid(psUserid);
        accountDto.setMaDcreate(now);
        //=========================================================
        //登録処理
        //=========================================================
        mastAccountService.save(accountDto);
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
            String sStatus = getStatus(dto);
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
            String sStatus = getStatus(dto);
            dto.setStatus(sStatus);
        }
        return userManagerDtoList;
    }

    /**
     * ステータス取得
     * @param poUserManagerDto ユーザDtoデータ
     * @return sValue
     */
    private String getStatus(UserManagerListDTO poUserManagerDto) {
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
