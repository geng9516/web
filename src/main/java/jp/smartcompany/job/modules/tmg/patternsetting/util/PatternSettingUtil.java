package jp.smartcompany.job.modules.tmg.patternsetting.util;

/**
 * @author 陳毅力
 * @description
 * @objectSource ps.c01.tmg.PatternSetting.PatternSettingUtil
 * @date 2020/06/16
 **/
public class PatternSettingUtil {

    /**
     * 文字列がNULLまたは空白である事を確認します。
     *
     * @param sString 文字列
     * @return boolean
     */
    public static boolean isEmpty(String sString) {

        if (sString == null || sString.equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }


}
