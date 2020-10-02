package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.framework.auth.entity.LoginControlEntity;
import jp.smartcompany.framework.compatible.entity.V3CompatiblePostEntity;
import jp.smartcompany.framework.component.dto.EmployInfoSearchDTO;
import jp.smartcompany.framework.component.entity.EmployeeInfoSearchEntity;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmployMentWithMEVo;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基本情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastEmployeesMapper extends BaseMapper<MastEmployeesDO> {

    /**
     * 勤務日を取得
     *
     * @param map 検索条件
     * @return Date
     */
    Date selectBegindateWork(Map<String, Object> map);

    List<PaidHolidayInitVO> selectPaidHolidayInit(String empSql);

    /**
     * サイトIDを判定し更新対象の職員番号
     */
    List<String> selectEmpIdListForTmgDaily(Map<String, Object> map);

    int selectRelationEx(@Param("psCust") String sCust,
                     @Param("psLogin") String sLoginUser,
                     @Param("psTarget") String sTargetUser,
                     @Param("psSystem") String sSystem,
                     @Param("psDesig") String sDesig,
                     @Param("psDate") String sDate,
                     @Param("psBase1") String psBase1,@Param("psBase2") String psBase2,@Param("psBase3") String psBase3,
                     @Param("psBase4")String psBase4,@Param("psBase5") String psBase5,@Param("psBase6") String psBase6,
                     @Param("psBase7")String psBase7,@Param("psBase8") String psBase8);

    int selectRelation(@Param("psCust") String sCust,
                       @Param("psLogin") String sLoginUser,
                       @Param("psTarget") String sTargetUser,
                       @Param("psSystem") String sSystem,
                       @Param("psDesig") String sDesig,
                       @Param("psDate") String sDate);

    List<LoginControlEntity> selectUserInfoByDate(
      @Param("loginUser") String loginUser,
      @Param("language") String language,
      @Param("date") String date
    );

    int selectOrgRelation(@Param("psCust") String sCust,
                          @Param("loginUser") String sLoginUser,
                          @Param("targetComp") String sTargetComp,
                          @Param("targetSection") String sTargetSec,
                          @Param("systemId") String sSystem,
                          @Param("desig") String desig,
                          @Param("psDate") String sDate);


    /**
     * 所属長 取得SQLの検索.
     * 役職コードなし、仮想兼務適用
     * @param sCustid 検索対象顧客コード
     * @param sCompid 検索対象法人コード
     * @param sDeptid 検索対象組織コード
     * @param sDate 検索基準日
     * @param sPostid 所属長役職コード
     * @param bIncludeactual 仮想兼務の適用可否フラグ
     * @return 所属長情報
     */
    List<V3CompatiblePostEntity> getVersion3SectionChief(
            @Param("custId") String sCustid,
            @Param("compId") String sCompid,
            @Param("deptId") String sDeptid,
            @Param("date") String sDate,
            @Param("postId") String sPostid,
            @Param("bIncludeactual") boolean bIncludeactual
    );

    EmployMentWithMEVo selectDateofemploymentWithME(@Param("custId") String custId,
                                                    @Param("compId") String compId,
                                                    @Param("empId") String empId);


    /**
     * =================== 社员检索（group定义用） =================
     */

    List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDList(EmployInfoSearchDTO searchDTO);

    List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDListAdd(EmployInfoSearchDTO searchDTO);

    List<EmployeeInfoSearchEntity> selectEmployeeInfoList(
            @Param("searchDate") String searchDate,
            @Param("language") String language,
            @Param("designation") String designation,
            @Param("sEmpInfoUserIDList") String sEmpInfoUserIDList,
            @Param("sCompNick") String sCompNick,
            @Param("sSectionNick") String sSectionNick,
            @Param("sPostNick") String sPostNick,
            @Param("loginUser") String loginUser,
            @Param("systemId") String systemId
    );

    IPage<UserManagerListDTO> selectMainAllList(IPage<UserManagerListDTO> page, @Param("custId") String custId,
                                             @Param("language") String language, @Param("companyId") String companyId, @Param("companyList") List<String> companyList);

    IPage<UserManagerListDTO> selectMainLockoutList(IPage<UserManagerListDTO> page, @Param("custId") String custId,
                                                @Param("language") String language, @Param("companyId") String companyId, @Param("companyList") List<String> companyList);

    IPage<UserManagerListDTO> selectMainAdminList(IPage<UserManagerListDTO> page,@Param("custId") String custId,
                                                  @Param("language") String language,@Param("companyId") String companyId,
                                                  @Param("companyList") List<String> companyList);

    IPage<UserManagerListDTO> selectMainEmpNameList(IPage<UserManagerListDTO> page,
                                                    @Param("custId") String custId,
                                                    @Param("language") String language,
                                                    @Param("companyId") String companyId,
                                                    @Param("companyList") List<String> companyList,
                                                    @Param("empName") String empName);

    IPage<UserManagerListDTO> selectMainSectionList(IPage<UserManagerListDTO> page,@Param("custId") String custId,@Param("language") String language,
                                                    @Param("companyList") List<String> companyList, @Param("sectionCompanyId") String sectionCompanyId,
                                                    @Param("sectionId") String sectionId);
}
