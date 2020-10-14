package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDO;
import com.baomidou.mybatisplus.extension.service.IService;
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

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * [勤怠]日別情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgDailyService extends IService<TmgDailyDO> {

    /**
     * 対象職員について対象月の未承認日数を集計する
     *
     * @param targetDate  対象日
     * @param compId      　対象法人コード
     * @param custId      　対象顧客コード
     * @param empId       　対象職員
     * @param dataStatus9 　確定済
     * @param dataStatus5 　承認済
     * @return 未承認日数
     */
    String buildSQLForSelectCountNotApprovalDay(String targetDate, String compId, String custId, String empId, String dataStatus9, String dataStatus5);


    /**
     * 日別情報を取得する
     *
     * @param custID 法人コード
     * @param compCode 顧客コード
     * @param targetUser　対象者
     * @param language　言語
     * @param month　対象月
     * @param manageFlg　管理者フラグ
     * @param list　変動項目リスト
     * @return List<HashMap>
     */
    List<HashMap> buildSQLForSelectDaily(String custID, String compCode, String targetUser, String language, String month, String manageFlg, List<String> list);


    /**
     *日別情報(編集用)を取得する
     *
     * @param custId 法人コード
     * @param compCode 顧客コード
     * @param targetUser 対象者
     * @param day 対象日
     * @param today　今日
     * @param siteId　サイトID
     * @param language　言語
     * @return DailyEditVO
     */
    DailyEditVO buildSQLForSelectDailyEdit(String custId, String compCode, String targetUser, String day, String today, String siteId, String language);

    /**
     * 日別詳細情報（非勤務）を取得する
     *
     * @param custId 法人コード
     * @param compCode 顧客コード
     * @param targetUser 対象者
     * @param siteId　サイトID
     * @param day　対象日
     * @param language　言語
     * @return
     */
    List<DetailNonDutyVO> buildSQLForSelectDetailNonDuty(String custId, String compCode, String targetUser, String siteId, String day, String language);

    /**
     * 日別詳細情報（超過勤務）を取得する
     *
     * @param custId                 法人コード
     * @param compCode               顧客コード
     * @param targetUser             対象者
     * @param siteId                 　サイトID
     * @param day                    　対象日
     * @param language               　言語
     * @param isOvertimeNotification 表示制御
     * @return
     */
    List<DetailOverhoursVO> buildSQLForSelectDetailOverhours(String custId, String compCode, String targetUser, String siteId, String day, String language, boolean isOvertimeNotification);



    /**
     * [勤怠]日別集計情報より、表示対象職員の指定期間内の時間外労働時間、休日労働時間、超勤時間合計時間を集計して取得する
     * 引数には事前にescDBStringを使用しクエリにて使用出来る様にエスケープをかけて下さい。
     *
     * @param  custId      顧客ID
     * @param  compId      法人ID
     * @param  userID      職員ID
     * @param  sBaseDBDate 基準日(日付型変換)
     * @param  sMonthsNum  遡り月数
     * @return SQL文
     */
    List<MonthlyInfoOverSumVo> selectMonthlyOverSum(String custId, String compId, String userID, String sBaseDBDate, String sMonthsNum);



    /**
     * 日別一覧データを取得する
     *
     * @param targetDate 表示対象日
     * @param empSql     対象者取得SQL
     * @param list       　動態項目
     * @return
     */
    List<HashMap> buildSQLForSelectTmgDaily(String targetDate, String empSql, List<String> list);

    /**
     * 更新対象職員のROWIDを取得する
     *
     * @param empIdList 職員リスト
     * @param yyyymmdd  　対象日
     * @return 更新対象rowidリスト
     */
    List<String> buildSQLForSelectObjEmpForUpdate(List<String> empIdList, String yyyymmdd);


    /**
     *  一括承認データを更新する
     * @param loginUserCode 更新者
     * @param programId　更新プログラムID
     * @param yyyymmdd　対象日
     * @param empIdList　
     * @return
     */
    int buildSQLForUpdateTmgDaily(String loginUserCode, String programId, String yyyymmdd,List<String > empIdList);

    List<DailyVo> selectDaily(String pCustCode, String pCompCode, String sectionCode, String pYYYYMM,
                              String pYYYYMMDD, String pLangage, String empListSql, List<DispOverTimeItemsDto> itemsSql);


    /**
     * 日別詳細情報（超過勤務）を取得するSQLを返す
     *
     * @param  custId       顧客コード
     * @param  compId       法人コード
     * @param  baseDate  対象年月日（ＤＢ検索用にエスケープ済(')）
     * @return SQL文
     */
    List<DailyDetailOverHoursVo> selectDailyDetailOverHours(String custId, String compId, String baseDate , String empListSql);


    /**
     *[勤怠]年次休暇情報より、年次休暇付与状況一覧を取得する
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param dispUserCode 対象者
     * @param searchStart　開始日
     * @param searchEnd　終了日
     * @return
     */
    List<PaidHolidayVO> buildSQLForSelectPaidHoliday(String custID, String compCode, String dispUserCode, String searchStart, String searchEnd);

    /**
     * 対象職員・年月日の日別、月別ステータスおよび勤怠締め有無、給与確定有無、システム日付との比較結果を返します。
     *
     * @param custID  顧客コード
     * @param compCode 法人コード
     * @param userCode 対象者
     * @param day 対象者
     * @return
     */
    TmgStatus buildSQLForSelectTmgStatus(String custID, String compCode, String userCode, String day);


    /**
     * 予定入力可能情報を取得
     * @return String SQL (予定入力可能年月取得用クエリ)
     */
    String selectMaxDaily();
}
