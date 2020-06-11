package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.employeeDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.employeeListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 異動歴 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface HistDesignationMapper extends BaseMapper<HistDesignationDO> {


        /**
         * 在籍部署・グループを検索
         */
        SectionGroupId selectSecGroupId(Map<String, Object> map);


        List<EvaluatorBO> selectEvaluator(@Param("customerId") String customerId,
                                          @Param("systemId") String systemId,
                                          @Param("userId") String userId,
                                          @Param("searcharDate") String pdSearchDate,
                                          @Param("evaluation") int evaluation,
                                          @Param("reportType") String reportType,
                                          @Param("language") String language);

        List<EvaluatorBO> selectAllEvaluator(@Param("customerId") String psCustomerId,
                                             @Param("userId") String psUserId,
                                             @Param("searchDate") String pdSearchDate,
                                             @Param("language") String psLanguage);

        List<EvaluatorBO> selectWithSection(
                @Param("custId") String psCustId,
                @Param("userId") String psUserId,
                @Param("sectionId") String psSectionId,
                @Param("searchDate") String pdSearchDate,
                @Param("language") String sLanguage);

        List<EvaluatorBO> selectSectionChief(
                @Param("custId") String psCustId,
                @Param("compId") String compId,
                @Param("sectionId") String sectionId,
                @Param("searchDate")String pdSearchDate,
                @Param("language") String sLanguage);

        /**
         * ヘッダ情報(新規申請用)を取得するSQLを生成します。
         */
        employeeDetailVo selectemployeeDetail(Map<String, Object> map);


        employeeDetailVo selectemployee(Map<String, Object> map);

        List<employeeListVo> selectemployeeList(@Param("custId")String custId,
                                                @Param("compId")String compId,
                                                @Param("date")String date,
                                                @Param("sql")String sql);

        String  selectSectionNAme(@Param("custId")String custId,
                                                @Param("compId")String compId,
                                                @Param("date")Date date,
                                                @Param("sectionId")String sectionId);

        /**
         * CSV出力用データを取得
         */
        List<Map> buildSQLForSelectCSVOutputImage(Map<String, Object> map);



}
