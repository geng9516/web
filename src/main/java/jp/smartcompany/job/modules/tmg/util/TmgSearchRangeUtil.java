package jp.smartcompany.job.modules.tmg.util;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * @author Xiao Wenpeng
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgSearchRangeUtil  {

//    private final PsBuildTargetSqlLogic psBuildTargetSqlLogic;

    /**
     * LOG出力用ディスクリプタ
      */
    private final String BEAN_DESC = "TmgSearchRangeUtil";
    /**
     * 自所属以下を示すグループ
     */
    public static final String SEARCH_RANGE_USER_SECTION_DEFS ="'0008'";
    /**
     * 前所属を示すグループ
     */
    public static final String SEARCH_RANGE_ALL_SECTION_DEFS ="'0001'";

    /**
     * 検索対象範囲EXISTS句取得処理
     * @param pSession
     * @param psCompanyColumn
     * @param psEmployeeColumn
     * @return
     * @throws Exception
     */
    public String getExistsQuery(PsDBBean psDBBean, HttpSession pSession, String psCompanyColumn, String psEmployeeColumn) {
        // 動作フレームワーク判定
        // V3の処理
//        if (getFrameVersion().equals("3")) {
//            // 検索対象範囲情報を取得してEXISTS句を組み立てる
//            return createExistsQuery(pRequestHash, pSession, psCompanyColumn, psEmployeeColumn);
//        }
        // V4の処理
//        else {
//            // パラメータの法人、社員番号カラムからユーザIDカラムを取得する
//            String sUserIdColumn = getUserIdColumn(psEmployeeColumn);
//            // 検索対象範囲APIを呼び出してEXISTS句を取得する
//            String sExists = psBuildTargetSqlLogic.getExistsQuery(sUserIdColumn);
//            // 再取得判定用にEXISTS句を保存
//            pSession.setAttribute(TmgReferList.SESSION_KEY_SEARCHRANGE, sExists);
//            return sExists;
//        }
        return null;
    }

}
