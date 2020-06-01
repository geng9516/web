package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailCheckDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.DetailCheckDto;

/**
 * <p>
 * [勤怠]エラーチェック用・日別情報詳細           2007/02/23元テーブルのレイアウト変更に伴い修正 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgDailyDetailCheckService extends IService<TmgDailyDetailCheckDO> {


    /**
     * 日別詳細情報チェック(申請関係)を追加する
     *
     * @param custID     顧客コード
     * @param ompCode    法人コード
     * @param targetUser 対象者
     * @param day        基準日
     * @param userCode   ユーザコード
     * @param txtAction  アクション
     * @return int
     */
    int buildSQLForInsertDetailCheckEtc(String custID, String ompCode, String targetUser, String day, String userCode, String txtAction);

    /**
     * 日別詳細情報チェック(画面非表示項目)を追加する
     *
     * @param custID     顧客コード
     * @param ompCode    法人コード
     * @param targetUser 対象者
     * @param day        基準日
     * @param userCode   ユーザコード
     * @param txtAction  アクション
     * @param siteId     サイトID
     * @return int
     */
    int buildSQLForInsertDetailCheckNotDisp(String custID, String ompCode, String targetUser, String day, String userCode, String txtAction, String siteId);

    /**
     *日別詳細情報チェックを追加する
     * @param detailCheckDto    DetailCheckDto
     * @return 件数
     */
    int buildSQLForInsertDetailCheck(DetailCheckDto detailCheckDto);

}
