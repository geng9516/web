package jp.smartcompany.boot.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public class SysUtil {

    /**
     * 构建mybatis的查询构造器QueryWrapper的快捷方法
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> query() {
        return new QueryWrapper<>();
    }

    /***********************************************************************************************
     * 文字列の置換メソッド
     *
     * @param psString 置換対象文字列
     * @param psPattern 置換前文字列
     * @param psReplace 置換後文字列
     * @return String 置換後の置換対象文字列
     */
    public static String replaceString(String psString, String psPattern, String psReplace) {

        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        while ((e = psString.indexOf(psPattern, s)) >= 0) {
            result.append(psString.substring(s, e));
            result.append(psReplace);
            s = e + psPattern.length();
        }
        result.append(psString.substring(s));
        return result.toString();

    }

    public static String transDateNullToDB(String psDate) {
        if ((psDate == null) || (psDate.equals(""))) {
            return "NULL";
        }
        return "to_date('" + psDate + "', 'yyyy/mm/dd')";
    }

    public static String transStringNullToDB(String psString) {
        if ((psString == null) || (psString.equals(""))) {
            return "NULL";
        }
        return "'" + psString + "'";
    }

    public static String escapeQuote(String psSourceStr) {
        StringBuilder sbResult = new StringBuilder("");
        int i ;
        if (psSourceStr == null) {
            return null;
        }
        for (i = 0; i < psSourceStr.length(); i++) {
            if (psSourceStr.charAt(i) == '\'') {
                sbResult.append("''");
            } else {
                sbResult.append(psSourceStr.substring(i, i + 1));
            }
        }
        return sbResult.toString();
    }

    public static Vector replaceEscape(Vector pvReplaceStrings) {
        if (pvReplaceStrings == null) {
            return null;
        }
        String value = "";
        String replacevalue = "";
        for (int i = 0; i < pvReplaceStrings.size(); i++) {
            if (pvReplaceStrings.get(i) != null) {
                value = (String) pvReplaceStrings.get(i);
                replacevalue = escape(value);
                pvReplaceStrings.removeElementAt(i);
                pvReplaceStrings.insertElementAt(replacevalue, i);
            }
        }
        return pvReplaceStrings;
    }

    public static String escape(String psSourceStr) {
        StringBuilder sbResult = new StringBuilder();
        int i = 0;
        String sDest = "";
        if (psSourceStr == null) {
            return null;
        }
        for (i = 0; i < psSourceStr.length(); i++) {
            if (psSourceStr.charAt(i) == '\'') {
                sbResult.append("''");
            } else {
                sbResult.append(psSourceStr.substring(i, i + 1));
            }
        }
        return sbResult.toString();
    }

    /***********************************************************************************************
     * 数値をnull → 0 にします。null以外はそのまま返します。
     *
     * @param psNumber 変換する文字列。
     * @return String 変換後の文字列
     */
    public static String transNumberNullToDB(String psNumber) {

        if (psNumber == null || psNumber.equals("")) {
            return "0";
        } else {
            return psNumber;
        }
    }

    /***********************************************************************************************
     * null → "" にします。
     *
     * @param psString 変換する文字列。もちろん、null でないときは このまま返される。
     * @return String 変換後の文字列
     */
    public static String transNull(String psString) {

        if (psString == null) {
            return "";
        } else {
            return psString;
        }
    }

    /* ▲****************************** 定数情報返却 *******************************▲ */

    /**
     * 文字列(yyyy/MM/dd)をDateに変換する nullの場合は、nullを返す
     *
     * @param psDate 日付(yyyy/MM/dd)
     * @return Date
     * @throws ParseException 変換出来なかった場合
     */
    public static Date transStringToDate(String psDate) throws ParseException {

        if (psDate == null) {
            return null;
        }
        Date dDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            dDate = sdf.parse(psDate);

            if (dDate != null) {
                // ****/**/32などの日付が翌月の01日として判定されるのを回避
                // ex.1) 2009/01/01 → 2009/01/01
                // ex.2) 2009/01/01 → 2009/01/1
                // ex.3) 2009/01/01 → 2009/1/01
                // ex.4) 2009/01/01 → 2009/1/1
                if (psDate.replaceAll("/0", "/").equals(transDateToString(dDate).replaceAll("/0", "/"))) {
                    return dDate;
                }
            }
        } catch (ParseException e) {
            throw e;
        }
        return null;

    }

    /**
     * Dateを文字列(yyyy/MM/dd)に変換する nullの場合は、nullを返す
     *
     * @param pdDate 日付
     * @return String(yyyy/MM/dd)
     */
    public static String transDateToString(Date pdDate) {

        if (pdDate == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(pdDate);

    }

    /**
     * システム日付（時刻）を指定フォーマットで返却します。<BR>
     *
     * @param psFormat フォーマット
     * @return String システム日付（時刻）
     */
    public static String getSysdate(String psFormat) {

        Date dateToday = new Date();
        SimpleDateFormat fFormat = new SimpleDateFormat(psFormat);
        String sSysdate = fFormat.format(dateToday);

        return sSysdate;
    }

    /***********************************************************************************************
     * 文字列の置換メソッド（大文字小文字にかかわらずパターン文字と一致する部分を置換する）
     *
     * @param psString 置換対象文字列
     * @param psPattern 置換前文字列
     * @param psReplace 置換後文字列
     * @return String 置換後の置換対象文字列
     */
    public static String replaceStringIgnoreCase(String psString, String psPattern, String psReplace) {

        int s = 0;
        int e = 0;
        StringBuilder result = new StringBuilder();
        while ((e = psString.toUpperCase().indexOf(psPattern.toUpperCase(), s)) >= 0) {
            result.append(psString.substring(s, e));
            result.append(psReplace);
            s = e + psPattern.length();
        }
        result.append(psString.substring(s));
        return result.toString();
    }

    /**
     * Timestampを文字列(yyyy/MM/dd)に変換する nullの場合は、nullを返す
     *
     * @param pTimestamp 日付
     * @return String(yyyy/MM/dd)
     */
    public static String transTimestampToString(Timestamp pTimestamp) {

        if (pTimestamp == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(pTimestamp.getTime());
    }

}
