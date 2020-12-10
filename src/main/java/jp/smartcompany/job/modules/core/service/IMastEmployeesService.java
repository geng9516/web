package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.admin.usermanager.dto.PersonalInfoDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.framework.auth.entity.LoginControlEntity;
import jp.smartcompany.framework.compatible.entity.V3CompatiblePostEntity;
import jp.smartcompany.framework.component.dto.EmployInfoSearchDTO;
import jp.smartcompany.framework.component.entity.EmployeeInfoSearchEntity;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmployMentWithMEVo;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 基本情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastEmployeesService extends IService<MastEmployeesDO> {

    /**
     * MAST_EMPLOYEESの採用日を取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 職員番号
     * @param yyyymmdd   基準日
     * @return Date 採用日
     */
    Date selectBegindateWork(String customerId, String companyId, String employeeId, Date yyyymmdd);

    List<PaidHolidayInitVO> listPaidHolidayInit(String empSql);

    List<String> selectUserIdList(String psCustid,
                                  String psCompid,
                                  String psLoginUserId,
                                  Date psDate);

    List<MastEmployeesDO> selectEmployByLoginUserId(String psCustid,
                                  String psCompid,
                                  String psLoginUserId,
                                  String psDate);

    /**
     * サイトIDを判定し更新対象の職員番号
     *
     * @param siteId   サイトID
     * @param yyyymmdd 　対象日
     * @param empsql   　対象職員取得SQL
     * @param empIds   　画面チェックした職員番号リスト
     * @return
     */
    List<String> selectEmpIdListForTmgDaily(String siteId, String yyyymmdd, String empsql, String[] empIds);

    int selectRelation(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                       String sDate);

    int selectRelationEx(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                                String sDate, String psBase1, String psBase2, String psBase3,
                                String psBase4, String psBase5, String psBase6,
                                String psBase7, String psBase8);

    List<LoginControlEntity> selectUserInfoByDate(String loginUser,String language,String psDate);

    int selectOrgRelation(String sCust, String sLoginUser,String sTargetComp,
                          String sTargetSec,String sSystem,String non,String sDate);

    List<V3CompatiblePostEntity> getVersion3SectionChief(
            String sCustid,
            String sCompid,
            String sDeptid,
            String sDate,
            String sPostid,
            boolean bIncludeactual
    );


    /**
    * 発令上の勤務開始日取得用SQL取得メソッド
    */
    EmployMentWithMEVo selectDateofemploymentWithME(String custId,String compId,String empId);

    List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDList(EmployInfoSearchDTO searchDTO);

    List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDListAdd(EmployInfoSearchDTO searchDTO);

    List<EmployeeInfoSearchEntity> selectEmployeeInfoList(
            String searchDate,
            String language,
            String designation,
            String sEmpInfoUserIDList,
            String sCompNick,
            String sSectionNick,
            String sPostNick,
            String loginUser,
            String systemId
    );


    /**
     * ===================用户管理 搜索用户开始===============
     */
    IPage<UserManagerListDTO> selectMainAllList(IPage<UserManagerListDTO> page, String custId, String language, String companyId, List<String> companyList);

    IPage<UserManagerListDTO> selectMainLockoutList(IPage<UserManagerListDTO> page, String custId, String language, String companyId, List<String> companyList);

    IPage<UserManagerListDTO> selectMainAdminList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList);

    IPage<UserManagerListDTO> selectMainEmpNameList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList,String empName);

    IPage<UserManagerListDTO> selectMainSectionList(IPage<UserManagerListDTO> page,String custId,String language,List<String> companyList,
                                                    String sectionCompanyId,String sectionId);

    IPage<UserManagerListDTO> selectMainEmpIdList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList,String empId);

    IPage<UserManagerListDTO> selectMainAfterRetireList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList);

    IPage<UserManagerListDTO> selectMainAfterJoinList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList);

    IPage<UserManagerListDTO> selectMainBeforeJoinList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList);

    IPage<UserManagerListDTO> selectMainValidList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList);

    List<UserManagerDTO> selectStartList(String custId, List<String> userIds,
                                         String language, Integer searchType,
                                         List<String> companyList);

    List<UserManagerDTO> selectEndList(String custId, List<String> userIds,
                                         String language, Integer searchType,
                                         List<String> companyList);

    UserManagerDTO selectPersonalName(String custId, String userId, String language,  List<String> companyList);

    PersonalInfoDTO selectPersonalInfo(Date baseDate,String custId,String language,String userId);
    /**
     * ===================用户管理 搜索用户结束===============
     */

    IPage<MastEmployeesDO> selectInvalidEmailEmpList(IPage<MastEmployeesDO> page);

    IPage<UserManagerListDTO> searchEmpForUpdateMail(IPage<UserManagerListDTO> page,String keyword);


    Optional<MastEmployeesDO> getEmployInfo(String empId);
}
