package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgErrmsgDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.ErrMsgDto;

/**
 * <p>
 * [勤怠]エラーメッセージ格納テーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgErrmsgService extends IService<TmgErrmsgDO> {

    /**
     * エラーメッセージを取得する
     *
     * @param custID   顧客コード
     * @param ompCode  法人コード
     * @param userCode ユーザコード
     */
    ErrMsgDto buildSQLForSelectErrMsg(String custID, String ompCode, String userCode);

    /**
     * エラーメッセージをインサートする
     *
     * @param custId    顧客コード
     * @param compId    法人コード
     * @param userId    ユーザコード
     * @param programId プログラムID
     * @param lang      　言語
     * @returnk 件数
     */
    int buildSQLForInsertNoErrMsg(String custId, String compId, String userId, String programId, String lang);


    /**
     * 月次承認処理時、エラーチェックを行った結果をエラーメッセージテーブルへ登録する
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param userCode ユーザコード
     * @param programId　プログラムID
     * @param language　　言語
     * @param txtcCmployeeid　職員番号
     * @param txtDyyyymm　該当月
     * @return
     */
    int buildSQLForInsertErrMsgForMonthlyApproval(String custID, String compCode, String userCode, String programId, String language, String txtcCmployeeid, String txtDyyyymm);


    /**
     * エラーメッセージに追加する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param userCode   ユーザコード
     * @param programId  　プログラムID
     * @param language   　　言語
     * @param targetUser 職員番号
     * @param day        該当日
     * @return
     */
    int buildSQLForInsertErrMsg(String custID, String compCode, String userCode, String programId, String language, String targetUser, String day);

}
