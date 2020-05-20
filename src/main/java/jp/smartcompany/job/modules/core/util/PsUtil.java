package jp.smartcompany.job.modules.core.util;

import java.util.Vector;

public class PsUtil {
    private static PsUtil psUtil = null;
    private String StSelYear;
    private String StSelMonth;
    private String StSelDate;
    private String tempconcat = "";
    private String separator;
    private StringBuffer strBuff = null;

    public PsUtil() {
    }

    public static PsUtil getPsUtil() {
        if (psUtil == null) {
            psUtil = new PsUtil();
        }
        return psUtil;
    }
    public Vector replaceEscape(Vector pvReplaceStrings) {
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
        StringBuffer sbResult = new StringBuffer("");
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

    public String transStringNullToDB(String psString) {
        if ((psString == null) || (psString.equals(""))) {
            return "NULL";
        }
        return "'" + psString + "'";
    }

    public String escapeQuote(String psSourceStr) {
        StringBuffer sbResult = new StringBuffer("");
        int i = 0;
        String sResult = "";
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

    public String escapeQuote2(String psSourceStr) {
        StringBuffer sbResult = new StringBuffer("");
        int i = 0;
        String sResult = "";
        if (psSourceStr == null) {
            return null;
        }
        for (i = 0; i < psSourceStr.length(); i++) {
            if (psSourceStr.charAt(i) == '\'') {
                sbResult.append("\\'");
            } else if (psSourceStr.charAt(i) == '"') {
                sbResult.append("&quot;");
            } else {
                sbResult.append(psSourceStr.substring(i, i + 1));
            }
        }
        return sbResult.toString();
    }

}
