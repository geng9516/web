package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.ListBoxVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.PaidHolidayThisMonthInfoVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MonthlyLinkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月別情報                      2007/2/23 年休調整日数のカラムを追加。年休(時間) Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMonthlyMapper extends BaseMapper<TmgMonthlyDO> {

    /**
     * 該当の職員・年月の超過勤務時間数の合計を取得する
     *
     * @param map Map
     * @return 合計
     */
    List<String> buildSQLForSelectMonthlyOverTime(Map<String, Object> map);


    /**
     * 表示月遷移リスト情報を取得する
     *
     * @param map Map
     * @return 合計
     */
    List<DispMonthlyVO> buildSQLForSelectDispMonthlyList(Map<String, Object> map);

    /**
     * 前月・翌月の月別情報を取得する
     *
     * @param map Map
     * @return MonthlyLinkVO
     */
    MonthlyLinkVO buildSQLForSelectMonthlyLink(Map<String, Object> map);

    /**
     * カレンダーを取得する
     * @param map Map
     * @return Map
     */
    HashMap buildSQLForSelectCalendar(Map<String, Object> map);


    /**
     * 月別情報を取得する
     * @param map Map
     * @return Map
     */
    HashMap buildSQLForSelectMonthly(Map<String, Object> map);



        /**
         * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
         */

        Map<String,Object> selectMonthlyDisp(@Param("customerId") String customerId,
                                             @Param("companyId") String companyId,
                                             @Param("yyyymmdd") String yyyymmdd,
                                             @Param("employeeId") String employeeId,
                                             @Param("array")String[] params);


        /**
         * 今月の月中有給付与に関する情報を返すSQL
         */
        List<PaidHolidayThisMonthInfoVo>  selectPaidHolidayThisMonthInfo(Map<String,Object> map);

    /**
     * 月別情報を確定/確定解除する
     * @param map Map
     * @return Map
     */
    int buildSQLForUpdateMonthly(Map<String, Object> map);


    /**
     * 表示月遷移リスト情報を取得する
     */
    List<DispMonthlyVO> buildSQLForSelectDispTmgMonthlyList(Map<String, Object> map);

    /**
     * 月別エラーチェック
     */
    String checkMonthly(Map<String, Object> map);

    /**
     * 共通：画面に表示する職員一覧のデータを取得する
     */
    Map buildSQLSelectSection(Map<String, Object> map);

    /**
     * 前月リンク取得用
     */
    String buildSQLSelectLinkOfPreMonth(@Param("empsql")String empsql, @Param("baseDate")String baseDate);
    /**
     * 翌月リンク取得用
     */
    String buildSQLSelectLinkOfNextMonth(@Param("empsql")String empsql, @Param("baseDate")String baseDate);

    /**
     * [勤怠]社員別のデータを取得
     */
    List<Map> buildSQLSelectEmployyes(Map<String, Object> map);

    List<ListBoxVo> selectYearDate(@Param("custID")String custID, @Param("compCode")String compCode);

    List<ListBoxVo> selectMonthDate(@Param("custID")String custID, @Param("compCode")String compCode);
}
