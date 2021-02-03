/*==============================================================================
 *  system	U-PDS 就業
 *  version
 *  id      TmgLedgerCsvDlOvertimeList.java
 *  title   帳票「超過勤務実施状況リスト」ＣＳＶダウンロード
 *  author  yamaguchi.k
 *  create  2013/01/29
 *
 *          更新日        更新者         更新内容
 *  update  2013/01/29    yamaguchi.k    tmd#803 新規作成 帳票出力CSVダウンロード対応
 *          2013/05/20    yamaguchi.k    tmd#883 超過勤務実績状況リストＣＳＶで超勤時間０時間でも０を表示する様にする。
 *  remark
 *
 =============================================================================*/

package jp.smartcompany.job.modules.tmg.tmgledger;

import java.util.Vector;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 帳票「超過勤務実施状況リスト」のCSVダウンロード機能を提供するクラス
 *
 * @author yamaguchi.k
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgLedgerCsvDlOvertimeList extends TmgLedgerCsvDl {

	private final TmgLedgerCsvDlUtil util;

	/** LOG出力用ディスクリプタ */
	private static final String BEAN_DESC                 = "TmgLedger";

	/** プロパティKey */
	public static final String PROP_WRD_CSV_TARGET_YYYY   = "WRD_CSV_TARGET_YYYY";
	public static final String PROP_WRD_CSV_TARGET_ORG    = "WRD_CSV_TARGET_ORG";
	public static final String PROP_WRD_CSV_ORGNAME       = "WRD_CSV_ORGNAME";
	public static final String PROP_WRD_CSV_EMPNAME       = "WRD_CSV_EMPNAME";
	public static final String PROP_WRD_CSV_POSTNAME      = "WRD_CSV_POSTNAME";
	public static final String PROP_WRD_CSV_WORKTYPENAME  = "WRD_CSV_WORKTYPENAME";
	public static final String PROP_WRD_CSV_SUM           = "WRD_CSV_SUM";
	public static final String PROP_WRD_CSV_TOTAL_SUM     = "WRD_CSV_TOTAL_SUM";
	public static final String PROP_WRD_CSV_OVER45_CNT    = "WRD_CSV_OVER45_CNT";
	public static final String PROP_WRD_CSV_SUM_OVER45    = "WRD_CSV_SUM_OVER45";

	/** CSVファイルヘッダー項目 */
	private static final String[] CSV_HEADER_ITEMS = {PROP_WRD_CSV_TARGET_YYYY, PROP_WRD_CSV_TARGET_ORG};

	/** 組織番号 */
	private String gsOrgId    = null;
	/** 対象年度 */
	private String gsYYYY     = null;
	/** 顧客コード */
	private String gsCompId   = null;
	/** 法人コード */
	private String gsCustId   = null;
	/** 言語区分 */
	private String gsLang     = null;
	/** 法定内超勤を含むフラグ */
	private int giIncludeOt100 = 0;
	/** PsDBBean */
	private PsDBBean psDbBean = null;

	/**
	 * パラメータ設定
	 *
	 * @param pPsDbBean PsDBBeanインスタンス
	 * @param pParam    帳票出力パラメータクラス
	 */
	public void setParam(PsDBBean pPsDbBean, TmgLedgerParam pParam) {

		// パラメータを設定
		psDbBean = pPsDbBean;              // PsDBBean
		gsOrgId  = pParam.getOrgID();      // 組織番号
		gsYYYY   = pParam.getYYYY();       // 対象年度
		gsCompId = pParam.getCustomerID(); // 顧客コード
		gsCustId = pParam.getCompanyID();  // 法人コード
		gsLang   = pParam.getLanguage();   // 言語区分
		giIncludeOt100 = Integer.valueOf(pParam.getsIncludeOt100Flg()).intValue(); // 法定内超勤を含むフラグ
	}

	/**
	 * 帳票「出勤簿（月）」のCSVファイルをダウンロードします。
	 *
	 * @param  sFileName ダウンロードファイル名
	 * @throws Exception
	 */
	public void download(String sFileName) throws Exception {

		// データ検索に使用するパラメータ生成
//		TmgLedgerCsvDlUtil util = new TmgLedgerCsvDlUtil();
		String sDBCustId  = util.escDBString(gsCustId);
		String sDBCompId  = util.escDBString(gsCompId);
		String sDBOrgId   = util.escDBString(gsOrgId);
		String sDBDate    = gsYYYY;
		String sDBLang    = util.escDBString(gsLang);

		// ダウンロードデータ検索
		Vector vQuery = new Vector(); // クエリ格納オブジェクト

		// 0 ヘッダーデータ部　検索
		vQuery.add(buildSQLForSelectHeader(sDBCustId, sDBCompId, sDBOrgId, sDBDate, sDBLang));

		// 1 明細データ部　検索
		vQuery.add(buildSQLForSelectDetail(sDBCustId, sDBCompId, sDBOrgId, sDBDate, sDBLang, giIncludeOt100));

		// 2 集計データ部　検索
		vQuery.add(buildSQLForSelectSummary(sDBCustId, sDBCompId, sDBOrgId, sDBDate, sDBLang, giIncludeOt100));

		try {
			// 検索結果取得
			PsResult result = (PsResult)psDbBean.getValuesforMultiquery(vQuery, BEAN_DESC);

			// 検索結果からCSV用データを生成
			Vector vecCSVHeader  = getCSVHeader(result);  // ヘッダー部
			Vector vecCSVDetail  = getCSVDetail(result);  // 明細部
			Vector vecCSVSummary = getCSVSummary(result); // 集計部

			// 生成したデータを一つのデータに連結。
			vecCSVDetail.addAll(vecCSVSummary); // 明細部へ合計部を連結
			vecCSVHeader.addAll(vecCSVDetail);  // ヘッダー部へ（明細＋合計）部を連結

			// CSVファイルをダウンロードする。
			util.csvDownload(sFileName, vecCSVHeader,null);

		} catch (Exception e) {
			// ダウンロード処理中にエラーが発生した場合、発生した例外をスローする。
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * ヘッダー情報を検索するクエリを生成します。
	 *
	 * @param  sCustId 顧客コード
	 * @param  sCompId 法人コード
	 * @param  sOrgId  組織番号
	 * @param  sDate   対象年月
	 * @param  sLang   言語区分
	 * @return SQL文
	 */
	private String buildSQLForSelectHeader(String sCustId, String sCompId, String sOrgId, String sDate, String sLang) {

		StringBuffer      sbSQL = new StringBuffer();

		sbSQL.append(" SELECT ");
		// ヘッダー部データ
		sbSQL.append("     TARGET_YEAR,");                                                  //  0 対象年度
		sbSQL.append("     TARGET_SECTION, ");                                              //  1 対象組織

		/*
		 * 詳細部タイトル名称
		 */
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_ORGNAME,      gsLang) + ", "); //  2 「所属」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_EMPNAME,      gsLang) + ", "); //  3 「氏名」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_POSTNAME,     gsLang) + ", "); //  4 「職名」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_WORKTYPENAME, gsLang) + ", "); //  5 「種別」文字列
		sbSQL.append("     ITEM1_NAME, ");                                                  //  6 年度開始１月目の月
		sbSQL.append("     ITEM2_NAME, ");                                                  //  7 年度開始２月目の月
		sbSQL.append("     ITEM3_NAME, ");                                                  //  8 年度開始３月目の月
		sbSQL.append("     ITEM4_NAME, ");                                                  //  9 年度開始４月目の月
		sbSQL.append("     ITEM5_NAME, ");                                                  // 10 年度開始５月目の月
		sbSQL.append("     ITEM6_NAME, ");                                                  // 11 年度開始６月目の月
		sbSQL.append("     ITEM7_NAME, ");                                                  // 12 年度開始７月目の月
		sbSQL.append("     ITEM8_NAME, ");                                                  // 13 年度開始８月目の月
		sbSQL.append("     ITEM9_NAME, ");                                                  // 14 年度開始９月目の月
		sbSQL.append("     ITEM10_NAME, ");                                                 // 15 年度開始１０月目の月
		sbSQL.append("     ITEM11_NAME, ");                                                 // 16 年度開始１１月目の月
		sbSQL.append("     ITEM12_NAME, ");                                                 // 17 年度開始１２月目の月
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_TOTAL_SUM,  gsLang) + ", ");   // 18 「合計」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_OVER45_CNT, gsLang));          // 19 「45超(回)」文字列
		sbSQL.append(" FROM ");
		sbSQL.append("     TABLE(");
		sbSQL.append("         TMG_SELECT_LEDGER_5_1( ");
		sbSQL.append(                  sOrgId  + ", ");
		sbSQL.append(                  sDate   + ", ");
		sbSQL.append(                  sCustId + ", ");
		sbSQL.append(                  sCompId + ", ");
		sbSQL.append(                  sLang);
		sbSQL.append("         ) ");
		sbSQL.append("     ) ");

		return sbSQL.toString();
	}

	/**
	 * 明細情報を検索するクエリを生成します。
	 *
	 * @param  sCustId 顧客コード
	 * @param  sCompId 法人コード
	 * @param  sOrgId  組織番号
	 * @param  sDate   対象年月
	 * @param  sLang   言語区分
	 * @param  iIncledeFlg 法定内超勤を含むフラグ（0:含まない、1:含む）
	 * @return SQL文
	 */
	private String buildSQLForSelectDetail(String sCustId, String sCompId, String sOrgId, String sDate, String sLang, int iIncledeFlg) {

		StringBuffer sbSQL = new StringBuffer();

		sbSQL.append(" SELECT ");
		sbSQL.append("     SECTION, ");                                    //  0 所属
		sbSQL.append("     NAME, ");                                       //  1 氏名
		sbSQL.append("     POST, ");                                       //  2 職名
		sbSQL.append("     WORKTYPE, ");                                   //  3 種別
		sbSQL.append("     DECODE(OVERTIME_HOURS_1,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_1,  0)), "); //  4 年度開始　１月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_2,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_2,  0)), "); //  5 年度開始　２月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_3,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_3,  0)), "); //  6 年度開始　３月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_4,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_4,  0)), "); //  7 年度開始　４月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_5,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_5,  0)), "); //  8 年度開始　５月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_6,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_6,  0)), "); //  9 年度開始　６月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_7,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_7,  0)), "); // 10 年度開始　７月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_8,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_8,  0)), "); // 11 年度開始　８月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_9,  NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_9,  0)), "); // 12 年度開始　９月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_10, NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_10, 0)), "); // 13 年度開始１０月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_11, NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_11, 0)), "); // 14 年度開始１１月目の月の超勤合計(HH:MI)
		sbSQL.append("     DECODE(OVERTIME_HOURS_12, NULL, '', TMG_F_CONV_MIN2HHMI(OVERTIME_HOURS_12, 0)), "); // 15 年度開始１２月目の月の超勤合計(HH:MI)
		sbSQL.append("     TMG_F_CONV_MIN2HHMI(SUM_OVERTIME_HOURS, 0), "); // 16 年間超勤合計
		sbSQL.append("     SUM_OVER45_CNT ");                              // 17 超勤合計が45時間を超えた月数
		sbSQL.append(" FROM ");
		sbSQL.append("     TABLE(");
		sbSQL.append("         TMG_SELECT_LEDGER_5_2( ");
		sbSQL.append(                  sOrgId  + ", ");
		sbSQL.append(                  sDate   + ", ");
		sbSQL.append(                  sCustId + ", ");
		sbSQL.append(                  sCompId + ", ");
		sbSQL.append(                  sLang   + ", ");
		sbSQL.append(                  "SYSDATE   , ");
		sbSQL.append(                  iIncledeFlg);
		sbSQL.append("         ) ");
		sbSQL.append("     ) ");

		return sbSQL.toString();
	}


	/**
	 * 集計情報を検索するクエリを生成します。
	 *
	 * @param  sCustId 顧客コード
	 * @param  sCompId 法人コード
	 * @param  sOrgId  組織番号
	 * @param  sDate   対象年月
	 * @param  sLang   言語区分
	 * @param  iIncledeFlg 法定内超勤を含むフラグ（0:含まない、1:含む）
	 * @return SQL文
	 */
	private String buildSQLForSelectSummary(String sCustId, String sCompId, String sOrgId, String sDate, String sLang, int iIncledeFlg) {

//		TmgLedgerCsvDlUtil util = new TmgLedgerCsvDlUtil();
		StringBuffer      sbSQL = new StringBuffer();

		sbSQL.append(" SELECT ");
		sbSQL.append("     COL1, ");
		sbSQL.append("     COL2, ");
		sbSQL.append("     COL3, ");
		sbSQL.append("     COL4, ");
		sbSQL.append("     COL5, ");
		sbSQL.append("     COL6, ");
		sbSQL.append("     COL7, ");
		sbSQL.append("     COL8, ");
		sbSQL.append("     COL9, ");
		sbSQL.append("     COL10, ");
		sbSQL.append("     COL11, ");
		sbSQL.append("     COL12, ");
		sbSQL.append("     COL13, ");
		sbSQL.append("     COL14, ");
		sbSQL.append("     COL15, ");
		sbSQL.append("     COL16, ");
		sbSQL.append("     COL17  ");
		sbSQL.append(" FROM ");
		sbSQL.append("     ( ");
		// 「総合計」欄データ
		sbSQL.append("     SELECT ");
		sbSQL.append("         '' AS COL1, ");                                                       //  0 「空欄」
		sbSQL.append("         '' AS COL2, ");                                                       //  1 「空欄」
		sbSQL.append("         '' AS COL3, ");                                                       //  2 「空欄」
		sbSQL.append(          util.getEscDBPropMsg(PROP_WRD_CSV_TOTAL_SUM, gsLang) + " AS COL4, "); //  4 「総合計」
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_1)   AS COL5, ");                              //  5 年度開始１月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_2)   AS COL6, ");                              //  6 年度開始２月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_3)   AS COL7, ");                              //  7 年度開始３月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_4)   AS COL8, ");                              //  8 年度開始４月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_5)   AS COL9, ");                              //  9 年度開始５月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_6)   AS COL10, ");                             // 10 年度開始６月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_7)   AS COL11, ");                             // 11 年度開始７月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_8)   AS COL12, ");                             // 12 年度開始８月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_9)   AS COL13, ");                             // 13 年度開始９月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_10)  AS COL14, ");                             // 14 年度開始１０月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_11)  AS COL15, ");                             // 15 年度開始１１月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(OVERTIME_HOURS_12)  AS COL16, ");                             // 16 年度開始１２月目の月の超勤総合計
		sbSQL.append("         TO_CHAR(SUM_OVERTIME_HOURS) AS COL17, ");                             // 17 年間超勤合計
		sbSQL.append("         0 AS SORT ");                                                         // 18 並び順
		sbSQL.append("     FROM ");
		sbSQL.append("         TABLE(");
		sbSQL.append("             TMG_SELECT_LEDGER_5_3( ");
		sbSQL.append(                      sOrgId  + ", ");
		sbSQL.append(                      sDate   + ", ");
		sbSQL.append(                      sCustId + ", ");
		sbSQL.append(                      sCompId + ", ");
		sbSQL.append(                      sLang   + ", ");
		sbSQL.append(                      iIncledeFlg);
		sbSQL.append("             ) ");
		sbSQL.append("         ) ");
		sbSQL.append("     UNION ");
		// 「合計 45超(人)」欄データ
		sbSQL.append("     SELECT ");
		sbSQL.append("         '' AS COL1, ");                                                        //  0 「空欄」
		sbSQL.append("         '' AS COL2, ");                                                        //  1 「空欄」
		sbSQL.append("         '' AS COL3, ");                                                        //  2 「空欄」
		sbSQL.append(          util.getEscDBPropMsg(PROP_WRD_CSV_SUM_OVER45, gsLang) + " AS COL4, "); //  4 「合計 45超(人)」
		sbSQL.append("         TO_CHAR(OVER45_NOP_1)   AS COL5, ");                                   //  5 年度開始１月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_2)   AS COL6, ");                                   //  6 年度開始２月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_3)   AS COL7, ");                                   //  7 年度開始３月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_4)   AS COL8, ");                                   //  8 年度開始４月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_5)   AS COL9, ");                                   //  9 年度開始５月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_6)   AS COL10, ");                                  // 10 年度開始６月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_7)   AS COL11, ");                                  // 11 年度開始７月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_8)   AS COL12, ");                                  // 12 年度開始８月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_9)   AS COL13, ");                                  // 13 年度開始９月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_10)  AS COL14, ");                                  // 14 年度開始１０月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_11)  AS COL15, ");                                  // 15 年度開始１１月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(OVER45_NOP_12)  AS COL16, ");                                  // 16 年度開始１２月目の月の45超の超勤を行った人数
		sbSQL.append("         TO_CHAR(SUM_OVER45_NOP) AS COL17, ");                                  // 17 年間超勤合計
		sbSQL.append("         1 AS SORT ");                                                          // 18 並び順
		sbSQL.append("     FROM ");
		sbSQL.append("         TABLE(");
		sbSQL.append("             TMG_SELECT_LEDGER_5_3( ");
		sbSQL.append(                      sOrgId  + ", ");
		sbSQL.append(                      sDate   + ", ");
		sbSQL.append(                      sCustId + ", ");
		sbSQL.append(                      sCompId + ", ");
		sbSQL.append(                      sLang   + ", ");
		sbSQL.append(                      iIncledeFlg);
		sbSQL.append("             ) ");
		sbSQL.append("         ) ");
		sbSQL.append("     ORDER BY SORT "); // 「総合計」、「合計 45超(人)」の順になる様に並び替え
		sbSQL.append(" ) ");

		return sbSQL.toString();
	}

	/**
	 * ダウンロードCSVファイルのヘッダー部情報を取得します。
	 *
	 * @param  result
	 * @return CSVファイルヘッダー部情報
	 */
	private Vector getCSVHeader(PsResult result) {

//		TmgLedgerCsvDlUtil util = new TmgLedgerCsvDlUtil();

		Vector vecCSVHeader = new Vector(); // CSVヘッダーデータ格納リスト
		Vector vecCSVRecord = null;         // CSVレコードデータ格納リスト

		// クエリ０番目の検索結果を取得（ヘッダー・タイトル部データ）
		Vector vecResult = result.getResult();
		Vector vecData   = (Vector)vecResult.get(0);
		Vector vecRec    = (Vector)vecData.get(0);   // 検索結果の１レコード目を取得

		// ヘッダー部項目について、設定を行う。
		for (int i = 0; i < CSV_HEADER_ITEMS.length; i++) {

			vecCSVRecord = new Vector();
			vecCSVRecord.add(util.getPropMsg(CSV_HEADER_ITEMS[i], gsLang)); // 項目名を設定
			vecCSVRecord.add(vecRec.get(i));                                // 検索結果データを設定

			// ヘッダーデータへ追加（項目データはカンマ区切りで連結）
			vecCSVHeader.add(StringUtils.join(vecCSVRecord.toArray(), ","));
		}

		return vecCSVHeader;
	}

	/**
	 * ダウンロードCSVファイルの詳細部情報を取得します。
	 *
	 * @param  result
	 * @return CSVファイル詳細部情報
	 */
	private Vector getCSVDetail(PsResult result) {

		Vector vecCSVDetail = new Vector(); // CSV詳細データ格納リスト
		Vector vecCSVRecord = new Vector(); // CSVレコードデータ格納リスト

		// クエリ０番目の検索結果を取得（ヘッダー・タイトル部データ）
		Vector vecResult = result.getResult();
		Vector vecData   = (Vector)vecResult.get(0);
		Vector vecRec    = (Vector)vecData.get(0);   // 検索結果の１レコード目を取得

		/*
		 * 詳細タイトル部の項目について、設定を行う。
		 */
		for (int i = CSV_HEADER_ITEMS.length; i < vecRec.size(); i++) {
			vecCSVRecord.add(vecRec.get(i));  // 検索結果データを設定
		}

		// 詳細部タイトル部を詳細部データに追加。
		vecCSVDetail.add(StringUtils.join(vecCSVRecord.toArray(), ","));


		/*
		 * 詳細データ部の項目について、設定を行う。
		 */
		// クエリ１番目の検索結果を取得（詳細データ）
		vecData = (Vector)vecResult.get(1);
		for (int i = 0; i < vecData.size(); i++) {

			vecCSVRecord = new Vector();
			// 検索結果からレコードデータを取得
			vecCSVRecord = (Vector)vecData.get(i);

			// レコード配列を詳細データ配列に追加する（項目データはカンマ区切りで連結）
			vecCSVDetail.add(StringUtils.join(vecCSVRecord.toArray(), ","));
		}

		return vecCSVDetail;
	}

	/**
	 * ダウンロードCSVファイルの集計部情報を取得します。
	 *
	 * @param  result
	 * @return CSVファイル集計部情報
	 */
	private Vector getCSVSummary(PsResult result) {

		Vector vecCSVSummary = new Vector(); // CSV集計データ格納リスト
		Vector vecCSVRecord  = null;         // CSVレコードデータ格納リスト

		/*
		 * 合計データ部の項目について、設定を行う。
		 */
		Vector vecResult = result.getResult();
		Vector vecData   = (Vector)vecResult.get(2); // クエリ２番目の検索結果を取得（合計部データ）
		for (int i = 0; i < vecData.size(); i++) {

			vecCSVRecord = new Vector();
			// 検索結果の１レコード目を取得
			vecCSVRecord = (Vector)vecData.get(i);

			// レコード配列を合計データ配列に追加する（項目データはカンマ区切りで連結）
			vecCSVSummary.add(StringUtils.join(vecCSVRecord.toArray(), ","));
		}

		return vecCSVSummary;
	}

}
