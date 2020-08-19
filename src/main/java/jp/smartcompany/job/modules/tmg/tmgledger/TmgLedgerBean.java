package jp.smartcompany.job.modules.tmg.tmgledger;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerParamVO;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.ListBoxVo;
import jp.smartcompany.job.modules.tmg.util.TmgPdfReportUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TmgLedgerBean {

    /** 帳票出力用ディスクリプタ */
    private static final String BEAN_DESC = "TmgLedger";
    /** 帳票出力パラメータ */
    private TmgLedgerParam tmgLedgerParam;
    private Map      parameterMap  = new HashMap();

    private final IMastGenericDetailService iMastGenericDetailService;
    private final ITmgMonthlyService iTmgMonthlyService;


    /**
     * 帳票種別リストボックスのデータを取得するクエリ文を生成します。
     */
    public List<LedgerSheetVo> getLedgerSheetList(PsDBBean psDBBean){
        List<LedgerSheetVo> ledgerSheetVoList = iMastGenericDetailService.selectLedgerSheetList(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getLanguage());
        return ledgerSheetVoList;
    }

    /**
     * 対象年リストボックスのデータを取得するクエリ文を生成します。
     */
    public List<ListBoxVo> getYearlist(PsDBBean psDBBean){
        List<ListBoxVo> yearList = iTmgMonthlyService.selectYearDate(psDBBean.getCustID(),psDBBean.getCompCode());
        return yearList;
    }
    /**
     * 対象年月リストボックスのデータを取得するクエリ文を生成します。
     */
    public List<ListBoxVo> getMonthlist(PsDBBean psDBBean){
        List<ListBoxVo> monthList = iTmgMonthlyService.selectMonthDate(psDBBean.getCustID(),psDBBean.getCompCode());
        return monthList;
    }

    /**
     * メインメソッド
     */
    public void execute(HttpServletRequest request, PsDBBean psDBBean, LedgerParamVO ledgerParamVO, HttpServletResponse response) throws Exception {

        // 初期化処理
        psDBBean.setSysControl((Hashtable<String, Object>) psDBBean.getRequestHash());

        // パラメータ設定
        TmgLedgerParam param = getLedgerParam(psDBBean,ledgerParamVO);

        // アクション制御処理
        this.controlAction(param,psDBBean,response);

    }

    // =========================================================================
    // 各アクション コントロール
    // =========================================================================
    /**
     *
     * アクション制御処理 アクションにより処理を振り分けます
     *
     * @throws Exception 帳票出力処理時例外
     */
    public void controlAction(TmgLedgerParam param,PsDBBean psDBBean, HttpServletResponse response) throws Exception {

        // アクション
        String action = param.getAction();

        /*
         *  アクションによって、処理の振り分け
         */
        // // PDF帳票ダウンロード
        if (TmgLedgerConst.ACT_LEDGER_PDF_DOWNLOAD.equals(action)) {

            executePdfDownload(param,psDBBean, response);

            // CSVファイルダウンロード
        } else if (TmgLedgerConst.ACT_LEDGER_CSV_DOWNLOAD.equals(action)) {

            executeCsvDownload(param,psDBBean);
        }

        // パラメータの格納
        setTmgLedgerParam(param);

        return;
    }

    /**
     * 帳票出力パラメータの基本情報設定を行ったパラメータクラスを取得する。
     * @return TmgLedgerParam 帳票出力パラメータ
     */
    private TmgLedgerParam getLedgerParam(PsDBBean psDBBean,LedgerParamVO ledgerParamVO) {

        TmgLedgerParam param = new TmgLedgerParam();

        // 各種パラメータの設定
        // 基本パラメータ
        param.setAction(ledgerParamVO.getTxtAction()); // アクション
        param.setCustomerID(psDBBean.getCustID());  // 顧客コード
        param.setCompanyID(psDBBean.getCompCode()); // 法人コード
        param.setLanguage(psDBBean.getLanguage());  // 言語区分

        // 帳票ダウンロード時使用パラメータ
        param.setEmpID(ledgerParamVO.getHidEmpId());        // 対象職員
        param.setOrgID(ledgerParamVO.getHidOrgId());        // 対象部署
        param.setYYYY(ledgerParamVO.getTxtYYYY());          // 対象年
        param.setYYYYMM(ledgerParamVO.getTxtYYYYMM());      // 対象年月
        param.setTermFrom(ledgerParamVO.getTxtTermFrom()); // 対象期間・開始日
        param.setTermTo(ledgerParamVO.getTxtTermTo());     // 対象期間・終了日
        param.setsLedgerSheetId(ledgerParamVO.getTxtLedgerSheetId());// 選択帳票種別ID

        // 出勤簿（年単位）の表示開始月、表示終了月を取得する。
        String[] sAtdBookTerm = getTermForAtdBookYear(ledgerParamVO.getTxtYYYY(),ledgerParamVO.getTxtAtdBookTermFrom());

        param.setAtdBookTermFrom(sAtdBookTerm[0]); // 出勤簿（年単位）・表示開始月
        param.setAtdBookTermTo(sAtdBookTerm[1]);   // 出勤簿（年単位）・表示終了月

        // 超過勤務実施状況リストの「法定内超勤を含める」フラグを設定（0：含めない、1：含める）
        param.setsIncludeOt100Flg(ledgerParamVO.getTxtIncludeOt100Flg());

        return param;
    }

    /**
     * PDFファイルをダウンロードする。
     *
     * @param  param 帳票出力パラメータクラス
     * @throws Exception PDFファイルダウンロード処理時例外
     */
    private void executePdfDownload(TmgLedgerParam param,PsDBBean psDBBean, HttpServletResponse response) throws Exception {

        try {

            // 帳票へ渡すパラメータの設定
            HashMap mapReportParam = getPdfReportParam(param);

            // PDFレポートユーティリティクラスの生成
            TmgPdfReportUtil pdfReportUtil = new TmgPdfReportUtil(
                    psDBBean,
                    param.getCustomerID(),  // 顧客コード
                    param.getCompanyID(),   // 法人コード
                    getToday(psDBBean),             // 処理日
                    param.getsLedgerSheetId(),    // 出力帳票ID
                    param.getLanguage());   // 言語区分

            // 帳票出力に使用するパラメータを設定する。
            pdfReportUtil.addAllReportParam(mapReportParam);

            // PDFファイル取得（byte）
            byte[] bytePdf = pdfReportUtil.runReportToPdf(pdfReportUtil.getFileName(),response);

            // ファイルダウンロード
            pdfReportUtil.downloadForPDF(pdfReportUtil.getFileName(), bytePdf,response);

        } catch (Exception e) {
            // PDFファイルda
            e.printStackTrace();

            throw e;
        }

    }

    /**
     * 出勤簿（年単位）の表示対象期間を取得する。
     *
     * @param sYear          対象年
     * @param sDispMonthFrom 表示開始月
     * @return String[] 表示開始月（Index:0）、表示終了月（Index:1）
     */
    private String[] getTermForAtdBookYear(String sYear, String sDispMonthFrom) {

        String[] sTerms = {"", ""};

        // パラメータデータ無い場合は、表示開始月の計算を行わない。
        if (!StrUtil.hasEmpty(sYear) && !StrUtil.hasEmpty(sDispMonthFrom)) {

            SimpleDateFormat oDtFormat = new SimpleDateFormat("yyyy/MM/dd");

            // 表示開始日付
            Calendar calFrom = Calendar.getInstance();
            calFrom.set(Integer.parseInt(sYear), Integer.parseInt(sDispMonthFrom)-1, 1); // カレンダークラスの月は0から始まるので、-1する。
            sTerms[0] = oDtFormat.format(calFrom.getTime());

            // 表示終了日付
            Calendar calTo = Calendar.getInstance();
            calTo.set(Integer.parseInt(sYear), Integer.parseInt(sDispMonthFrom)-1, 1); // カレンダークラスの月は0から始まるので、-1する。
            calTo.add(Calendar.MONTH, 11); // 12か月後の日付へ変更
            sTerms[1] = oDtFormat.format(calTo.getTime());
        }

        return sTerms;
    }

    /**
     * CSVファイルをダウンロードする。
     *
     * @param  param 帳票出力パラメータクラス
     * @throws Exception CSVファイルダウンロード処理時例外
     */
    private void executeCsvDownload(TmgLedgerParam param,PsDBBean psDBBean) throws Exception {
        try {

            // ダウンロードデータ検索
            Vector vQuery = new Vector(); // クエリ格納オブジェクト
            String sDBCustId   = psDBBean.escDBString(param.getCustomerID());
            String sDBCompId   = psDBBean.escDBString(param.getCompanyID());
            String sDBLang     = psDBBean.escDBString(param.getLanguage());
            String sDBMasterId = psDBBean.escDBString(TmgUtil.Cs_MGD_LEDGER_CSV_DOWNLOAD + "|" + psDBBean.getReqParm(TmgLedgerConst.REQ_TARGET_LEDGER_SHEET));

            // 0 CSVダウンロード設定　検索
            vQuery.add(buildSQLForCSVDownloadInfo(sDBCustId, sDBCompId, sDBMasterId, sDBLang));

            // 検索結果取得
            PsResult result = (PsResult)psDBBean.getValuesforMultiquery(vQuery, BEAN_DESC);

            Vector vecResult  = result.getResult();
            Vector vecData    = (Vector)vecResult.get(0); // クエリ０番目の検索結果取得
            Vector vecRec     = (Vector)vecData.get(0);   // 検索結果の１レコード目を取得
            String sClassName = (String)vecRec.get(0);    // 検索結果の１項目目（クラス名）を取得
            String sFileName  = (String)vecRec.get(1);    // 検索結果の１項目目（ファイル名）を取得

            // 選択した帳票のダウンロードクラスのインスタンスを取得。
            TmgLedgerCsvDl csvDl = (TmgLedgerCsvDl)Class.forName(sClassName).newInstance();

            csvDl.setParam(psDBBean, param); // パラメータ情報を設定
            csvDl.download(sFileName);   // ＣＳＶファイルをダウンロード

        } catch (Exception e) {
            e.printStackTrace();
        throw e;}
    }

    /**
     * CSVダウンロード設定を検索するクエリを生成します。
     *
     * @param  sCustId   顧客コード
     * @param  sCompId   法人コード
     * @param  sMasterId 帳票マスタコード
     * @param  sLang     言語区分
     * @return SQL文
     */
    private String buildSQLForCSVDownloadInfo(String sCustId, String sCompId, String sMasterId, String sLang) {

        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" SELECT ");
        sbSQL.append("     MGD_CLASS_FILE_NAME, ");
        sbSQL.append("     MGD_DOWNLOAD_FILE_NAME ");
        sbSQL.append(" FROM ");
        sbSQL.append("     TMG_V_MGD_LEDGER_CSV_DWLD ");
        sbSQL.append(" WHERE ");
        sbSQL.append("     MGD_CCUSTOMERID       = " + sCustId);
        sbSQL.append(" AND MGD_CCOMPANYID_CK_FK  = " + sCompId);
        sbSQL.append(" AND SYSDATE BETWEEN MGD_DSTART_CK AND MGD_DEND ");
        sbSQL.append(" AND MGD_CLANGUAGE_CK      = " + sLang);
        sbSQL.append(" AND MGD_CSV_DOWNLOAD_CODE = " + sMasterId);

        return sbSQL.toString();
    }

    /**
     * 処理日を取得
     * @return String 処理日（YYYY/MM/DD）
     */
    private String getToday(PsDBBean psDBBean) {

        return psDBBean.getSysDate().substring(0, 10).replaceAll("-", "/");
    }

    /**
     * 帳票出力パラメータの取得
     * @return TmgLedgerParam 帳票出力パラメータ
     */
    public TmgLedgerParam getTmgLedgerParam() {

        return this.tmgLedgerParam;
    }

    /**
     * 帳票出力パラメータの設定
     * @param param 帳票出力パラメータ
     */
    public void setTmgLedgerParam(TmgLedgerParam param) {

        this.tmgLedgerParam = param;
    }

    /**
     * 帳票ダウンロード時のパラメータ設定を行う。
     * @param param 帳票出力パラメータクラス
     * @return HashMap
     */
    private HashMap getPdfReportParam(TmgLedgerParam param) throws FileNotFoundException {

        HashMap mapParam = new HashMap();

        mapParam.put(TmgLedgerConst.PARAM_CUST_ID,           param.getCustomerID());      // 顧客ID
        mapParam.put(TmgLedgerConst.PARAM_COMP_ID,           param.getCompanyID());       // 法人ID
        mapParam.put(TmgLedgerConst.PARAM_LANG,              param.getLanguage());        // 言語区分
        mapParam.put(TmgLedgerConst.PARAM_EMP_ID,            param.getEmpID());           // 対象職員番号
        mapParam.put(TmgLedgerConst.PARAM_ORG_ID,            param.getOrgID());           // 対象組織コード
        mapParam.put(TmgLedgerConst.PARAM_TARGET_YYYY,       param.getYYYY());            // 対象年
        mapParam.put(TmgLedgerConst.PARAM_TARGET_YYYYMM,     param.getYYYYMM());          // 対象年月
        mapParam.put(TmgLedgerConst.PARAM_TARGET_TERM_FROM,  param.getTermFrom());        // 対象期間・開始日
        mapParam.put(TmgLedgerConst.PARAM_TARGET_TERM_TO,    param.getTermTo());          // 対象期間・終了日
        mapParam.put(TmgLedgerConst.PARAM_ATDBOOK_TERM_FROM, param.getAtdBookTermFrom()); // 出勤簿(年)・表示開始月
        mapParam.put(TmgLedgerConst.PARAM_ATDBOOK_TERM_TO,   param.getAtdBookTermTo());   // 出勤簿(年)・表示終了月
        mapParam.put(TmgLedgerConst.PARAM_OT100FLG,          param.getsIncludeOt100Flg());// 超過勤務実施状況リスト・法定内超勤を含める
        mapParam.put(TmgLedgerConst.SUBREPORT_DIR,           getReportDir());             // サブレポートディレクトリ

        return mapParam;
    }

    private String getReportDir() {

        // サブレポートの配置ディレクトリを取得
        String sReportDirPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + TmgLedgerConst.DIRPATH_REPORTFILES;

        return sReportDirPath;
    }
}
