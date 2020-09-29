package jp.smartcompany.job.modules.tmg.patternsetting.util;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.SysUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

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

    /**
     * メッセージ取得関数 指定されたリソースバンドルのキーを元に、 対応するメッセージを返します。
     *
     * @param sLang
     * @param sKey
     * @return リソースバンドルより取得したメッセージ
     */
    public static String getMessage(String sLang, String sKey) {

        // システムプロパティからメッセージを取得し、返却する
        return SysUtil.getpropertyvalue(sLang, sKey,PatternSettingConst.PATTEN_SETTING_PAGE_NAME);

    }

    /**
     * HH:MMをXXXX分に変換する(引数が不正な場合は -1 を返す)
     *
     * @param sHHMM
     * @return XXXX
     */
    public static int toMinuteFromHHcMI60(String sHHMM) {

        // コロン
        String sColonSTR = ":";
        // 引数が不正な場合の戻り値
        int nErrorReturn = -1;

        if (!formatChackHHMM(sHHMM)) {
            // 引数が不正な場合
            return nErrorReturn;
        }
        // コロンの位置を取得
        int nColonPoint = sHHMM.indexOf(sColonSTR);

        // 分に変換します
        int nHour = Integer.parseInt(sHHMM.substring(0, nColonPoint)) * 60;
        int nMinute = Integer.parseInt(sHHMM.substring(nColonPoint + 1));

        return nHour + nMinute;
    }

    /**
     * hh:mm形式チェック フォーマットが合っていればtrueを返す
     *
     * @param psString
     * @return flg
     */
    public static boolean formatChackHHMM(String psString) {
        boolean flg = false;
        // 時間 24時間表記とは限らないため00-99もしくは0-9を可とする
        // 分 60分表記とするため00-59のみを可とする
        if (!StrUtil.isBlank(psString) && psString.matches("^([0-9][0-9]|[0-9]):[0-5][0-9]$")) {
            flg = true;
        }
        return flg;
    }

    /**
     * 全角チェック 全角文字が含まれていた場合trueを返す
     *
     * @param psString
     * @return flg
     * @throws UnsupportedEncodingException
     */
    public static boolean isDoubleByte(String psString) throws UnsupportedEncodingException {

        byte[] buf1;
        boolean flg = false;

        buf1 = psString.getBytes("SJIS");

        if (psString.length() != buf1.length) {
            // 全角が含まれてる
            flg = true;
        }
        return flg;
    }

    /**
     * 開始・終了時刻の比較チェック 開始＜終了であればtrue
     *
     * @param timeMap
     * @return flg
     */
    public static boolean chackOpenCloseTime(Map timeMap) {

        boolean flg = false;

        if (null != timeMap.get(PatternSettingConst.OPEN) && null != timeMap.get(PatternSettingConst.CLOSE)) {

            int nOpen = toMinuteFromHHcMI60((String)timeMap.get(PatternSettingConst.OPEN));
            int nClose = toMinuteFromHHcMI60((String)timeMap.get(PatternSettingConst.CLOSE));

            if (nOpen < nClose) {
                flg = true;
            }
        }
        return flg;
    }

}
