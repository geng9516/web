/*==============================================================================
 *  system	U-PDS 就業
 *  version
 *  id      TmgLedgerCsvDlAttendancebookMonthly.java
 *  title   帳票ＣＳＶダウンロード処理のユーティリティクラス
 *  author  yamaguchi.k
 *  create  2013/01/29
 *
 *          更新日        更新者         更新内容
 *  update  2013/01/29    yamaguchi.k    tmd#803 新規作成 帳票出力CSVダウンロード対応
 *  remark
 *
 =============================================================================*/

package jp.smartcompany.job.modules.tmg.tmgledger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;


/**
 * 帳票のCSVダウンロードユーティリティ機能を提供するクラス
 *
 * @author yamaguchi.k
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgLedgerCsvDlUtil {

	private final HttpServletResponse response;

	/** プロパティファイルパス */
	public static final String PATH_PROPERTIES       = "ps.c01.tmg.TmgLedger.TmgLedger";
	/** ダウンロードコンテンツタイプ */
	public static final String DOWNLOAD_CONTENT_TYPE = "application/octetstream;charset=Shift_JIS";
	/** OS改行文字 */
	public static final String LINE_SEPARATOR        = System.getProperty("line.separator");

	/**
	 * CSVファイルをダウンロードします。
	 *
	 * @param sFileName  ダウンロードファイル名
	 * @param vecCSVData CSVデータを格納した配列
	 */
	public void csvDownload(String sFileName, Vector vecCSVData,String datePattern) throws UnsupportedEncodingException {
		if (StrUtil.isBlank(datePattern)){
			datePattern = DatePattern.NORM_DATETIME_PATTERN;
		}
		List<List<Object>> rows= convCsvListToByte(vecCSVData);
		Properties pro = System.getProperties();
		response.setCharacterEncoding(pro.getProperty("file.encoding"));
		response.setHeader("Content-Type", "text/csv;charset=Shift_JIS");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Content-Disposition", "application");
		response.setHeader("filename", URLEncoder.encode(sFileName, "utf-8"));

		try {
			@Cleanup PrintWriter writer = response.getWriter();
			for(List<Object> row: rows) {
				StringBuilder excelRowData = new StringBuilder();
				for(Object col :row){
					if (col instanceof Date) {
						Date d = (Date)col;
						excelRowData.append("\t");
						excelRowData.append(DateUtil.format(d, datePattern));
						excelRowData.append("\t");
					} else {
						excelRowData.append(col);
					}
					excelRowData.append(",");
				}
				String rowData=excelRowData.toString().substring(0,excelRowData.length()-1);
				writer.println(rowData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * CSV出力バイトデータ生成処理
	 *
	 * @param  vecCSVList CSVデータリスト
	 * @return CSVデータリストのバイト変換配列
	 * @throws Exception
	 */
	public List<List<Object>> convCsvListToByte(Vector vecCSVList) {

		List<List<Object>> csvData=new ArrayList<List<Object>>();

		for (int i = 0; i < vecCSVList.size(); i++) {
			csvData.add(new ArrayList<>(Arrays.asList(vecCSVList.get(i))));
		}

		return csvData;
	}

	/**
	 * プロパティファイルから指定のキーに該当するメッセージを取得する
	 *
	 * @param  psPropKey
	 * @return プロパティファイルに定義してあるメッセージ
	 */
	public String getPropMsg(String psPropKey, String sLang) {

		return  PsUtil.getPsUtil().getpropertyvalue(sLang, psPropKey, this.PATH_PROPERTIES);
	}

	/**
	 * プロパティファイルから指定のキーに該当するメッセージを取得する
	 * （ＤＢ文字列エスケープ付き）
	 *
	 * @param  psPropKey
	 * @param  sLang
	 * @return ＤＢ検索用エスケープ済のメッセージ（プロパティファイルに定義してあるメッセージ）
	 */
	public String getEscDBPropMsg(String psPropKey, String sLang) {

		return  escDBString(PsUtil.getPsUtil().getpropertyvalue(sLang, psPropKey, this.PATH_PROPERTIES));
	}

	/**
	 * 指定文字列をＤＢ検索用にエスケープする。
	 *
	 * @param  sVal 文字列
	 * @return シングルクォートでエスケープした文字列を返す。
	 */
	public String escDBString(String sVal) {

		PsDBBean psDbBean = new PsDBBean();
		return psDbBean.escDBString(sVal);
	}

	/**
	 * 指定文字列をＤＢ検索用にエスケープする。
	 *
	 * @param  sDate 日付（YYYY/MM/DD）
	 * @return ORACLE日付型変換エスケープした文字列を返す。
	 */
	public String toDBDate(String sDate) {

		PsDBBean psDbBean = new PsDBBean();
		return psDbBean.toDBDate(sDate);
	}
}
