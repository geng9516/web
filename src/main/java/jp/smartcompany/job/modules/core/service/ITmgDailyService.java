package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyEditVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailNonDutyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailOverhoursVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 対象社員について対象月の未承認日数を集計する
     *
     * @param targetDate  対象日
     * @param compId      　対象法人コード
     * @param custId      　対象顧客コード
     * @param empId       　対象社員
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

}
