package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.dto.dispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.dailyDetailVo;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.monthlyInfoOverSumVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyEditVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailNonDutyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailOverhoursVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]日別情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgDailyMapper extends BaseMapper<TmgDailyDO> {

    /**
     * 対象社員について対象月の未承認日数を集計する
     *
     * @param map
     * @return 未承認日数
     */
    String buildSQLForSelectCountNotApprovalDay(Map<String, Object> map);


    /**
     *日別情報を取得する
     *
     * @param map
     * @return
     */
    List<HashMap> buildSQLForSelectDaily(Map<String, Object> map);


    /**
     * 日別情報(編集用)を取得する
     *
     * @param map
     * @return
     */
    DailyEditVO buildSQLForSelectDailyEdit(Map<String, Object> map);
    /**
     * 日別詳細情報（非勤務）を取得する
     *
     * @param map
     * @return
     */
    List<DetailNonDutyVO>  buildSQLForSelectDetailNonDuty(Map<String, Object> map);
    /**
     * 日別詳細情報（超過勤務）を取得する
     *
     * @param map
     * @return
     */
    List<DetailOverhoursVO> buildSQLForSelectDetailOverhours(Map<String, Object> map);




    List<monthlyInfoOverSumVo> selectMonthlyOverSum(@Param("custId")String custId,
                                                    @Param("compId")String compId,
                                                    @Param("userID")String userID,
                                                    @Param("sBaseDBDate")String sBaseDBDate,
                                                    @Param("sMonthsNum")String sMonthsNum);



    List<dailyDetailVo> selectDailyDetail(@Param("custId")String custId,
                                          @Param("compId")String compId,
                                          @Param("sectionId")String sectionId,
                                          @Param("baseMonth")String baseMonth,
                                          @Param("baseDay")String baseDay,
                                          @Param("pLanguage")String pLanguage,
                                          @Param("empListSql")String empListSql,
                                          @Param("itemsSql")List<dispOverTimeItemsDto> itemsSql);

    /**
     *日別一覧データを取得する
     */
    List<HashMap> buildSQLForSelectTmgDaily(String targetDate, String empSql, List<String> list);
}
