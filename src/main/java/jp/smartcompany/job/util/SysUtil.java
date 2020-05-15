package jp.smartcompany.job.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

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

}
