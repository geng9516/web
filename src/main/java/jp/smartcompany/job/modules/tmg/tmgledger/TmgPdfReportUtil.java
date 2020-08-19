package jp.smartcompany.job.modules.tmg.tmgledger;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.core.util.PsUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.springframework.util.ClassUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class TmgPdfReportUtil {


    // 定数
    /** LOG出力用ディスクリプタ */
    public static final String BEAN_DESC = "TmgPdfReportUtil";

    /** 定数：リクエスト */
    public static final String REQUEST_KEY_SERVLETCONTEXT = "ServletContext";

    /** 定数：帳票パラメータ「サブレポートパス」 */
    public static final String SUBREPORT_DIR	= "SUBREPORT_DIR";

    /** プロパティファイルパス */
    public static final String PATH_PROPERTIES  = "ps.c01.tmg.util.TmgPdfReportUtil";

    /** プロパティKey */
    public static final String PROP_NO_DATA_MGD_LEDGER    = "ERROR_NO_DATA_MGD_LEDGER";
    public static final String PROP_NO_DATA_MGD_JASPER    = "ERROR_NO_DATA_MGD_JASPER";
    public static final String PROP_NO_DATA_MGD_FILENAME  = "ERROR_NO_DATA_MGD_FILENAME";
    public static final String PROP_NO_FILE_JASPER        = "ERROR_NO_FILE_JASPER";
    public static final String PROP_NOT_CON_DB            = "ERROR_NOT_CON_DB";
    public static final String PROP_EXEC_JASPER_EXCEPTION = "ERROR_EXEC_JASPER_EXCEPTION";

    private static final String ADD_PAGE_NUMBER	= "ADD_PAGE_NUMBER";

    private String   gsId          = null;
    private String   gsDate        = null;
    private String   gsLang        = null;
    private Map      parameterMap  = new HashMap();
    private Map fileJasperMap = new HashMap();
    private PsDBBean goBean;
    private boolean  gbDownLoad    = false;

    //DataBaseConnection取得
    private String   gsDriver   = null;
    private String   gsUrl      = null;
    private String   gsUser     = null;
    private String   gsPassword = null;

    /**
     * メイン処理
     * @param   poPsDBBean        JavaBeanクラス
     * @param   psCustId          顧客コード
     * @param   psCompId          法人コード
     * @param   psBaseDate        基準日
     * @param   psMgLedgerSheetId 帳票出力する帳票識別ID
     * @param   psLang            言語区分
     * @throws  Exception
     */
    public TmgPdfReportUtil(PsDBBean poPsDBBean, String psCustId, String psCompId, String psBaseDate, String psMgLedgerSheetId, String psLang) throws Exception {

        //パラメータをメンバー変数へ保持
        this.setBean(poPsDBBean);
        this.setId(psMgLedgerSheetId);
        this.setDate(psBaseDate);
        this.setLang(psLang);

        //パラメータ(request)
        this.parameterMap.putAll(this.goBean.getRequestHash());

        //帳票定義を検索
        Vector vecQuery = new Vector();
        vecQuery.add(this.buildSQLForSelectConfiguration(
                this.goBean.toDBString(psCustId),
                this.goBean.toDBString(psCompId),
                this.goBean.toDBDate(psBaseDate),
                this.goBean.toDBString(TmgUtil.Cs_MGD_LEDGER_PDF_DOWNLOAD + "|" + psMgLedgerSheetId),
                this.goBean.toDBString(this.gsLang)
        ));

        // 検索結果取得
        PsResult psResult = this.goBean.getValuesforMultiquery(vecQuery, BEAN_DESC);

        // 検索結果が無い場合
        if (psResult.getResult().isEmpty()) {
            // エラーメッセージ：名称マスタの設定が取得出来ません。帳票定義の設定を確認して下さい。
            throw new Exception(getPropMsg(PROP_NO_DATA_MGD_LEDGER));
        }

        // 検索結果クエリ分処理を行う
        for (Iterator queryIte = psResult.getResult().iterator(); queryIte.hasNext();) {

            Vector recodeVec = (Vector)queryIte.next();

            // レコードが空の場合
            if (recodeVec.isEmpty()) {
                // エラーメッセージ：名称マスタの設定が取得出来ません。帳票定義の設定を確認して下さい。
                throw new Exception(getPropMsg(PROP_NO_DATA_MGD_LEDGER));
            }

            //AppRootパス取得
            String sAppRootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

            // 検索結果レコード分処理を行う
            for (Iterator recodeIte = recodeVec.iterator(); recodeIte.hasNext();) {

                Vector columnVev         = (Vector)recodeIte.next();
                String sJasperPath       = (String) columnVev.get(0); // jasperファイルパス
                String sDownloadFileName = (String) columnVev.get(1); // ダウンロード時ファイル名

                // jasperファイルの定義が登録されていない場合
                if (this.isEmpty(sJasperPath)) {
                    // エラーメッセージ：.jasperが名称マスタへ設定されていません。帳票定義の設定を確認して下さい。
                    throw new Exception(getPropMsg(PROP_NO_DATA_MGD_JASPER));
                }

                // パス名を環境に合わせ修正
                sJasperPath	= adjustPath(sAppRootPath + sJasperPath);

                //帳票定義ファイル(.jasper)
                File fileJasper	=	new File(sJasperPath);
                if (!fileJasper.isFile()) {

                    // エラーメッセージ：～が存在しません。帳票定義の存在を確認して下さい。
                    String sErrMsg = PsUtil.getPsUtil().getpropertyvalue(this.gsLang, PROP_NO_FILE_JASPER, PATH_PROPERTIES);
                    throw new JRException(fileJasper.getName() + sErrMsg);
                }

                if (!this.isEmpty(sDownloadFileName)) {
                    List fileJasperList = (List)fileJasperMap.get(sDownloadFileName);
                    if (fileJasperList == null) {
                        fileJasperList = new ArrayList();
                    }
                    fileJasperList.add(fileJasper);
                    fileJasperMap.put(sDownloadFileName, fileJasperList);
                }
            }

            break;
        }

        if (fileJasperMap.isEmpty()) {

            // エラーメッセージ：ダウンロード時のファイル名が設定されていません。帳票定義の設定を確認して下さい。
            throw new Exception(getPropMsg(PROP_NO_DATA_MGD_FILENAME));
        }

        //DataBaseConnection取得（システムプロパティ）
        this.gsDriver   = this.goBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_JASPERREPORTDB_DRIVER);
        this.gsUrl      = this.goBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_JASPERREPORTDB_URL);
        this.gsUser     = this.goBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_JASPERREPORTDB_USER);
        this.gsPassword = this.goBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_JASPERREPORTDB_PASSWORD);

    }

    /**
     * .jasperファイルを実行し、PDF形式にバイナリ情報を取得
     *
     * @return
     * @throws JRException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public byte[] runReportToPdf(String psFileName,HttpServletResponse response) throws ClassNotFoundException, SQLException, JRException {

        Connection con = null;
        byte[] bytePdf = null;

        try {

            try {
                // Oracle JDBC Driverのロード
                Class.forName(this.gsDriver);
                // 接続
                con = DriverManager.getConnection(this.gsUrl, this.gsUser, this.gsPassword);
            } catch (ClassNotFoundException e) {

                // エラーメッセージ：帳票出力用のデータベースに接続出来ません。システムプロパティの接続設定を確認して下さい。
                throw new ClassNotFoundException(getPropMsg(PROP_NOT_CON_DB));
            } catch (SQLException e) {

                // エラーメッセージ：帳票出力用のデータベースに接続出来ません。システムプロパティの接続設定を確認して下さい。
                throw new SQLException(getPropMsg(PROP_NOT_CON_DB));
            }
            JasperPrint oJasperPrint = new JasperPrint();
            //.jasperファイルを実行し、PDF形式にバイナリ情報を取得
            try {
                //複数の.jasperファイルを出力する際に2つ目以降のページ開始位置を保持
                int nAddPageNumber = 0;

                List	jasperFileList	=  (List)   this.fileJasperMap.get(this.getFileName());
                List jasperPrintList	=  new ArrayList();

                for (Iterator fileJasperIte = jasperFileList.iterator(); fileJasperIte.hasNext();) {

                    File fileJasper = (File)fileJasperIte.next();

                    oJasperPrint = JasperFillManager.fillReport(fileJasper.getAbsolutePath(), this.parameterMap, con);

                    //ページ数を保持
                    int nPageNumber = oJasperPrint.getPages().size();

                    //ページ数ゼロは出力しない(空ページの抑制)
                    if (nPageNumber != 0) {

                        //次のページ開始位置を保持
                        nAddPageNumber = nAddPageNumber + oJasperPrint.getPages().size();
                        this.parameterMap.put(ADD_PAGE_NUMBER, nAddPageNumber);

                        //1つの.jasperファイル分を出力
                        jasperPrintList.add(oJasperPrint);

                    }
                }

                JRPdfExporter jrPdfExporter		= new JRPdfExporter();
                ByteArrayOutputStream outStream	= new ByteArrayOutputStream();

                // PDFデータの出力先を設定
                jrPdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);

                //Listに追加したJasperPrintデータをJASPER_PRINT_LISTに設定する
                jrPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);

                // JASPER_PRINT_LISTに設定されたデータを元に、outStreamに出力
                jrPdfExporter.exportReport();

                //outStreamからbyte[]に変換して戻り値に返す
                bytePdf = outStream.toByteArray();

            } catch (JRException e) {

                // エラーメッセージ：jasperファイルを実行中に問題が発生しました。
                throw new JRException(getPropMsg(PROP_EXEC_JASPER_EXCEPTION));
            }

        } finally {
            if (con != null) {
                con.close();
            }
        }

        return bytePdf;

    }

    /**
     * 帳票PDFダウンロード定義取得用クエリ
     *
     * @param psDBCustId        顧客コード
     * @param psDBCompId        法人コード
     * @param psDBBaseDate      基準日
     * @param psDBMgdMasterCode マスタコード
     * @param psDBLang          言語区分
     * @return String 帳票PDFダウンロード定義取得クエリ
     */
    private String buildSQLForSelectConfiguration(String psDBCustId, String psDBCompId, String psDBBaseDate, String psDBMgdMasterCode, String psDBLang) {

        StringBuffer sSql = new StringBuffer();

        sSql.append(" SELECT ");
        sSql.append("     MGD_JASPER_FILE_PATH, ");   // 帳票定義ファイル(.jasper)PATH
        sSql.append("     MGD_DOWNLOAD_FILE_NAME  "); // sDownloadファイル名
        sSql.append(" FROM ");
        sSql.append("     TMG_V_MGD_LEDGER_PDF_DWLD ");
        sSql.append(" WHERE ");
        sSql.append("     MGD_CCUSTOMERID       = " + psDBCustId);
        sSql.append(" AND MGD_CCOMPANYID_CK_FK  = " + psDBCompId);
        sSql.append(" AND MGD_PDF_DOWNLOAD_CODE = " + psDBMgdMasterCode);
        sSql.append(" AND MGD_CLANGUAGE_CK      = " + psDBLang);
        sSql.append(" AND " + psDBBaseDate + " BETWEEN MGD_DSTART_CK AND MGD_DEND ");

        return sSql.toString();
    }

    /**
     * 帳票ダウンロード(PDFファイル)を行う。
     *
     * @param psFileName ダウンロードファイル名
     * @param pbytePdf   ダウンロードファイル（Byte）
     * @throws JRException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void downloadForPDF(String psFileName, byte[] pbytePdf,HttpServletResponse response) throws JRException, ClassNotFoundException, SQLException, IOException {

        if (!isEmpty(pbytePdf)) {
            Properties pro = System.getProperties();
            response.setCharacterEncoding(pro.getProperty("file.encoding"));
            response.setHeader("Content-Type", "application/pdf;charset=UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "application");
            response.setHeader("filename", URLEncoder.encode(psFileName, "UTF-8"));
            response.setContentLength(pbytePdf.length);

            ServletOutputStream sosStream = response.getOutputStream();
            if(pbytePdf == null) {
            }
            else {
                sosStream.write(pbytePdf);
                sosStream.close();
                sosStream.flush();
            }
            // ダウンロード完了フラグをtrueにする。
            this.gbDownLoad = true;
        }
        // ダウンロード完了フラグをfalseにする。
        this.gbDownLoad = false;
    }

    /**
     * 環境に合わせ、パス名のセパレータを調整する。
     *
     * @param  psPath パス名
     * @return String 調整後パス名
     */
    private String adjustPath(String psPath) {

        String sAjustPath = psPath;

        if (File.separator.equals("\\")) {
            //Windows環境の場合(セパレータ=\なので/を\に変換)
            sAjustPath = PsUtil.getPsUtil().ReplaceString(sAjustPath, "/", File.separator);
        } else if (File.separator.equals("/")) {
            //Linux環境の場合(セパレータ=/なので\を/に変換)
            sAjustPath = PsUtil.getPsUtil().ReplaceString(sAjustPath, "\\", File.separator);
        }

        return sAjustPath;
    }

    /**
     * プロパティファイルから指定のキーに該当するメッセージを取得する
     *
     * @param psPropKey
     * @return プロパティファイルに定義してあるメッセージ
     */
    private String getPropMsg(String psPropKey) {

        return  PsUtil.getPsUtil().getpropertyvalue(this.gsLang, psPropKey, PATH_PROPERTIES);
    }

    /**
     * 指定のバイトデータが空かどうか判定します。
     *
     * @param pbyteBinary
     * @return 空の場合、trueを返します。
     */
    private boolean isEmpty(byte[] pbyteBinary) {
        if (pbyteBinary == null) {
            return true;
        }
        if (pbyteBinary.length <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 指定の文字列が空かどうか判定します。
     *
     * @param psValue
     * @return 空の場合、trueを返します。
     */
    private boolean isEmpty(String psValue) {
        if (psValue == null) {
            return true;
        }
        if (psValue.equals("")) {
            return true;
        }
        return false;
    }

    public String getId() {
        return gsId;
    }

    public void setId(String psId) {
        this.gsId = psId;
    }

    public void setBean(PsDBBean poBean) {
        this.goBean = poBean;
    }

    public String getDate() {
        return gsDate;
    }

    public void setDate(String psDate) {
        this.gsDate = psDate;
    }

    public void setLang(String psLang) {
        this.gsLang = psLang;
    }

    /**
     * 名称マスタに定義されている、ダウンロードファイル名称を取得する。
     * @return ファイル名
     */
    public String getFileName() {
        return (String) this.fileJasperMap.keySet().iterator().next();
    }

    /**
     * 帳票出力用パラメータ設定を追加する
     */
    public void addReportParam(String psKey, String psVal) {
        this.parameterMap.put(psKey, psVal);
    }

    /**
     * 帳票出力用パラメータ設定を全て追加する
     */
    public void addAllReportParam(HashMap poMapReportParam) {
        this.parameterMap.putAll(poMapReportParam);
    }

    /**
     * 帳票出力用パラメータ設定から指定パラメータを削除する
     */
    public void removeReportParam(String psKey) {
        this.parameterMap.remove(psKey);
    }

    /**
     * サブレポートの配置フォルダパスのパラメータ設定を行う。
     * @param psSubReportDirPath フォルダパス
     */
    public void setSubReportDirParam(String psSubReportDirPath) {
        this.parameterMap.put(SUBREPORT_DIR, psSubReportDirPath);
    }

    /**
     * ダウンロードが出来たかどうか判定する。
     * @return ダウンロード成功：true、ダウンロード失敗（ダウンロード未実施時も同様）：false
     */
    public boolean isDownload() {

        return this.gbDownLoad;
    }

}
