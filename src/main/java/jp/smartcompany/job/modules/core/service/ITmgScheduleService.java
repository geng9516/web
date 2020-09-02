package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import jp.smartcompany.job.modules.tmg.schedule.vo.ScheduleDateListVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.TmgWeekPatternVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.WeekWorkType;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 予定作成
 * @date 2020/05/25
 **/
public interface ITmgScheduleService extends IService<Object> {

    /**
     * 年次休暇残
     *
     * @param employeeId
     * @param workYear
     * @param compCode
     * @param custId
     * @return
     */
    NpaidRestDTO selectTmgMonthly(String employeeId, String workYear, String compCode, String custId);

    /**
     * [勤怠]日別情報より予定データを取得します
     *
     * @param ctpye_plan
     * @param dStart
     * @param dEnd
     * @param manageflg
     * @param useFixedFunction
     * @param employeeId
     * @param compCode
     * @param custId
     * @param language
     * @return
     */
    List<ScheduleDataDTO> selectSchedule(String ctpye_plan, String dStart, String dEnd, boolean isVariationalWorkDays, String manageflg,String useFixedFunction, String employeeId, String compCode, String custId, String language);

    /**
     * 4週間単位の変形労働制職員対応
     *
     * @param baseDate
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    HashMap<String, Object> selectBaseDateOf4WeeksBeforeDay(String baseDate, String detailPeriod, String custId, String compCode, String employeeId);

    /**
     * 4週間単位の変形労働制職員対応
     *
     * @param baseDate
     * @param detailPeriod
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    HashMap<String, Object> SelectBaseDateOf4WeeksAfterDay(String baseDate, String detailPeriod, String custId, String compCode, String employeeId);


    /**
     * 翌月リンクを取得
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    String selectLinkOfNextMonth(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 前月リンクを取得
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    HashMap<String, Object> selectLinkOfPreMonth(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 対象ユーザー情報
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @param startDispDate
     * @param language
     * @return
     */
    TargetUserDetailDTO selectTargetUserDetail(String employeeId, String baseDate, String custId, String compCode, String startDispDate, String language);

    /**
     * 平日時の勤務時間
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    String selectWorkingHours(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 今月の月中有給付与の情報
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    HalfwayPaidHolidayDTO selectHalfwayPaidHoliday(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 週勤務パターン
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @param isAfter
     * @return
     */
    List<TmgWeekPatternDTO> selectTmgWeekPattern(String employeeId, String baseDate, String custId, String compCode, boolean isAfter);

    /**
     * 基準日時点で４週間の変形労働制対象者か
     *
     * @param employeeId
     * @param targetDate
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    int selectVariationalWorkInfo(String employeeId, String targetDate, String custId, String compCode, String language);

    /**
     * 基準日の月に４週間の変形労働制の期間が存在するか
     *
     * @param employeeId
     * @param targetDate
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    int selectVariationalWorkDays(String employeeId, String targetDate, String custId, String compCode, String language);


    /**
     * 基本労働制対象者の表示開始?終了日の取得する
     *
     * @param employeeId
     * @param baseDate
     * @param lastday
     * @param custId
     * @param compCode
     * @return
     */
    HashMap<String, Object> selectDsipDate(String employeeId, String baseDate, String lastday, String custId, String compCode);


    /**
     * 月末の次の土曜日まで表示する
     *
     * @param employeeId
     * @param baseDate
     * @param compCode
     * @param custId
     * @return
     */
    HashMap<String, Object> selectLinkOfNextMonthNextSaturday(String employeeId, String baseDate, String compCode, String custId);

    /**
     * 起算日を取得します
     *
     * @param employeeId
     * @param compCode
     * @param custId
     * @return
     */
    String selectDetailPeriod(String employeeId, String compCode, String custId);

    /**
     * 表示基準日が4週間単位の変形労働に則っているか
     *
     * @param targetDate
     * @param detailPeriod
     * @return
     */
    String selectIsStart4weeks(String targetDate, String detailPeriod);


    /**
     * [区分]汎用マスタより区分コンボボックスの選択値を取得します
     *
     * @param language
     * @param baseDate
     * @param compCode
     * @param custId
     * @return
     */
    List<HashMap<String, Object>> selectGenericDetail(String language, String baseDate, String compCode, String custId);


    /**
     * [出張]汎用マスタより出張区分コンボボックスの選択値を取得します
     *
     * @param language
     * @param baseDate
     * @param compCode
     * @param custId
     * @return
     */
    List<HashMap<String, Object>> selectBusinessTrip(String language, String baseDate, String compCode, String custId);

    /**
     * 勤務パターンテーブルより勤務パターンコンボボックスの選択値を取得します.(一括指定用)
     *
     * @param compCode
     * @param custId
     * @param sectionid
     * @param groupid
     * @param baseDate
     * @return
     */
    List<HashMap<String, Object>> selectWorkPatternIkkatu(String compCode, String custId, String sectionid, String groupid, String baseDate);

    /**
     * エラーチェックを削除する
     *
     * @param employeeId tda_cmodifieruserid
     * @param compCode
     * @param custId
     */
    void deleteDailyCheck(String employeeId, String compCode, String custId, String sTargetDate);

    /**
     * エラーチェックを削除する
     *
     * @param employeeId tda_cmodifieruserid
     * @param compCode
     * @param custId
     */
    void deleteDailyDetailCheck(String employeeId, String compCode, String custId, String sTargetDate);

    /**
     * [勤怠]日別情報を更新する
     *
     * @param sLoginUserCode
     * @param tmg_schedule_cmodifierprogramid
     * @param isClearResult
     * @param cs_mgd_holflg
     * @param tda_cworkingid_p
     * @param tda_nopen_p
     * @param tda_nclose_p
     * @param bNoWorking
     * @param tda_cbusinesstripid_p
     * @param tda_ccomment_p
     * @param employeeid
     * @param dyyyymmdd
     * @param compCode
     * @param custId
     * @param language
     */
    void insertTmgDailyCheck(String sLoginUserCode, String tmg_schedule_cmodifierprogramid, boolean isClearResult, String cs_mgd_holflg, String tda_cworkingid_p,
                             String tda_nopen_p, String tda_nclose_p, boolean bNoWorking, String tda_cbusinesstripid_p, String tda_ccomment_p, String employeeid,
                             String dyyyymmdd, String compCode, String custId, String language);

    /**
     * 日次詳細情報登録（休憩時間：予定）
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param sLoginUserCode
     * @param tmg_schedule_cmodifierprogramid
     * @param sTargetDate
     * @param sNotWorkId
     * @param nRestOpen
     * @param nRestClose
     * @param notworkingid_plan_rest
     * @param notworkingid_notice_rest
     * @param notworkingid_result_rest
     * @param isClearResult
     * @param isNotWorkId
     */
    void insertTmgDailyDetailCheckRest(String custId, String compCode, String employeeId, String minDate, String maxDate, String sLoginUserCode, String tmg_schedule_cmodifierprogramid, String sTargetDate, String sNotWorkId, String nRestOpen, String nRestClose, String notworkingid_plan_rest, String notworkingid_notice_rest, String notworkingid_result_rest, boolean isClearResult, boolean isNotWorkId);

    /**
     * 勤怠トリガーテーブルに更新対象のデータを挿入する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param sLoginUserCode
     * @param tmg_schedule_cmodifierprogramid
     * @param sTargetDate
     * @param act_editmonthly_uschedule
     */
    void insertTmgTrigger(String custId, String compCode, String employeeId, String minDate, String maxDate, String sLoginUserCode, String tmg_schedule_cmodifierprogramid, String sTargetDate, String act_editmonthly_uschedule);

    /**
     * 勤怠トリガーテーブルから該当データを削除する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param tmg_schedule_cmodifierprogramid
     */
    void deleteTmgTrigger(String custId, String compCode, String employeeId, String tmg_schedule_cmodifierprogramid);

    /**
     * エラーメッセージを削除する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param tmg_schedule_cmodifierprogramid
     */
    void deleteErrMsg(String custId, String compCode, String employeeId, String tmg_schedule_cmodifierprogramid);

    /**
     * 週勤務パターンチェックを削除する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param tmg_schedule_cmodifierprogramid
     */
    void deleteWeekPatternCheck(String custId, String compCode, String employeeId, String tmg_schedule_cmodifierprogramid);

    /**
     * エラーチェックを削除する
     *
     * @param custId
     * @param compCode
     * @param sLoginUserCode
     */
    void deleteDetailCheck(String custId, String compCode, String sLoginUserCode, String sTargetDate);


    /**
     * 週勤務パターンを取得する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param language
     * @param twp_nid
     * @return
     */
    TmgWeekPatternVO selectCsvReference(String custId, String compCode, String language, String employeeId, int twp_nid);

    /**
     * 週勤務パターンリストを取得する
     *
     * @param custId
     * @param compCode
     * @param language
     * @param employeeId
     * @return
     */
    List<TmgWeekPatternVO> selectCsvReferenceList(String custId, String compCode, String language, String employeeId, int twp_nid);

    /**
     * [勤怠]週次勤務パターン（エラーチェック用）登録
     *
     * @param tmgWeekPatternCheckDTO
     */
    void insertTmgWeekPatternCheck(TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO);

    /**
     * エラーメッセージに追加する
     *
     * @param custId
     * @param compCode
     * @param language
     * @param employeeId
     * @param modifieruserid
     * @param modifierprogramid
     * @param minDate
     * @param maxDate
     */
    void insertErrMsg(String custId, String compCode, String language, String employeeId, String modifieruserid, String modifierprogramid, String minDate, String maxDate);


    /**
     * エラーメッセージを取得する
     *
     * @param custId
     * @param compCode
     * @param modifieruserid
     * @param modifierprogramid
     * @return
     */
    String selectErrMsg(String custId, String compCode, String modifieruserid, String modifierprogramid);

    /**
     * トリガーに追加する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param sLoginUserCode
     * @param modifierprogramid
     * @param sAction
     */
    void insertTrigger(String custId, String compCode, String employeeId, String sLoginUserCode, String modifierprogramid, String sAction);

    /**
     * 検索対象年月日の開始日
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param startDate
     * @param endDate
     * @param param4WeekAfter
     * @return
     */
    String selectBaseDateFor4Week(String custId, String compCode, String employeeId, String startDate, String endDate, int param4WeekAfter);

    /**
     * 対象社員の勤怠日別情報の最小日と最大日を取得する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    HashMap<String, String> selectTmgDailyMinAndMax(String custId, String compCode, String employeeId);

    /**
     * TmgMonthlyの更新日取得(予定確認画面)
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param baseDate
     * @return
     */
    String selectMonthlyModifiedDate(String custId, String compCode, String employeeId, String baseDate);


    /**
     * 確認ボタンのフラグレコード更新処理
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param baseDate
     * @param modifierprogramid
     * @param onff
     * @param type
     */
    void updateSchedulePermStatus(String custId, String compCode, String employeeId, String baseDate, String modifierprogramid, String onff, String type);

    /**
     * 週勤務パターンリスト
     *
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    List<WeekWorkType> selectWeekPtn(String custId, String compCode, String language);

    /**
     * 週勤務パターンを削除する
     *
     * @param custId
     * @param compCode
     * @param twp_nid
     */
    void deleteWeekPtn(String custId, String compCode, String twp_nid);

    /**
     * [勤怠]週次勤務パターン（エラーチェック用）抽出登録
     *
     * @param custId            　　　必要
     * @param compCode          　　　必要
     * @param employeeId        　　　必要
     * @param modifieruserid    　　　非必要
     * @param modifierprogramid 　　　非必要
     * @param twp_dstartdate    　　　非必要
     * @param twp_denddate      　　　非必要
     * @param twp_nid           　　　非必要
     * @return
     */
    int buildSQLForSelectInsertTmgWeekPatternCheck(String custId, String compCode, String employeeId, String modifieruserid, String modifierprogramid, String twp_dstartdate, String twp_denddate, String twp_nid);

    /**
     * 週勤務パターンを更新する
     *
     * @param twp_dstartdate
     * @param twp_denddate
     * @param twp_nid
     * @param modifieruserid
     * @return
     */
    int updateWeekPattern(String twp_dstartdate, String twp_denddate, String twp_nid, String modifieruserid);


    /**
     * * 対象職員について、開始日～終了日の勤怠締め有無、給与確定有無を判定して返します。
     * * この検索結果は、個人タイプかつ編集機能を持つ各種コンテンツにおいて、編集可否を判定する際に使用できます。
     * * 第3引数"all"がtrueであれば、指定された期間全てが勤怠締め完了（または給与確定完了）の場合に1を返します。
     * * falseであれば、指定された期間のうち1月でも勤怠締め完了（または給与確定完了）の場合に1を返します。
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param dstart
     * @param dend
     * @param group
     * @return
     */
    TmgStatusDTO buildSQLForSelectTmgStatus(String custId, String compCode, String employeeId, String dstart, String dend, String group);

    /**
     * * 対象職員・年月日の日別、月別ステータスおよび勤怠締め有無、給与確定有無、システム日付との比較結果を返します。
     * * この検索結果は、個人タイプかつ編集機能を持つ各種コンテンツにおいて、編集可否を判定する際に使用できます。
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param dyyyymmdd
     * @return
     */
    TmgStatusDTO buildSQLForSelectTmgStatus2(String custId, String compCode, String employeeId, String dyyyymmdd);

    /**
     * 予定編集可能な職員かマスタデータより判定を行った結果を取得する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param language
     * @return
     */
    String buildSQLForSelectUseScheculeUser(String custId, String compCode, String employeeId, String language);

    /**
     * 勤務予定時間リスト
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param formatter
     * @return
     */
    List<ScheduleDateListVO> selectScheduleDateList(String custId, String compCode, String employeeId, String formatter);


}
