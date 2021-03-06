package jp.smartcompany.boot.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        int e;
        StringBuilder result = new StringBuilder();
        while ((e = psString.indexOf(psPattern, s)) >= 0) {
            result.append(psString, s, e);
            result.append(psReplace);
            s = e + psPattern.length();
        }
        result.append(psString.substring(s));
        return result.toString();

    }

    public static String transDateNullToDB(String psDate) {
        if (StrUtil.isBlank(psDate)) {
            return "NULL";
        }
        return "to_date('" + psDate + "','yyyy/mm/dd')";
    }

    public static String transStringNullToDB(String psString) {
        if (StrUtil.isBlank(psString)) {
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
                sbResult.append(psSourceStr.charAt(i));
            }
        }
        return sbResult.toString();
    }

    public static Vector replaceEscape(Vector pvReplaceStrings) {
        if (pvReplaceStrings == null) {
            return null;
        }
        String value;
        String replacevalue;
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
        int i;
        if (psSourceStr == null) {
            return null;
        }
        for (i = 0; i < psSourceStr.length(); i++) {
            if (psSourceStr.charAt(i) == '\'') {
                sbResult.append("''");
            } else {
                sbResult.append(psSourceStr.charAt(i));
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

        if (StrUtil.isBlank(psNumber)) {
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
     */
    public static Date transStringToDate(String psDate)  {

        if (psDate == null) {
            return null;
        }
        Date dDate;
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
            throw new GlobalException(e.getMessage());
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

        return fFormat.format(dateToday);
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
        int e;
        StringBuilder result = new StringBuilder();
        while ((e = psString.toUpperCase().indexOf(psPattern.toUpperCase(), s)) >= 0) {
            result.append(psString, s, e);
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

    public static String getPermissionString() {
        HttpSession session = ContextUtil.getSession();
        PsSession psSession = (PsSession)session.getAttribute(Constant.PS_SESSION);
        ScCacheUtil scCacheUtil = SpringUtil.getBean("scCacheUtil");
        return scCacheUtil.getPermissionString(psSession.getLoginCustomer(),
                psSession.getLoginCompany(), psSession.getLanguage());
    }

    /* ▼****************************** 区切り文字置換 *******************************▼ */
    /** デフォルト区切り文字 */
    public static final String PS_DEFAULT_SEPARATOR = "_";

    /** デフォルト以外に有効な区切り文字 */
    public static final String[] PS_VALID_SEPARATORS = {","};


    /***********************************************************************************************
     * プロパティファイルより値を取得 言語区分がNULLの場合には英語モードとなります、また指定した取得キーが存在しない
     * 場合には[default]をキーとした値を返す、[default]が存在しない場合にはNULLを返します。 ResourceBundleのBaseNameは[PS]固定
     *
     * @param psLanguage 言語区分 ja または en-us
     * @param psValue プロパティファイルの取得キー
     * @return String プロパティファイルの値
     */
    public static String getpropertyvalue(String psLanguage, String psValue) {

        ResourceBundle rb = null;
        try {

            // 言語区分がNULLの場合は英語モードとする
            if (psLanguage == null) {
                psLanguage = "en-us";
            }

            // 言語区分が[,]で区切られている場合は最初の要素の値を使用する
            int index = psLanguage.indexOf(",");
            if (index != -1) {
                psLanguage = psLanguage.substring(0, index);
            }

            // ローカルの設定
            Locale locale;
            if (StrUtil.equalsIgnoreCase("en-us",psLanguage)) {
                // 言語区分:英 語
                locale = Locale.US;
            } else if (StrUtil.equalsIgnoreCase(psLanguage,"ja")) {
                // 言語区分:日本語
                locale = Locale.JAPAN;
            } else {
                // 言語区分:英 語
                locale = Locale.US;
            }

            // ローカルが日本語の場合にキャラクタネームを指定
            rb = ResourceBundle.getBundle("PS", locale);
            if (StrUtil.equalsIgnoreCase(psLanguage,"ja")) {
                // 日本語の場合はJDKのバージョンによってエンコード方式を変える
                if (isUpper141()) {
                    return new String(rb.getString(psValue).getBytes("8859_1"), "Windows-31J");
                } else {
                    return new String(rb.getString(psValue).getBytes("8859_1"), "Shift_JIS");
                }
            } else {
                return rb.getString(psValue);
            }
        } catch (Exception e) {
            try {

                // 指定のキーが無い場合には[default]をキーとした内容を返す
                // 日本語の場合はJDKのバージョンによってエンコード方式を変える
                if (isUpper141()) {
                    return new String(rb.getString("default").getBytes("8859_1"), "Windows-31J");
                } else {
                    return new String(rb.getString("default").getBytes("8859_1"), "Shift_JIS");
                }

            } catch (Exception e1) {
                // [default]の値が無い場合にはNULLを返す
                return null;
            }
        }
    }

    /***********************************************************************************************
     * JDKのバージョンが1.4.1以上かどうか判定する
     * @return boolean true:1.4.1以上 false:1.4.0以下
     */
    public static boolean isUpper141() {

        // ▼2005/04/04 Saito
//        ResourceBundle rb = ResourceBundle.getBundle("datasource");
//        String sServerType = rb.getString("SERVER");
//        final String SERVER_TOMCAT = "1";
        // 2006/03/13 Saito Tomcat向け判定処理を削除
        // ▲2005/04/04 Saito

        String sVersion = System.getProperty("java.vm.version");
        // ハイフンつきのバージョンに対応
        if (sVersion.contains("-")) {
            sVersion = sVersion.substring(0, sVersion.indexOf("-"));
        }
        // アンダーバーつきのバージョンに対応
        if (sVersion.contains("_")) {
            sVersion = sVersion.substring(0, sVersion.indexOf("_"));
        }

        String sMajorVersion;
        String sMinorVersion;
        String sReleaseVersion;
        // メジャーバージョン、マイナーバージョン、リリースバージョンを取得
        if (sVersion.contains(".")) {
            sMajorVersion = sVersion.substring(0, sVersion.indexOf("."));
            if (sVersion.indexOf(".") != sVersion.lastIndexOf(".")) {
                sMinorVersion =
                        sVersion.substring(sVersion.indexOf(".") + 1, sVersion.lastIndexOf("."));
                sReleaseVersion = sVersion.substring(sVersion.lastIndexOf(".") + 1);
            } else {
                sMinorVersion = sVersion.substring(sVersion.indexOf(".") + 1);
                sReleaseVersion = "0";
            }
        } else {
            sMajorVersion = sVersion;
            sMinorVersion = "0";
            sReleaseVersion = "0";
        }

        // メジャーバージョンが１より大きければバージョン1.4.1以上なのでtrue
        if (Integer.parseInt(sMajorVersion) > 1) {
            return true;
            // マイナーバージョンが４より大きければバージョン1.4.1以上なのでtrue
        } else if (Integer.parseInt(sMinorVersion) > 4) {
            return true;
            // マイナーバージョンが４でリリースバージョンが１以上ならバージョン1.4.1以上なのでtrue
        } else {
            return Integer.parseInt(sMinorVersion) == 4 && Integer.parseInt(sReleaseVersion) >= 1;
        }
    }

    /***********************************************************************************************
     * プロパティファイルより値を取得 言語区分がNULLの場合には英語モードとなります、また指定した取得キーが存在しない
     * 場合には[default]をキーとした値を返す、[default]が存在しない場合にはNULLを返します。
     * ResourceBundleのBaseNameはsApplicationnameにて指定
     *
     * @param psLanguage 言語区分 ja または en-us
     * @param psValue プロパティファイルの取得キー
     * @param psApplicationName ResourceBundleのBaseName
     * @return String プロパティファイルの値
     */
    public static String getpropertyvalue(String psLanguage, String psValue,
                                         String psApplicationName) {
        ResourceBundle rb = null;
        try {
            if (psLanguage == null) {
                psLanguage = "en-us";
            }
            int index = psLanguage.indexOf(",");
            if (index != -1) {
                psLanguage = psLanguage.substring(0, index);
            }
            Locale locale;
            if (StrUtil.equalsIgnoreCase(psLanguage,"en-us")) {
                locale = Locale.US;
            } else if (StrUtil.equalsIgnoreCase(psLanguage,"ja")) {
                locale = Locale.JAPAN;
            } else {
                locale = Locale.US;
            }
            rb = ResourceBundle.getBundle(psApplicationName, locale);
            if (StrUtil.equalsIgnoreCase(psLanguage,"ja")) {
                if (isUpper141()) {
                    return new String(rb.getString(psValue).getBytes("8859_1"),
                            "Windows-31J");
                }
                return new String(rb.getString(psValue).getBytes("8859_1"),
                        "Shift_JIS");
            }
            return rb.getString(psValue);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (isUpper141()) {
                    return new String(rb.getString("default")
                            .getBytes("8859_1"), "Windows-31J");
                }
                return new String(rb.getString("default").getBytes("8859_1"),
                        "Shift_JIS");
            } catch (Exception e1) {
            }
        }
        return null;
    }

    /**
     * Listを1000要素単位に分割し、IN句を作成します。
     * Oracleの制約により、INに1001個以上の要素を指定するとエラーが発生する現象の対応です
     * @param plIdList 1000件単位でIN句を区切った文字列(クエリ)
     */
    public static String separateList(String psColumn, List < String > plIdList) {
        String sReturn = "";
        if (StrUtil.isNotBlank(psColumn)
                && plIdList != null && plIdList.size() != 0) {
            StringBuilder sb = new StringBuilder(psColumn + " IN (");
            Iterator < String > ite = plIdList.iterator();
            int cnt = 0;
            while (ite.hasNext()) {
                cnt++;
                sb.append("'").append(escapeQuote(ite.next())).append("'");
                if (cnt >= 1000) {
                    if (ite.hasNext()) {
                        sb.append(") OR ").append(psColumn).append(" IN (");
                    } else {
                        sb.append(",");
                    }
                    cnt = 0;
                } else {
                    sb.append(",");
                }
            }
            sReturn = sb.substring(0, sb.length()-1) + ")";
        }
        return sReturn;
    }

    /**
     * <B>【最大日付取得（Date型）】</B><BR>
     * P@Sにて使用可能である最大日付を、Date型にて返却する。（非推奨）<BR>
     * @return Date 最大日付
     */
    public static Date getMaxDateObject() {

        // 日付の出力形式を設定
        SimpleDateFormat fFormat = new SimpleDateFormat();
        Date dRetDate = new Date();

        try {
            fFormat.applyPattern("yyyy/MM/dd");
            return fFormat.parse(PsConst.MAXDATE);

        } catch (ParseException e) {
            // APIでは、特にエラーメッセージなどは返却しない
            return dRetDate;
        }
    }

    /**
     * <B>【最小日付取得（Date型）】</B><BR>
     * P@Sにて使用可能である最小日付を、Date型にて返却する。（非推奨）<BR>
     * @return Date 最小日付
     */
    public static Date getMinDateObject() {

        // 日付の出力形式を設定
        SimpleDateFormat fFormat = new SimpleDateFormat();
        Date dRetDate = new Date();

        try {
            fFormat.applyPattern("yyyy/MM/dd");
            return fFormat.parse(PsConst.MINDATE);

        } catch (ParseException e) {
            // APIでは、特にエラーメッセージなどは返却しない
            e.printStackTrace();
            return dRetDate;
        }
    }


    public static boolean isAjaxRequest(HttpServletRequest request) {
        return StrUtil.equalsIgnoreCase("XMLHttpRequest",request.getHeader(Constant.HEADER_XMLHTTPREQUEST));
    }

    public static String escDBString(String sString) {
        return transStringNullToDB(escapeQuote(sString));
    }

    public static boolean isAnyBlank(String...texts) {
        boolean isBlank = false;
        for (String t : texts) {
            if (StrUtil.isNotBlank(t)){
                isBlank = true;
                break;
            }
        }
        return isBlank;
    }

    public static String getCryptPhotoName(String userId) {
        // 定数
        final String MD5_ADD_STRING = "P@S"; // MD5で変換する際に社員番号に付加する文字
        // 社員番号＋固定文字をMD5により変換した結果
        String sDigest = DigestUtil.md5Hex(userId + MD5_ADD_STRING);
        return userId + "_" + sDigest;
    }
}
