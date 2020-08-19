package jp.smartcompany.job.modules.core.util;

import java.util.Locale;
import java.util.ResourceBundle;
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

    public static final boolean isUpper141() {
        ResourceBundle rb = ResourceBundle.getBundle("datasource");
        String sServerType = rb.getString("SERVER");
        String SERVER_TOMCAT = "1";

        String sVersion = System.getProperty("java.vm.version");
        if (sVersion.indexOf("-") > -1) {
            sVersion = sVersion.substring(0, sVersion.indexOf("-"));
        }
        if (sVersion.indexOf("_") > -1) {
            sVersion = sVersion.substring(0, sVersion.indexOf("_"));
        }
        String sMajorVersion = null;
        String sMinorVersion = null;
        String sReleaseVersion = null;
        if (sVersion.indexOf(".") > -1) {
            sMajorVersion = sVersion.substring(0, sVersion.indexOf("."));
            if (sVersion.indexOf(".") != sVersion.lastIndexOf(".")) {
                sMinorVersion = sVersion.substring(sVersion.indexOf(".") + 1,
                        sVersion.lastIndexOf("."));

                sReleaseVersion = sVersion
                        .substring(sVersion.lastIndexOf(".") + 1);
            } else {
                sMinorVersion = sVersion.substring(sVersion.indexOf(".") + 1);
                sReleaseVersion = "0";
            }
        } else {
            sMajorVersion = sVersion;
            sMinorVersion = "0";
            sReleaseVersion = "0";
        }
        if (Integer.parseInt(sMajorVersion) > 1) {
            return true;
        }
        if (Integer.parseInt(sMinorVersion) > 4) {
            return true;
        }
        if ((Integer.parseInt(sMinorVersion) == 4)
                && (Integer.parseInt(sReleaseVersion) >= 1)) {
            return true;
        }
        return false;
    }

    public String transDateNullToDB(String psDate) {
        if ((psDate == null) || (psDate.equals(""))) {
            return "NULL";
        }
        return "to_date('" + psDate + "', 'yyyy/mm/dd')";
    }

    public final String getpropertyvalue(String psLanguage, String psValue) {
        ResourceBundle rb = null;
        try {
            if (psLanguage == null) {
                psLanguage = "en-us";
            }
            int index = psLanguage.indexOf(",");
            if (index != -1) {
                psLanguage = psLanguage.substring(0, index);
            }
            Locale locale = null;
            if (psLanguage.equalsIgnoreCase("en-us")) {
                locale = Locale.US;
            } else if (psLanguage.equalsIgnoreCase("ja")) {
                locale = Locale.JAPAN;
            } else {
                locale = Locale.US;
            }
            rb = ResourceBundle.getBundle("PS", locale);
            if (psLanguage.equalsIgnoreCase("ja")) {
                if (isUpper141()) {
                    return new String(rb.getString(psValue).getBytes("8859_1"),
                            "Windows-31J");
                }
                return new String(rb.getString(psValue).getBytes("8859_1"),
                        "Shift_JIS");
            }
            return rb.getString(psValue);
        } catch (Exception e) {
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

    public final String getpropertyvalue(String psLanguage, String psValue,
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
            Locale locale = null;
            if (psLanguage.equalsIgnoreCase("en-us")) {
                locale = Locale.US;
            } else if (psLanguage.equalsIgnoreCase("ja")) {
                locale = Locale.JAPAN;
            } else {
                locale = Locale.US;
            }
            rb = ResourceBundle.getBundle(psApplicationName, locale);
            if (psLanguage.equalsIgnoreCase("ja")) {
                if (isUpper141()) {
                    return new String(rb.getString(psValue).getBytes("8859_1"),
                            "Windows-31J");
                }
                return new String(rb.getString(psValue).getBytes("8859_1"),
                        "Shift_JIS");
            }
            return rb.getString(psValue);
        } catch (Exception e) {
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

    public String ReplaceString(String psString, String psPattern,
                                String psReplace) {
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

}
