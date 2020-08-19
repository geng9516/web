package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import jp.smartcompany.job.modules.tmg.schedule.vo.TmgWeekPatternVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.WeekWorkType;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 予定作成
 * @date 2020/05/25
 **/
@Mapper
public interface TmgScheduleMapper extends BaseMapper<Object> {

    /**
     * 年次休暇残
     *
     * @param params
     * @return
     */
    NpaidRestDTO selectTmgMonthly(HashMap<String, Object> params);

    /**
     * [勤怠]日別情報より予定データを取得します
     *
     * @param params
     * @return
     */
    @SqlParser(filter = true)
    List<ScheduleDataDTO> selectSchedule(HashMap<String, Object> params);

    /**
     * 4週間単位の変形労働制職員対応
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectBaseDateOf4WeeksBeforeDay(HashMap<String, Object> params);

    /**
     * 4週間単位の変形労働制職員対応
     *
     * @param params
     * @return
     */
    HashMap<String, Object> SelectBaseDateOf4WeeksAfterDay(HashMap<String, Object> params);


    /**
     * 翌月リンクを取得
     *
     * @param params
     * @return
     */
    String selectLinkOfNextMonth(HashMap<String, Object> params);

    /**
     * 前月リンクを取得
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectLinkOfPreMonth(HashMap<String, Object> params);

    /**
     * 対象ユーザー情報
     *
     * @param params
     * @return
     */
    TargetUserDetailDTO selectTargetUserDetail(HashMap<String, Object> params);

    /**
     * 平日時の勤務時間
     *
     * @param params
     * @return
     */
    String selectWorkingHours(HashMap<String, Object> params);

    /**
     * 今月の月中有給付与の情報
     *
     * @param params
     * @return
     */
    HalfwayPaidHolidayDTO selectHalfwayPaidHoliday(HashMap<String, Object> params);

    /**
     * 週勤務パターン
     *
     * @param params
     * @return
     */
    List<TmgWeekPatternDTO> selectTmgWeekPattern(HashMap<String, Object> params);

    /**
     * 基準日時点で４週間の変形労働制対象者か
     *
     * @param params
     * @return
     */
    int selectVariationalWorkInfo(HashMap<String, Object> params);

    /**
     * 基準日の月に４週間の変形労働制の期間が存在するか
     *
     * @param params
     * @return
     */
    int selectVariationalWorkDays(HashMap<String, Object> params);

    /**
     * 基本労働制対象者の表示開始?終了日の取得する
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectDsipDate(HashMap<String, Object> params);


    /**
     * 月末の次の土曜日まで表示する
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectLinkOfNextMonthNextSaturday(HashMap<String, Object> params);

    /**
     * 起算日を取得します
     *
     * @param params
     * @return
     */
    String selectDetailPeriod(HashMap<String, Object> params);

    /**
     * 表示基準日が4週間単位の変形労働に則っているか
     *
     * @param params
     * @return
     */
    String selectIsStart4weeks(HashMap<String, Object> params);

    /**
     * [区分]汎用マスタより区分コンボボックスの選択値を取得します
     *
     * @param params
     * @return
     */
    List<HashMap<String, Object>> selectGenericDetail(HashMap<String, Object> params);


    /**
     * [出張]汎用マスタより出張区分コンボボックスの選択値を取得します
     *
     * @param params
     * @return
     */
    List<HashMap<String, Object>> selectBusinessTrip(HashMap<String, Object> params);

    /**
     * 勤務パターンテーブルより勤務パターンコンボボックスの選択値を取得します.(一括指定用)
     *
     * @param params
     * @return
     */
    List<HashMap<String, Object>> selectWorkPatternIkkatu(HashMap<String, Object> params);

    /**
     * エラーチェックを削除する
     *
     * @param params
     */
    void deleteDailyCheck(HashMap<String, Object> params);

    /**
     * エラーチェックを削除する
     *
     * @param params
     */
    void deleteDailyDetailCheck(HashMap<String, Object> params);

    /**
     * [勤怠]日別情報を更新する
     *
     * @param params
     */

    void insertTmgDailyCheck(HashMap<String, Object> params);

    /**
     * 日次詳細情報登録（休憩時間：予定）
     *
     * @param params
     */
    void insertTmgDailyDetailCheckRest(HashMap<String, Object> params);

    /**
     * 勤怠トリガーテーブルに更新対象のデータを挿入する
     *
     * @param params
     */
    @SqlParser(filter = true)
    void insertTmgTrigger(HashMap<String, Object> params);

    /**
     * 勤怠トリガーテーブルから該当データを削除する
     *
     * @param params
     */
    void deleteTmgTrigger(HashMap<String, Object> params);

    /**
     * エラーチェックを削除する
     *
     * @param params
     */
    void deleteDetailCheck(HashMap<String, Object> params);


    /**
     * 週勤務パターンを取得する
     *
     * @param params
     * @return
     */
    TmgWeekPatternVO selectCsvReference(HashMap<String, Object> params);

    /**
     * 　週勤務パターンリストを取得する
     *
     * @param params
     * @return
     */
    List<TmgWeekPatternVO> selectCsvReferenceList(HashMap<String, Object> params);


    /**
     * エラーメッセージを削除する
     *
     * @param params
     */
    void deleteErrMsg(HashMap<String, Object> params);

    /**
     * 　週勤務パターンチェックを削除する
     *
     * @param params
     */
    void deleteWeekPatternCheck(HashMap<String, Object> params);

    /**
     * [勤怠]週次勤務パターン（エラーチェック用）登録
     *
     * @param tmgWeekPatternCheckDTO
     */
    @SqlParser(filter = true)
    void insertTmgWeekPatternCheck(TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO);

    /**
     * エラーメッセージに追加する
     *
     * @param params
     */
    void insertErrMsg(HashMap<String, Object> params);

    /**
     * エラーメッセージを取得する
     *
     * @param params
     * @return
     */
    String selectErrMsg(HashMap<String, Object> params);

    /**
     * トリガーに追加する
     *
     * @param params
     */
    void insertTrigger(HashMap<String, Object> params);

    /**
     * 検索対象年月日の開始日
     *
     * @param params
     * @return
     */
    String selectBaseDateFor4Week(HashMap<String, Object> params);

    /**
     * 対象社員の勤怠日別情報の最小日と最大日を取得する
     *
     * @param params
     * @return
     */
    HashMap<String, String> selectTmgDailyMinAndMax(HashMap<String, Object> params);

    /**
     * TmgMonthlyの更新日取得
     *
     * @param params
     * @return
     */
    String selectMonthlyModifiedDate(HashMap<String, Object> params);

    /**
     * 確認ボタンのフラグレコード更新処理
     *
     * @param params
     */
    void updateSchedulePermStatus(HashMap<String, Object> params);

    /**
     * 週勤務パターン画面に勤務区分リスト
     *
     * @param params
     * @return
     */
    List<WeekWorkType> selectWeekPtn(HashMap<String, Object> params);

    /**
     * 週勤務パターンを削除する
     *
     * @param params
     */
    void deleteWeekPtn(HashMap<String, Object> params);

    /**
     * [勤怠]週次勤務パターン（エラーチェック用）抽出登録
     *
     * @param params
     * @return
     */
    int buildSQLForSelectInsertTmgWeekPatternCheck(HashMap<String, Object> params);

}
