package jp.smartcompany.framework.compatible.business;

import jp.smartcompany.framework.sysboot.dto.SystemPropertyDTO;
import jp.smartcompany.job.modules.core.util.PsResult;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public interface Version3CompatibleLogic {

    /**
     * システムコードを返します
     * @return String システムコード
     */
    String getSystemCode();

    /**
     * SELECT文を実行します
     * @param vecQuery クエリ Vector
     * @param sUserid ユーザコード
     * @param vPostid 役職コード Vector
     * @param sGroupid グループコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param bApplypermission データアクセス制御の適用フラグ
     * @param sTargetuser 検索対象ユーザコード
     * @param vDept 検索者の組織コード Vector
     * @param sCompid 検索者の法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @param sDomainid ドメインコード
     * @param sDate 検索基準日
     * @param vSectionid 検索者組織コード（組織用）
     * @param sTarcomp 検索対象者の法人コード
     * @param sTarsection 検索対象者の組織コード
     * @param httpSession セッション情報
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
     PsResult SelectQuerywithPermission(
             Vector vecQuery,  String sUserid,
             Vector vPostid,  String sGroupid,
             String sBeandesc,
             boolean bApplypermission,
             String sTargetuser,  Vector vDept,
             String sCompid,  String sCustid,
             String sSystemCode,  String strGUID,
             String sDomainid,  String sDate,
             Vector vSectionid,  String sTarcomp,
             String sTarsection,
             HttpSession httpSession) throws Exception;

    /**
     * 名称マスタ一覧取得
     * @param sCustid 顧客コード
     * @param sCompid 法人コード
     * @param sLanguage 言語区分
     * @param sDate 基準日
     * @param sCode 名称マスタコード
     * @return PsResult 結果セット
     */
     PsResult SelectSpecificComboQuerywithoutPermission(
             String sCustid,  String sCompid,
             String sLanguage,  String sDate,  String sCode);

    /**
     * 更新SQL実行
     * @param vecQuery SQL文のVector
     * @param sUserid ユーザコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param sCompid 検索者の法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @return int 更新件数
     */
     int setInsertValues( Vector vecQuery,  String sUserid,
                                String sBeandesc,  String sCompid,
                                String sCustid,    String sSystemCode,
                                String strGUID);

    /**
     * 更新用SQLを実行します。（BLOB対応版）
     * @param vecQuery SQL文のVector
     * @param vecParams 各SQLに渡すBLOBデータのVector
     * @param sUserId ユーザコード
     * @param sBeanDesc ログ出力で用いる説明文字列
     * @param sCompId 検索者の法人コード
     * @param sCustId 顧客コード
     * @param sSystemCode システムコード
     * @param sGUID	 GUID
     * @return int 更新件数
     */
     int setInsertValuesForBlob( Vector vecQuery,  Vector vecParams,
                                       String sUserId,   String sBeanDesc,
                                       String sCompId,   String sCustId,
                                       String sSystemCode,  String sGUID);

    /**
     * 複数SELECT文を実行します
     * @param vecQuery クエリ Vector
     * @param sUserid ユーザコード
     * @param vPostid 役職コード Vector
     * @param sGroupid グループコード
     * @param vPostweightage 役職重み Vector
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param bApplypermission データアクセス制御の適用フラグ
     * @param sTargetuser 検索対象ユーザコード
     * @param vDept 検索対象組織コード Vector
     * @param sCompid 法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
     PsResult executeSelectQueryBatch(
             Vector vecQuery,  String sUserid,
             Vector vPostid,   String sGroupid,
             Vector vPostweightage,      String sBeandesc,
             boolean bApplypermission,  String sTargetuser,
             Vector vDept,    String sCompid,
             String sCustid,  String sSystemCode,
             String strGUID
    ) throws Exception;

    /**
     * SELECT文実行（一意）
     * @param vecQuery クエリVector
     * @param sCustid 顧客コード
     * @param sCompid 検索者の法人コード
     * @param sUserid ユーザコード
     * @param sGroupid グループコード
     * @param sTargetcustid 検索対象者の顧客コード
     * @param sTargetcompid 検索対象者の法人コード
     * @param sTargetuserorsection 検索対象のユーザコードまたは組織コード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param sSystemCode システムコード
     * @param sDomainid ドメインコード
     * @param strGUID GUID
     * @param sDate 検索基準日
     * @param httpSession セッション情報
     * @return Vector 結果セット
     * @throws Exception システム例外
     */
     PsResult SelectQuerywithTargetUser(
             Vector vecQuery,      String sCustid,
             String sCompid,        String sUserid,
             String sGroupid,       String sTargetcustid,
             String sTargetcompid,  String sTargetuserorsection,
             String sBeandesc,      String sSystemCode,
             String sDomainid,      String strGUID,
             String sDate,
             HttpSession httpSession) throws Exception;

    /**
     * SELECT文（複数対象）を実行します
     * @param vecQuery クエリVector
     * @param sUserid ユーザコード
     * @param vPostid 役職コード Vector
     * @param sGroupid グループコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param sTargetuser 検索対象ユーザコード
     * @param vDept 検索者の組織コード Vector
     * @param sCompid 検索者の法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @param sDomainid ドメインコード
     * @param sDate 検索基準日
     * @param vSectionid 検索者組織コード（組織用）
     * @param sTarcomp 検索対象者の法人コード
     * @param sTarsection 検索対象者の組織コード
     * @param httpSession セッション情報
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
     PsResult SelectMultiQuerywithPermission(
             Vector vecQuery,   String sUserid,     Vector vPostid,
             String sGroupid,     String sBeandesc,   String sTargetuser,
             Vector vDept,        String sCompid,     String sCustid,
             String sSystemCode,  String strGUID,    String sDomainid,
             String sDate,        Vector vSectionid,  String sTarcomp,
             String sTarsection,  HttpSession httpSession
    ) throws Exception;

    /**
     * セキュリティ判定なしでSELECT文を実行しPsResultとして返します。
     * @param pvSQL クエリVector
     * @param psCustid 顧客コード
     * @param psCompid 検索者の法人コード
     * @param sUserid ユーザコード
     * @param sGroupid グループコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param psSystemCode システムコード
     * @param psGUID GUID
     * @param pbDesigSW	 true:SQL先頭にDesig4項目あり/false:Desig4項目なし
     * @return	PsResult 結果セット
     * @throws Exception システム例外
     */
     PsResult executeMultiQuery(
             Vector pvSQL,         String psCustid,
             String psCompid,      String sUserid,
             String sGroupid,      String sBeandesc,
             String psSystemCode,  String psGUID,
             boolean pbDesigSW
    ) throws Exception;

    /**
     * ストアドプロシージャ実行
     * @param sCompid 検索者の法人コード
     * @param sUserid ユーザコード
     * @param sSystemCode システムコード
     * @param sGUID GUID
     * @param vSQL SQL文のVector
     * @param vParams パラメータのVector
     * @return boolean 更新結果
     * @throws Exception システム例外
     */
     boolean executeProcedure(
             String sCompid,  String sUserid,
             String sSystemCode,  String sGUID,
             Vector vSQL, 	 Vector vParams) throws Exception;

    /**
     * 所属長を取得します
     * @param sCustid 検索対象顧客コード
     * @param sCompid 検索対象法人コード
     * @param sDeptid 検索対象組織コード
     * @param sDate 検索基準日
     * @param bIncludeActual 仮想兼務の適用可否フラグ
     * @return PsResult 所属長情報を含む結果セット
     */
     PsResult checkforchief(
             String sCustid,  String sCompid,
             String sDeptid,  String sDate,
             boolean bIncludeActual);

    /**
     * 法人一覧取得
     * @param sCustid 顧客コード
     * @param sLanguage 言語区分
     * @param sDate 基準日
     * @return PsResult 結果セット
     */
     PsResult getCompanyInfo(
             String sCustid,  String sLanguage,  String sDate);

    /**
     * システム日付取得
     * @return String 日付（YYYY/MM/DD形式）
     */
     String getDataBaseDate();

    /**
     * SELECT文（複数対象行範囲あり）を実行します
     * @param vecQuery クエリVector
     * @param sUserid ユーザコード
     * @param vPostid 役職コード Vector
     * @param sGroupid グループコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param sTargetuser 検索対象ユーザコード
     * @param vDept 検索者の組織コード Vector
     * @param sCompid 検索者の法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @param sDomainid ドメインコード
     * @param sDate 検索基準日
     * @param vSectionid 検索者組織コード（組織用）
     * @param sTarcomp 検索対象者の法人コード
     * @param sTarsection 検索対象者の組織コード
     * @param sPageStart 開始行
     * @param sPageEnd 終了行
     * @param httpSession セッション情報
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
     PsResult SelectMultiQuerywithPermission(
             Vector vecQuery,    String sUserid,
             Vector vPostid,     String sGroupid,
             String sBeandesc,   String sTargetuser,
             Vector vDept, 	   String sCompid,
             String sCustid,     String sSystemCode,
             String strGUID,     String sDomainid,
             String sDate,       Vector vSectionid,
             String sTarcomp,    String sTarsection,
             String sPageStart,  String sPageEnd,
             HttpSession httpSession
    ) throws Exception;

    /**
     * 条件検索用SELECT文実行
     * @param vecQuery クエリVector
     * @param sUserid ユーザコード
     * @param vPostid 役職コード Vector
     * @param sGroupid グループコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param sTargetuser 検索対象ユーザコード
     * @param vDept 検索者の組織コード Vector
     * @param sCompid 検索者の法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @param sDomainid ドメインコード
     * @param sDate 検索基準日
     * @param vSectionid 検索者組織コード（組織用） Vector
     * @param sTarcomp 検索対象者の法人コード
     * @param sTarsection 検索対象者の組織コード
     * @param sPageStart 開始行
     * @param sPageEnd 終了行
     * @param httpSession セッション情報
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
     PsResult SelectMultiQuerywithPermissionForJk(
             Vector vecQuery,    String sUserid,
             Vector vPostid,     String sGroupid,
             String sBeandesc,   String sTargetuser,
             Vector vDept,       String sCompid,
             String sCustid,     String sSystemCode,
             String strGUID,     String sDomainid,
             String sDate,       Vector vSectionid,
             String sTarcomp,    String sTarsection,
             String sPageStart,  String sPageEnd,
             HttpSession httpSession
    ) throws Exception;

    /**
     * DebugMode取得
     * @return boolean DebugMode(YES):true、DebugMode(それ以外):false
     */
     boolean isDebugModeEnabled();

    /**
     * データベースモードを取得します。
     * @return String データベースモード：(1:Oracle)
     */
     String getDatabaseMode();

    /**
     * リレーションを取得します
     * @param sCustomerID 顧客コード
     * @param sLoginCompanyID ログイン法人コード
     * @param sLoginUserID ログイン社員番号
     * @param sTargetCompanyID 検索対象法人コード
     * @param sTargetUserID 検索対象社員番号
     * @param sDate 判定基準日
     * @return リレーションID
     * @throws Exception システム例外
     */
     int getRelation( String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sTargetCompanyID, String sTargetUserID, String sDate) throws Exception;

    /**
     * リレーション情報を取得します
     * @param sCustomerID 顧客コード
     * @param sLoginCompanyID ログイン法人コード
     * @param sLoginUserID ログイン社員番号
     * @param sTargetCompanyID 検索対象法人コード
     * @param sTargetUserID 検索対象社員番号
     * @param sDate 検索基準日
     * @param sReportLine レポートラインタイプ
     * @param httpSession セッション
     * @param sGroupID グループコード
     * @param sSystemCode システムコード
     * @param sGUID GUID
     * @param bFixed true:確定済み情報を参照, false:未確定情報を参照
     * @return Vector リレーション情報 - 0:リレーションID,
     *                                    1:適用開始日(自動判定の場合はnull),
     *                                    2:適用終了日(自動判定の場合はnull),
     *                                    3:判別区分(0:自動判定, 1:役割関係定義)
     * @throws Exception システム例外
     */
     Vector<Integer> getRelation(
             String sCustomerID, 	  String sLoginCompanyID,
             String sLoginUserID,   String sTargetCompanyID,
             String sTargetUserID,  String sDate,
             String sReportLine,  HttpSession httpSession,
             String sGroupID,       String sSystemCode,
             String sGUID,          boolean bFixed
    ) throws Exception;

    /**
     * 被評価者情報リストを取得します
     * @param sCustomerID 顧客コード
     * @param sLoginCompanyID ログイン者の法人コード
     * @param sLoginUserID ログイン者の社員番号
     * @param sCreterialDate 検索基準日
     * @param nEvaluationLevel 評価レベル
     * @param sReportLine レポートラインタイプ
     * @param sGroupID グループコード
     * @param sSystemCode システムコード
     * @param sGUID GUID
     * @param sLanguage 言語区分
     * @return 被評価者ユーザ情報配列
     *          顧客コード、法人コード、英語法人名、
     *          日本語法人名、所属コード、役職名、社員番号、
     *          漢字氏名を含むVector×n 対象者なし時はNULL
     * @throws Exception システム例外
     */
     Vector<Vector<Object>> getEvaluatee(
             String sCustomerID,    String sLoginCompanyID,
             String sLoginUserID,   String sCreterialDate,
             int nEvaluationLevel,  String sReportLine,
             String sGroupID,       String sSystemCode,
             String sGUID,          String sLanguage
    ) throws Exception;

    /**
     * 指定した検索者と被検索者が指定評価レベルの評価者、被評価者の関係にあるかどうかを判定します<br>
     * （ドメイン01限定）
     * @param sCustomerID 顧客コード
     * @param sLoginCompanyID ログイン法人コード
     * @param sLoginUserID ログインユーザコード
     * @param sTargetCompanyID 検索対象法人コード
     * @param sTargetUserID 検索対象ユーザID
     * @param sDate データ検索基準日
     * @param nEvaluationLevel 評価レベル
     * @param sReportLine レポートラインタイプ
     * @param sGroupID グループコード
     * @param sSystemCode システムコード
     * @param sGUID GUID
     * @param sLoginChiefId sLoginChiefId
     * @return true:評価者、被評価者の関係である false:評価者、被評価者の関係ではない
     * @throws Exception システム例外
     */
     boolean isMatchEvaluationLevel(
             String sCustomerID,  String sLoginCompanyID,
             String sLoginUserID, 	 String sTargetCompanyID,
             String sTargetUserID,  String sDate,
             int nEvaluationLevel,  String sReportLine,
             String sGroupID,  String sSystemCode,
             String sGUID,  String[] sLoginChiefId
    ) throws Exception;

    /**
     * 評価レベルの最大値を取得します
     * @param sCustomerID 顧客コード
     * @param sCompanyID 法人コード
     * @param sCreterialDate 検索基準日
     * @return int 評価レベルの最大値
     * @throws Exception システム例外
     */
     int getMaxEvaluationLevel(
             String sCustomerID,
             String sCompanyID,
             String sCreterialDate) throws Exception;

    /**
     * 指定評価レベルの評価者情報を取得します。
     * @param sCustomerID 顧客コード
     * @param sLoginCompanyID ログイン法人コード
     * @param sLoginUserID ログインユーザ社員番号
     * @param sTargetCompanyID 検索対象ユーザ法人コード
     * @param sTargetUserID 検索対象ユーザ社員番号
     * @param sCreterialDate 検索基準日
     * @param nEvaluationLevel 評価レベル
     * @param sReportLine レポートラインタイプ
     * @param bIgnoreRelationDefinitions 判定基準フラグ
     * @param sGroupID グループコード
     * @param sSystemCode システムコード
     * @param sGUID GUID
     * @return Vector 評価者ユーザ情報配列
     * @throws Exception システム例外
     */
     Vector<Vector<Object>> getEvaluator(
             String sCustomerID,    String sLoginCompanyID,
             String sLoginUserID,   String sTargetCompanyID,
             String sTargetUserID,  String sCreterialDate,
             int nEvaluationLevel,  String sReportLine,
             boolean bIgnoreRelationDefinitions,
             String sGroupID,       String sSystemCode,
             String sGUID) throws Exception;

    /**
     * 基点組織以下の組織リストをSQL用のカンマ区切りで取得します
     * @param sCustID	顧客コード
     * @param sCompID 法人コード
     * @param sEmployeeID 社員番号
     * @param sCreterialDate 基準日
     * @return String 基点組織またはそれ以下の組織のリスト（カンマ区切り）
     */
     String getBaseSectionListForSQL(
             String sCustID,      String sCompID,
             String sEmployeeID,  String sCreterialDate);

    /**
     * 基点組織以下の組織リストをSQL用のカンマ区切りで取得します（複数法人対応）
     * @param sCustID	顧客コード
     * @param sCompID 法人コード
     * @param sEmployeeID 社員番号
     * @param sCreterialDate 基準日
     * @return String 法人区分をキーに、基点組織またはそれ以下の組織のリスト（カンマ区切り）
     */
     Map<String,String> getBaseSectionListMultiCompForSQL(
             String sCustID,      String sCompID,
             String sEmployeeID,  String sCreterialDate);

    /**
     * 指定社員の下位組織リストをSQL用のカンマ区切りで取得します
     * 社員指定、仮想組織含むか
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sEmployeeID 社員番号
     * @param sCreterialDate 基準日
     * @param bIncludeVirtual 仮想組織含むか
     * @return String 組織コードリスト
     */
     String getLowerSectionListUserForSQL(
             String sCustID,      String sCompID,
             String sEmployeeID,  String sCreterialDate,
            boolean bIncludeVirtual);

    /**
     * 指定社員の下位組織リストをSQL用のカンマ区切りで取得します
     * 社員指定
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sEmployeeID 社員番号
     * @param sCreterialDate 基準日
     * @return String 組織コードリスト
     */
     String getLowerSectionListUserForSQL(
             String sCustID,      String sCompID,
             String sEmployeeID,  String sCreterialDate);

    /**
     * 指定組織の下位組織リストをSQL用のカンマ区切りで取得します
     * 組織指定
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sSection 組織コード
     * @param sCreterialDate 基準日
     * @return String 組織コードリスト
     */
     String getLowerSectionListForSQL(
             String sCustID,   String sCompID,
             String sSection,  String sCreterialDate);

    /**
     * 指定組織の上位組織リストをSQL用のカンマ区切りで取得します
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sSection 組織コード
     * @param sCreterialDate 基準日
     * @return String 組織コードリスト
     */
     String getUpperSectionListForSQL(
             String sCustID,   String sCompID,
             String sSection,  String sCreterialDate);

    /**
     * システムプロパティ情報を取得します<br>
     * @param sKey プロパティ名
     * @return プロパティ値
     */
     String getSystemProperty( String sKey);

    /**
     * 所属長役職を取得します
     * @param sCustid 検索対象顧客コード
     * @param sCompid 検索対象法人コード
     * @param sDeptid 検索対象組織コード
     * @param sDate 検索基準日
     * @return String 所属長の役職コード
     */
     PsResult getChiefPostOfDesignation(
             String sCustid,  String sCompid,
             String sDeptid,  String sDate);

    /**
     * SQL実行結果セット（Desigあり）
     * @param vecResult SQL実行結果
     * @return Vector 結果セット
     */
     Vector<Object> setV3ColumnDataContDesig(Vector<Object> vecResult);

    /**
     * 全てのシステムプロパティ情報を取得します<br>
     * TODO:
     * @return プロパティ値
     */
     Map<String, String> getLookAndFeelSettings();


    /**
     * V3の顧客コード、法人コード、社員番号からユーザコードを取得します
     * @param sCustid
     * @param sCompid
     * @param sLoginUserId
     * @param sDate
     */
    String getUseridForV4(String sCustid,String sCompid,String sLoginUserId,String sDate);

}
