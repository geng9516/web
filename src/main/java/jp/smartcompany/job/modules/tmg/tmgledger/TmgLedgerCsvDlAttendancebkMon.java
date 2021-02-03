/*==============================================================================
 *  system	U-PDS 就業
 *  version
 *  id      TmgLedgerCsvDlAttendancebookMonthly.java
 *  title   帳票「出勤簿（月）」ＣＳＶダウンロード
 *  author  yamaguchi.k
 *  create  2013/01/29
 *
 *          更新日        更新者         更新内容
 *  update  2013/01/29    yamaguchi.k    tmd#803 新規作成 帳票出力CSVダウンロード対応
 *  remark
 *
 =============================================================================*/

package jp.smartcompany.job.modules.tmg.tmgledger;

import java.util.Vector;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.tmg.tmgledger.TmgLedgerCsvDlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 帳票「出勤簿（月）」のCSVダウンロード機能を提供するクラス
 *
 * @author yamaguchi.k
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgLedgerCsvDlAttendancebkMon extends TmgLedgerCsvDl {

	private final TmgLedgerCsvDlUtil util;

	/** LOG出力用ディスクリプタ */
	private static final String BEAN_DESC                 = "TmgLedger";

	/** プロパティKey */
	public static final String PROP_WRD_CSV_TARGET_YYYYMM = "WRD_CSV_TARGET_YYYYMM";
	public static final String PROP_WRD_CSV_EMPNAME       = "WRD_CSV_EMPNAME";
	public static final String PROP_WRD_CSV_ORGNAME       = "WRD_CSV_ORGNAME";
	public static final String PROP_WRD_CSV_POSTNAME      = "WRD_CSV_POSTNAME";
	public static final String PROP_WRD_CSV_WORKTYPENAME  = "WRD_CSV_WORKTYPENAME";
	public static final String PROP_WRD_CSV_DAY           = "WRD_CSV_DAY";
	public static final String PROP_WRD_CSV_DAYOFWEEK     = "WRD_CSV_DAYOFWEEK";
	public static final String PROP_WRD_CSV_WORKNAME      = "WRD_CSV_WORKNAME";
	public static final String PROP_WRD_CSV_PLAN_OPEN     = "WRD_CSV_PLAN_OPEN";
	public static final String PROP_WRD_CSV_PLAN_CLOSE    = "WRD_CSV_PLAN_CLOSE";
	public static final String PROP_WRD_CSV_TP_OPEN       = "WRD_CSV_TP_OPEN";
	public static final String PROP_WRD_CSV_TP_CLOSE      = "WRD_CSV_TP_CLOSE";
	public static final String PROP_WRD_CSV_RESULT_OPEN   = "WRD_CSV_RESULT_OPEN";
	public static final String PROP_WRD_CSV_RESULT_CLOSE  = "WRD_CSV_RESULT_CLOSE";
	public static final String PROP_WRD_CSV_REMARK        = "WRD_CSV_REMARK";
	public static final String PROP_WRD_CSV_SUM           = "WRD_CSV_SUM";

	/** CSVファイルヘッダー項目 */
	private static final String[] CSV_HEADER_ITEMS =
			{PROP_WRD_CSV_TARGET_YYYYMM,
					PROP_WRD_CSV_EMPNAME,
					PROP_WRD_CSV_ORGNAME,
					PROP_WRD_CSV_POSTNAME,
					PROP_WRD_CSV_WORKTYPENAME};

	/** 顧客コード */
	private String gsCompId   = null;
	/** 法人コード */
	private String gsCustId   = null;
	/** 言語区分 */
	private String gsLang     = null;
	/** 職員番号 */
	private String gsEmpId    = null;
	/** 対象年月 */
	private String gsYYYYMM   = null;
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
		gsCompId = pParam.getCustomerID(); // 顧客コード
		gsCustId = pParam.getCompanyID();  // 法人コード
		gsLang   = pParam.getLanguage();   // 言語区分
		gsEmpId  = pParam.getEmpID();      // 職員番号
		gsYYYYMM = pParam.getYYYYMM();     // 対象年月
	}

	/**
	 * 帳票「出勤簿（月）」のCSVファイルをダウンロードします。
	 *
	 * @param  sFileName ダウンロードファイル名
	 * @throws Exception
	 */
	public void download(String sFileName) throws Exception {

		// データ検索に使用するパラメータ生成
	//	TmgLedgerCsvDlUtil util = new TmgLedgerCsvDlUtil();
		String sDBCustId  = util.escDBString(gsCustId);
		String sDBCompId  = util.escDBString(gsCompId);
		String sDBEmpId   = util.escDBString(gsEmpId);
		String sDBDate    = util.toDBDate(gsYYYYMM);
		String sDBLang    = util.escDBString(gsLang);

		// ダウンロードデータ検索
		Vector vQuery = new Vector(); // クエリ格納オブジェクト

		// 0 ヘッダーデータ部　検索
		vQuery.add(buildSQLForSelectHeader(sDBCustId, sDBCompId, sDBEmpId, sDBDate, sDBLang));

		// 1 明細データ部　検索
		vQuery.add(buildSQLForSelectDetail(sDBCustId, sDBCompId, sDBEmpId, sDBDate, sDBLang));

		// 2 集計データ部　検索
		vQuery.add(buildSQLForSelectSummary(sDBCustId, sDBCompId, sDBEmpId, sDBDate, sDBLang));

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
	 * @param  sEmpId  職員番号
	 * @param  sDate   対象年月
	 * @param  sLang   言語区分
	 * @return SQL文
	 */
	private String buildSQLForSelectHeader(String sCustId, String sCompId, String sEmpId, String sDate, String sLang) {

//		TmgLedgerCsvDlUtil util = new TmgLedgerCsvDlUtil();
		StringBuffer      sbSQL = new StringBuffer();

		sbSQL.append(" SELECT ");
		// ヘッダー部データ
		sbSQL.append("     TARGET_MONTH,");                                                 //  0 対象年月
		sbSQL.append("     NAME, ");                                                        //  1 氏名
		sbSQL.append("     SECTION, ");                                                     //  2 所属
		sbSQL.append("     POST, ");                                                        //  3 職名
		sbSQL.append("     WORKTYPE, ");                                                    //  4 職種

		/*
		 * 詳細部タイトル名称
		 * 集計項目名はPDFダウンロードにも使用するマスタ設定項目。
		 * その為、HTML改行コードが設定されている場合を考慮し、改行コードは除外する。
		 */
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_DAY,          gsLang) + ", "); //  5 「日付」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_DAYOFWEEK,    gsLang) + ", "); //  6 「曜日」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_WORKNAME,     gsLang) + ", "); //  7 「区分」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_PLAN_OPEN,    gsLang) + ", "); //  8 「予定始業」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_PLAN_CLOSE,   gsLang) + ", "); //  9 「予定終業」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_TP_OPEN,      gsLang) + ", "); // 10 「打刻始業」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_TP_CLOSE,     gsLang) + ", "); // 11 「打刻終業」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_RESULT_OPEN,  gsLang) + ", "); // 12 「実績始業」文字列
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_RESULT_CLOSE, gsLang) + ", "); // 13 「実績終業」文字列
		sbSQL.append("     REPLACE(UPPER(ITEM1_NAME),  '<BR>', '') AS ITEM1_NAME, ");       // 14 集計項目01 名称
		sbSQL.append("     REPLACE(UPPER(ITEM2_NAME),  '<BR>', '') AS ITEM2_NAME, ");       // 15 集計項目02 名称
		sbSQL.append("     REPLACE(UPPER(ITEM3_NAME),  '<BR>', '') AS ITEM3_NAME, ");       // 16 集計項目03 名称
		sbSQL.append("     REPLACE(UPPER(ITEM4_NAME),  '<BR>', '') AS ITEM4_NAME, ");       // 17 集計項目04 名称
		sbSQL.append("     REPLACE(UPPER(ITEM5_NAME),  '<BR>', '') AS ITEM5_NAME, ");       // 18 集計項目05 名称
		sbSQL.append("     REPLACE(UPPER(ITEM6_NAME),  '<BR>', '') AS ITEM6_NAME, ");       // 19 集計項目06 名称
		sbSQL.append("     REPLACE(UPPER(ITEM7_NAME),  '<BR>', '') AS ITEM7_NAME, ");       // 20 集計項目07 名称
		sbSQL.append("     REPLACE(UPPER(ITEM8_NAME),  '<BR>', '') AS ITEM8_NAME, ");       // 21 集計項目08 名称
		sbSQL.append("     REPLACE(UPPER(ITEM9_NAME),  '<BR>', '') AS ITEM9_NAME, ");       // 22 集計項目09 名称
		sbSQL.append("     REPLACE(UPPER(ITEM10_NAME), '<BR>', '') AS ITEM10_NAME, ");      // 23 集計項目10 名称
		sbSQL.append("     REPLACE(UPPER(ITEM11_NAME), '<BR>', '') AS ITEM11_NAME, ");      // 24 集計項目11 名称
		sbSQL.append("     REPLACE(UPPER(ITEM12_NAME), '<BR>', '') AS ITEM12_NAME, ");      // 25 集計項目12 名称
		sbSQL.append("     REPLACE(UPPER(ITEM13_NAME), '<BR>', '') AS ITEM13_NAME, ");      // 26 集計項目13 名称
		sbSQL.append("     REPLACE(UPPER(ITEM14_NAME), '<BR>', '') AS ITEM14_NAME, ");      // 27 集計項目14 名称
		sbSQL.append("     REPLACE(UPPER(ITEM15_NAME), '<BR>', '') AS ITEM15_NAME, ");      // 28 集計項目15 名称
		sbSQL.append("     REPLACE(UPPER(ITEM16_NAME), '<BR>', '') AS ITEM16_NAME, ");      // 29 集計項目16 名称
		sbSQL.append("     REPLACE(UPPER(ITEM17_NAME), '<BR>', '') AS ITEM17_NAME, ");      // 30 集計項目17 名称
		sbSQL.append("     REPLACE(UPPER(ITEM18_NAME), '<BR>', '') AS ITEM18_NAME, ");      // 31 集計項目18 名称
		sbSQL.append("     REPLACE(UPPER(ITEM19_NAME), '<BR>', '') AS ITEM19_NAME, ");      // 32 集計項目19 名称
		sbSQL.append("     REPLACE(UPPER(ITEM20_NAME), '<BR>', '') AS ITEM20_NAME, ");      // 33 集計項目20 名称
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_REMARK, gsLang));              // 34 「備考」文字列
		sbSQL.append(" FROM ");
		sbSQL.append("     TABLE(");
		sbSQL.append("         TMG_SELECT_LEDGER_2_1( ");
		sbSQL.append(                  sEmpId  + ", ");
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
	 * @param  sEmpId  職員番号
	 * @param  sDate   対象年月
	 * @param  sLang   言語区分
	 * @return SQL文
	 */
	private String buildSQLForSelectDetail(String sCustId, String sCompId, String sEmpId, String sDate, String sLang) {

		StringBuffer sbSQL = new StringBuffer();

		sbSQL.append(" SELECT ");
		sbSQL.append("     DISP_DATE, ");    //  0 日付
		sbSQL.append("     DISP_DAY, ");     //  1 曜日
		sbSQL.append("     WORK_NAME, ");    //  2 就業区分
		sbSQL.append("     OPEN_N, ");       //  3 予定・始業時刻
		sbSQL.append("     CLOSE_N, ");      //  4 予定・終業時刻
		sbSQL.append("     OPEN_TP, ");      //  5 打刻・始業時刻
		sbSQL.append("     CLOSE_TP, ");     //  6 打刻・終業時刻
		sbSQL.append("     OPEN_R, ");       //  7 実績・始業時刻
		sbSQL.append("     CLOSE_R, ");      //  8 実績・終業時刻
		sbSQL.append("     ITEM1_VALUE, ");  //  9 集計項目01値
		sbSQL.append("     ITEM2_VALUE, ");  // 10 集計項目02値
		sbSQL.append("     ITEM3_VALUE, ");  // 11 集計項目03値
		sbSQL.append("     ITEM4_VALUE, ");  // 12 集計項目04値
		sbSQL.append("     ITEM5_VALUE, ");  // 13 集計項目05値
		sbSQL.append("     ITEM6_VALUE, ");  // 14 集計項目06値
		sbSQL.append("     ITEM7_VALUE, ");  // 15 集計項目07値
		sbSQL.append("     ITEM8_VALUE, ");  // 16 集計項目08値
		sbSQL.append("     ITEM9_VALUE, ");  // 17 集計項目09値
		sbSQL.append("     ITEM10_VALUE, "); // 18 集計項目10値
		sbSQL.append("     ITEM11_VALUE, "); // 19 集計項目11値
		sbSQL.append("     ITEM12_VALUE, "); // 20 集計項目12値
		sbSQL.append("     ITEM13_VALUE, "); // 21 集計項目13値
		sbSQL.append("     ITEM14_VALUE, "); // 22 集計項目14値
		sbSQL.append("     ITEM15_VALUE, "); // 23 集計項目15値
		sbSQL.append("     ITEM16_VALUE, "); // 24 集計項目16値
		sbSQL.append("     ITEM17_VALUE, "); // 25 集計項目17値
		sbSQL.append("     ITEM18_VALUE, "); // 26 集計項目18値
		sbSQL.append("     ITEM19_VALUE, "); // 27 集計項目19値
		sbSQL.append("     ITEM20_VALUE, "); // 28 集計項目20値
		sbSQL.append("     DAILY_MSG ");     // 29 備考
		sbSQL.append(" FROM ");
		sbSQL.append("     TABLE(");
		sbSQL.append("         TMG_SELECT_LEDGER_2_2( ");
		sbSQL.append(                  sEmpId  + ", ");
		sbSQL.append(                  sDate   + ", ");
		sbSQL.append(                  sCustId + ", ");
		sbSQL.append(                  sCompId + ", ");
		sbSQL.append(                  sLang);
		sbSQL.append("         ) ");
		sbSQL.append("     ) ");

		return sbSQL.toString();
	}


	/**
	 * 集計情報を検索するクエリを生成します。
	 *
	 * @param  sCustId 顧客コード
	 * @param  sCompId 法人コード
	 * @param  sEmpId  職員番号
	 * @param  sDate   対象年月
	 * @param  sLang   言語区分
	 * @return SQL文
	 */
	private String buildSQLForSelectSummary(String sCustId, String sCompId, String sEmpId, String sDate, String sLang) {

//		TmgLedgerCsvDlUtil util = new TmgLedgerCsvDlUtil();
		StringBuffer      sbSQL = new StringBuffer();

		sbSQL.append(" SELECT ");
		sbSQL.append("     '', ");                                                 //  0 「空欄」
		sbSQL.append("     '', ");                                                 //  1 「空欄」
		sbSQL.append("     '', ");                                                 //  2 「空欄」
		sbSQL.append("     '', ");                                                 //  3 「空欄」
		sbSQL.append("     '', ");                                                 //  4 「空欄」
		sbSQL.append("     '', ");                                                 //  5 「空欄」
		sbSQL.append("     '', ");                                                 //  6 「空欄」
		sbSQL.append("     '', ");                                                 //  7 「空欄」
		sbSQL.append(      util.getEscDBPropMsg(PROP_WRD_CSV_SUM, gsLang) + ", "); //  8 「合計」
		sbSQL.append("     SUM_ITEM1_VALUE, ");                                    //  9 集計項目01の合計値
		sbSQL.append("     SUM_ITEM2_VALUE, ");                                    // 10 集計項目02の合計値
		sbSQL.append("     SUM_ITEM3_VALUE, ");                                    // 11 集計項目03の合計値
		sbSQL.append("     SUM_ITEM4_VALUE, ");                                    // 12 集計項目04の合計値
		sbSQL.append("     SUM_ITEM5_VALUE, ");                                    // 13 集計項目05の合計値
		sbSQL.append("     SUM_ITEM6_VALUE, ");                                    // 14 集計項目06の合計値
		sbSQL.append("     SUM_ITEM7_VALUE, ");                                    // 15 集計項目07の合計値
		sbSQL.append("     SUM_ITEM8_VALUE, ");                                    // 16 集計項目08の合計値
		sbSQL.append("     SUM_ITEM9_VALUE, ");                                    // 17 集計項目09の合計値
		sbSQL.append("     SUM_ITEM10_VALUE, ");                                   // 18 集計項目10の合計値
		sbSQL.append("     SUM_ITEM11_VALUE, ");                                   // 19 集計項目11の合計値
		sbSQL.append("     SUM_ITEM12_VALUE, ");                                   // 20 集計項目12の合計値
		sbSQL.append("     SUM_ITEM13_VALUE, ");                                   // 21 集計項目13の合計値
		sbSQL.append("     SUM_ITEM14_VALUE, ");                                   // 22 集計項目14の合計値
		sbSQL.append("     SUM_ITEM15_VALUE, ");                                   // 23 集計項目15の合計値
		sbSQL.append("     SUM_ITEM16_VALUE, ");                                   // 24 集計項目16の合計値
		sbSQL.append("     SUM_ITEM17_VALUE, ");                                   // 25 集計項目17の合計値
		sbSQL.append("     SUM_ITEM18_VALUE, ");                                   // 26 集計項目18の合計値
		sbSQL.append("     SUM_ITEM19_VALUE, ");                                   // 27 集計項目19の合計値
		sbSQL.append("     SUM_ITEM20_VALUE ");                                    // 28 集計項目20の合計値
		sbSQL.append(" FROM ");
		sbSQL.append("     TABLE(");
		sbSQL.append("         TMG_SELECT_LEDGER_2_3( ");
		sbSQL.append(                  sEmpId  + ", ");
		sbSQL.append(                  sDate   + ", ");
		sbSQL.append(                  sCustId + ", ");
		sbSQL.append(                  sCompId + ", ");
		sbSQL.append(                  sLang);
		sbSQL.append("         ) ");
		sbSQL.append("     ) ");

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
