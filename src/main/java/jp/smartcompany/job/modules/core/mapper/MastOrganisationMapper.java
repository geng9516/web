package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetFiscalYearDto;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotApprovalVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotFixedDeptListVo;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.SectionRankDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.LimitOfBasedateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 組織ツリーマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastOrganisationMapper extends BaseMapper<MastOrganisationDO> {

     List<String> selectLowerSection(@Param("custId") String custId,@Param("compId") String compId,
                                            @Param("sectionId") String psSection,@Param("date") Date date);

     List<MastOrganisationDO> selectLowEmp(@Param("custId") String custId,
                                           @Param("compId") String compId,
                                           @Param("sectionId") String sectionId,
                                           @Param("searchDate") String searchDate,
                                           @Param("virtualSection") Boolean virtualSection);

     /**
      * 基準日時点の超勤限度時間取得用
      */
     LimitOfBasedateVO buildSQLForLimitOfBasedate(Map<String, Object> map);

     /**
      * 職員情報を取得する
      */
     String buildSQLForSelectEmployeeDetail(Map<String, Object> map);

     /**
      * 選択された組織の表示権限があるかを判定するSQLを返すメソッド
      */
     int selectHasAuth(@Param("customerId") String customerId,
                       @Param("compnyId")String compnyId,
                       @Param("sectionId")String sectionId,
                       @Param("searchDate")String searchDate,
                       @Param("language")String language,
                       @Param("exists")String exists);



     TargetFiscalYearDto selectTargetFiscalYear(@Param("cust")String cust,
                                                @Param("comp")String comp,
                                                @Param("section")String section,
                                                @Param("lang")String lang,
                                                @Param("targetDate")String targetDate,
                                                @Param("targetStartDate")String targetStartDate,
                                                @Param("targetEneDate")String targetEneDate,
                                                @Param("psBaseDate")String psBaseDate);


     List<NotApprovalVo>  selectNotApproval(@Param("custId")String custId,
                                            @Param("compId")String compId,
                                            @Param("secId")String secId,
                                            @Param("dyyyymm")String dyyyymm,
                                            @Param("lang")String lang,
                                            @Param("numStart")int numStart,
                                            @Param("numEnd")int numEnd);



     List<NotFixedDeptListVo>  selectNotFixedDeptList(@Param("custId")String custId,
                                                      @Param("compId")String compId,
                                                      @Param("secId")String secId,
                                                      @Param("dyyyymm")String dyyyymm,
                                                      @Param("lang")String lang,
                                                      @Param("numStart")int numStart,
                                                      @Param("numEnd")int numEnd);

     List<OrgTreeDTO> selectOrgList(
             @Param("customerId") String customerId,
             @Param("language") String language,
             @Param("companyId") String companyId,
             @Param("searchDate") String searchDate,
             @Param("startDate") String startDate,
             @Param("endDate") String endDate,
             @Param("companyCode") String companyCode,
             @Param("sectionCode") String sectionCode,
             @Param("sExists") String sExists
     );

     List<OrgTreeDTO> selectSelCompOrgTreeList(
             @Param("customerId") String customerId,
             @Param("language") String language,
             @Param("companyId") String companyId,
             @Param("startDate") String startDate,
             @Param("endDate") String endDate,
             @Param("sExists") String sExists
     );

    List<SectionRankDto> selectSectionRankDto(@Param("custID")String custID,
                                              @Param("compCode")String compCode,
                                              @Param("language")String language,
                                              @Param("sectionId")String sectionId);
}
