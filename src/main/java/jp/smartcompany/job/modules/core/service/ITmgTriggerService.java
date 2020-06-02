package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.ErrMsgDto;

/**
 * @author Xiao Wenpeng
 */
public interface ITmgTriggerService extends IService<TmgTriggerDO> {

    /**
     * 月次一覧、また日次登録（承認）画面表示時打刻反映処理　トリガーに追加する
     * 　月次一覧画面表示時：表示対象月で運用日以前の打刻反映対象となる全ての日のレコード分登録します。
     * 　日次登録（承認画面）表示時：表示対象日の打刻反映対象となる日のレコードを登録します。
     * TMG_DAILYから打刻未反映のデータを対象に処理するので、打刻反映対象のデータが無い場合はトリガーへ登録されないので打刻反映処理は実施されません。
     * ※月次一覧、日次登録（承認）表示時の打刻反映処理以外では使用を想定していないので使用しないで下さい。
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param targetUser 対象者
     * @param userCode　ユーザID
     * @param action　アクション
     * @param stDate　開始
     * @param endDate　終了
     * @return
     */
    int buildSQLForInsertTriggerByTimePunch(String custID, String compCode, String targetUser, String userCode, String action, String stDate, String endDate);

    /**
     * トリガーに追加する
     *
     * @param custID     顧客コード
     * @param ompCode    法人コード
     * @param targetUser 対象者
     * @param day        基準日
     * @param userCode   ユーザコード
     * @param txtAction  アクション
     * @return int
     */
    int buildSQLForInsertTrigger(String custID, String ompCode, String targetUser, String day, String userCode, String txtAction);

    /**
     * 月次一覧、また日次承認画面表示時打刻反映処理　トリガーに追加する
     * 　月次一覧画面表示時：表示対象月で運用日以前の打刻反映対象となる全ての日のレコード分登録します。
     * 　日次承認画面表示時：表示対象日の打刻反映対象となる日のレコードを登録します。
     * <p>
     * TMG_DAILYから打刻未反映のデータを対象に処理するので、打刻反映対象のデータが無い場合はトリガーへ登録されないので打刻反映処理は実施されません。
     * ※月次一覧、日次承認表示時の打刻反映処理以外では使用を想定していないので使用しないで下さい。
     *
     * @param userCode ユーザコード
     * @param action   アクション
     * @param stDate   打刻反映処理対象開始日
     * @param endDate  打刻反映処理対象終了日
     * @param empSql   選択した組織のすべて職員取得SQL
     * @return int
     */
    int buildSQLForInsertTmgTriggerByTimePunch(String userCode, String action, String stDate, String endDate, String empSql);

}
