package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.DispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.DailyDetailOverHoursVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.DailyVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.MonthlyInfoOverSumVo;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.PaidHolidayVO;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgStatus;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyDetailVO;
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




    List<MonthlyInfoOverSumVo> selectMonthlyOverSum(@Param("custId")String custId,
                                                    @Param("compId")String compId,
                                                    @Param("userID")String userID,
                                                    @Param("sBaseDBDate")String sBaseDBDate,
                                                    @Param("sMonthsNum")String sMonthsNum);



    List<DailyVo> selectDaily(@Param("custId")String custId,
                                          @Param("compId")String compId,
                                          @Param("sectionId")String sectionId,
                                          @Param("baseMonth")String baseMonth,
                                          @Param("baseDay")String baseDay,
                                          @Param("pLanguage")String pLanguage,
                                          @Param("empListSql")String empListSql,
                                          @Param("itemsSql")List<DispOverTimeItemsDto> itemsSql);

    /**
     *日別一覧データを取得する
     */
    List<HashMap> buildSQLForSelectTmgDaily(@Param("targetDate")String targetDate, @Param("empSql")String empSql, @Param("list") List<String> list);

    /**
     *更新対象職員のROWIDを取得する
     */
    List<String> buildSQLForSelectObjEmpForUpdate(Map<String, Object> map);

    /**
     * 一括承認データを更新する
     */
    int buildSQLForUpdateTmgDaily(Map<String, Object> map);

    List<DailyDetailOverHoursVo> selectDailyDetailOverHours(@Param("custId")String custId,
                                                            @Param("compId")String compId,
                                                            @Param("baseDate")String baseDate,
                                                            @Param("empListSql")String empListSql);

    /**
     * [勤怠]年次休暇情報より、年次休暇付与状況一覧を取得する
     */
    List<PaidHolidayVO> buildSQLForSelectPaidHoliday(Map<String, Object> map);

    /**
     *対象職員・年月日の日別、月別ステータスおよび勤怠締め有無、給与確定有無、システム日付との比較結果を返します。
     */
    TmgStatus buildSQLForSelectTmgStatus(@Param("custID")String custID,
                                         @Param("compCode")String compCode,
                                         @Param("userCode")String userCode,
                                         @Param("day")String day);

    String selectMaxDaily();
}
