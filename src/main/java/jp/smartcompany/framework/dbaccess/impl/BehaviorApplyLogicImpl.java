package jp.smartcompany.framework.dbaccess.impl;

import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.framework.dbaccess.BehaviorApplyLogic;
import jp.smartcompany.framework.sysboot.dto.MastDatadicSeclevelDTO;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BehaviorApplyLogicImpl implements BehaviorApplyLogic {
    /** ParameterNotFoundException（ドメインコード）エラー .*/
    public static final String ERR_NODATE_DOMAINCORD = "DomainID";

    /** ParameterNotFoundException（リレーションコード） .*/
    public static final String ERR_NODATE_RELATIONCORD =
            "RelationshipID";

    /** ParameterNotFoundException（カラムコード） .*/
    public static final String ERR_NODATE_COLUMNCORD = "ColumnName";

    /** 必須セッション情報未設定例外（ビヘイビア情報） .*/
    public static final String ERR_NODATE_BEHAVIOR = "loginBehaviors";

    /** 必須セッション情報未設定例外（顧客コード） .*/
    public static final String ERR_NODATE_CUSTOMERCODE =
            "loginCustomer";

    /** 必須常駐変数未設定例外(常駐変数[データディクショナリ情報]) .*/
    public static final String ERR_NODATE_DATADICTIONARYINFO =
            "DataDictionary";

    /** 必須常駐変数未設定例外(常駐変数[データディクショナリ機密レベル情報]) .*/
    public static final String ERR_NODATE_DATADIC_SECLEVELINFO =
            "DatadicSeclevel";

    /** 必須常駐変数不正例外(常駐変数[セキュリティレベル]) .*/
    public static final String ERR_NODATE_SECURITY_LEVEL =
            "DataDictionary(SecurityLevel)";

    /** 必須常駐変数不正例外(機密レベルマスタ.機密レベルコード]) .*/
    public static final String ERR_NODATE_MAST_SECURITY_LEVEL =
            "MAST_SECURITY_LEVEL.MSL_CLEVELID";

    /** アンダーライン */
    public static final String UNDER_LINE = "_";

    private final ScCacheUtil scCacheUtil;


    /**
     * <B>【マスキング判定処理】</B><BR>
     * データ項目（カラム単位）の表示権限の有無を取得します。.
     * @param psSystemID システムコード
     * @param psDomainID ドメインコード
     * @param psRelationID リレーションコード
     * @param psColumnId カラムコード
     * @return boolean カラム単位の表示権限可否
     */
    @Override
    public boolean isMask(final String psSystemID, final String psDomainID
            , final String psRelationID, final String psColumnId) {
        HttpServletRequest request = ContextUtil.getHttpRequest();
        assert request != null;
        HttpSession session = request.getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        // 参照権限有無
        boolean bResult = false;
        // ****************************************
        // パラメータチェック
        // ****************************************

        // システムコード
        if (this.checkParam(psSystemID)) {
            throw new GlobalException(PsConst.PARAM_KEY_SYSTEM);
        }
        // ドメインコード
        if (this.checkParam(psDomainID)) {
            throw new GlobalException(ERR_NODATE_DOMAINCORD);
        }
        // リレーションコード
        if (this.checkParam(psRelationID)) {
            throw new GlobalException(ERR_NODATE_RELATIONCORD);
        }
        // カラムコード
        if (this.checkParam(psColumnId)) {
            throw new GlobalException(ERR_NODATE_COLUMNCORD);
        }
        // PsSession.ビヘイビア情報
        if (psSession.getLoginBehaviors() == null) {
            throw new GlobalException(ERR_NODATE_BEHAVIOR);
        }
        // PsSession.顧客コード
        if (this.checkParam(psSession.getLoginCustomer())) {
            throw new GlobalException(ERR_NODATE_CUSTOMERCODE);
        }

        // ****************************************
        // 常駐変数（データディクショナリ情報）機密レベルを取得
        // ****************************************

        // データディクショナリ（常駐変数）情報取得
        MastDatadicSeclevelDTO oDataDicSeclevel = scCacheUtil.getDatadicSeclevel(
                psSystemID, psColumnId);
        // データディクショナリが設定されていない場合
        if (oDataDicSeclevel == null) {
            log.warn(BehaviorApplyLogicImpl.ERR_NODATE_DATADICTIONARYINFO
                    + " [SystemId=\"" + psSystemID + "\", " +
                    "ColumnId=\"" + psColumnId + "\"]");
            // 権限無しの判定を行う(処理を中断しない=例外をthrowしない)
            return false;
        }

        // データディクショナリ（常駐変数）情報より機密レベルを取得
        String sSecretlbl = oDataDicSeclevel.getMdslClevelid();

        // 機密レベルが設定されていない場合
        if (this.checkParam(sSecretlbl)) {
            log.warn(BehaviorApplyLogicImpl.ERR_NODATE_DATADIC_SECLEVELINFO
                    + " [SystemId=\"" + psSystemID + "\", " +
                    "ColumnId=\"" + psColumnId + "\"]");
            // 権限無しの判定を行う(処理を中断しない=例外をthrowしない)
            return false;
        }

        // ****************************************
        // セッション変数（ビヘイビア情報）より、参照権限の有無を取得
        // ****************************************
        // ビヘイビア情報取得
        Map<String,String> hBehaviors = psSession.getLoginBehaviors();
        // 参照権限有無情報取得
        Object oReference = hBehaviors.get(
                psSystemID
                        + BehaviorApplyLogicImpl.UNDER_LINE
                        + psDomainID
                        + BehaviorApplyLogicImpl.UNDER_LINE
                        + psRelationID
                        + BehaviorApplyLogicImpl.UNDER_LINE
                        + sSecretlbl);

        if (oReference == null || "".equals(oReference)) {
            // #259 ビヘイビアが未登録・未設定の場合の動作を未設定と同じようにする
            bResult = false;
        } else {
            bResult = Boolean.parseBoolean(oReference.toString());
        }

        // 結果を返却
        return bResult;

    }

    /**
     * パラメータチェック処理.
     * @param psParam パラメータ
     * @return bflg パラメータ有無チェック結果
     */
    private boolean checkParam(final String psParam) {

        boolean bflg = false;

        if (psParam == null || "".equals(psParam)) {
            bflg = true;
        }

        return bflg;
    }

}
