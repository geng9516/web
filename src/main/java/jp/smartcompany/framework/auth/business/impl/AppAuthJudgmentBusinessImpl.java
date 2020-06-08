package jp.smartcompany.framework.auth.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.framework.appcontrol.*;
import jp.smartcompany.framework.auth.business.AppAuthJudgmentBusiness;
import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import jp.smartcompany.framework.sysboot.dto.AppAuthJudgmentDTO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.util.PsSession;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * アプリケーション起動権限判定Logicクラス
 * @author Xiao Wenpeng
 */
@Service
public class AppAuthJudgmentBusinessImpl implements AppAuthJudgmentBusiness {

    /** エラーメッセージ用 .*/
    private static final String ERR_SESSION_LANGUAGE = "LANGUAGE";
    /** エラーメッセージ用 .*/
    private static final String ERR_MAST_APPTREE = "MAST_APPTREE";
    /** エラーメッセージ用 .*/
    private static final String ERR_MAST_GROUPAPPPERMISSION = "MAST_GROUPAPPPERMISSION";
    /** 実行権限：実行可 .*/
    private static final String EXECUTE_AUTH_PERMISSION = "1";
    /** 実行権限：実行不可 .*/
    private static final String EXECUTE_AUTH_IMPROPRIETY = "0";
    /** 実行拒否設定：拒否 .*/
    private static final String REJECT_REFUSAL = "1";
    /** 実行拒否設定：許可 .*/
    private static final String REJECT_PERMISSION = "0";
    /** 種別：トップページ .*/
    private static final String TYPE_TOP_PAGE = "0";
    /** 種別：サイト .*/
    private static final String TYPE_SITE = "1";
    /** 種別：ダイアログアプリケーション .*/
    private static final String TYPE_DIALOG_APP = "2";
    /** 種別：アプリケーション .*/
    private static final String TYPE_APP = "3";
    /** 種別：サブアプリケーション .*/
    private static final String TYPE_SUB_APP = "4";
    /** 種別：画面 .*/
    private static final String TYPE_SCREEN = "5";
    /** 種別：ボタン .*/
    private static final String TYPE_BUTTON = "6";
    /** サイトID：トップページ .*/
    private static final String SITEID_TOP_PAGE = "TopPage";
    /** KEY作成用：サイト .*/
    private static final String STR_KEY_SITE = "Site:";
    /** KEY作成用：ダイアログアプリケーション .*/
    private static final String STR_KEY_DLG = "_Dlg:";
    /** KEY作成用：アプリケーション .*/
    private static final String STR_KEY_APP = "_App:";
    /** KEY作成用：サブアプリケーション .*/
    private static final String STR_KEY_SUB = "_Sub:";
    /** KEY作成用：画面 .*/
    private static final String STR_KEY_SCR = "_Scr:";
    /** KEY作成用：ボタン .*/
    private static final String STR_KEY_BTN = "_Btn:";
    /** ダイアログアプリケーション .*/
    private static final String DLG_APP = "TopPage_Dlg";
    /** アプリケーション情報設定用（サブアプリ情報） .*/
    private static final String APP_FOR_SUB = "AppForSub";
    /** アプリケーション情報設定用（画面情報） .*/
    public static final String APP_FOR_SCR = "AppForScr";

    /**
     * セッションにアプリケーション起動権限判定 結果情報をセットします.
     */
    @Override
    public TopPageInfo getAppAuthJudgmentInfo() {
        HttpServletRequest request = ContextUtil.getHttpRequest();
        if (request==null) {
            return null;
        }
        PsSession psSession = (PsSession) request.getSession().getAttribute(Constant.PS_SESSION);
        // グループ判定結果取得
        Map<String, List<LoginGroupBO>> loginGroups = psSession.getLoginGroups();
        // 言語区分 取得
        String sLanguage = psSession.getLanguage();
        if (StrUtil.isBlank(sLanguage)) {
            throw new GlobalException(ERR_SESSION_LANGUAGE);
        }
        if (MapUtil.isEmpty(loginGroups)){
            throw new GlobalException("Login group not found");
        }
        //*****************************************
        // アプリケーション起動権限情報取得
        //*****************************************
        // 種別毎用：サイト情報LinkedHashMap
        Map<String, AppAuthJudgmentEntity> hSiteInfo = MapUtil.newHashMap(true);
        // 種別毎用：ボタン情報LinkedHashMap
        Map<String, AppAuthJudgmentEntity> hButtonInfo = MapUtil.newHashMap(true);
        // 種別毎用：画面情報LinkedHashMap
        Map<String, AppAuthJudgmentEntity> hScreenInfo = MapUtil.newHashMap(true);
        // 種別毎用：SubApp情報LinkedHashMap
        Map<String, AppAuthJudgmentEntity> hSubAppInfo = MapUtil.newHashMap(true);
        // 種別毎用：App情報LinkedHashMap
        Map<String, AppAuthJudgmentEntity> hAppInfo = MapUtil.newHashMap(true);
        // 種別毎用：ダイアログ情報LinkedHashMap
        Map<String, AppAuthJudgmentEntity> hDialogAppInfo = MapUtil.newHashMap(true);
        TopPageInfo topPageInfo = getAppAuthInfo(loginGroups, sLanguage, hButtonInfo,
                hScreenInfo, hSubAppInfo, hAppInfo,
                hDialogAppInfo, hSiteInfo);
        //*********************************************************
        // ボタン情報の起動権限判定・設定
        //*********************************************************
        // ボタン判定情報LinkedHashMap
        Map<String, Object> hmButtonInfo = MapUtil.newHashMap(true);
        if (MapUtil.isNotEmpty(hButtonInfo)) {
            hmButtonInfo = judgmentAndDataConvert(permuteAppAuthInfo(hButtonInfo));
        }
        //*********************************************************
        // 画面情報の起動権限判定・設定
        //*********************************************************
        // 画面情報（App配下用）
        Map<String, ScreenInfo> hScreenForApp = MapUtil.newHashMap(true);
        // 画面情報（SubApp配下用）
        Map<String, ScreenInfo> hScreenForSubApp = MapUtil.newHashMap(true);
        // 画面情報（ダイアログ用）
        Map<String, ScreenInfo> hScreenForDlg = MapUtil.newHashMap(true);
        if (MapUtil.isNotEmpty(hScreenInfo)) {
            // 起動権限判定後（画面情報のみ）：画面判定情報LinkedHashMap
            Map<String, Object> hmScreenInfo;
            // hScreenInfoの並替え後、画面情報の起動権限判定
            hmScreenInfo = judgmentAndDataConvert(permuteAppAuthInfo(hScreenInfo));
            // 画面情報に配下のボタン情報を設定
            setButtonInfoforForScreenInfoSubord(
                    hmScreenInfo, hmButtonInfo, hScreenForApp,
                    hScreenForSubApp, hScreenForDlg);
        }


        //*********************************************************
        // SubApp情報の起動権限判定・設定
        //*********************************************************
        // SubApp情報（App情報へ設定用）
        // 起動権限判定後（SubApp情報のみ）：SubApp判定情報LinkedHashMap
        Map<String, Object> hmSubAppInfo;
        Map<String, Object> hSubAppForApp =MapUtil.newHashMap(true);
        if (MapUtil.isNotEmpty(hSubAppInfo)) {
            // hSubAppInfoの並替え後、SubApp情報の起動権限判定
            hmSubAppInfo = judgmentAndDataConvert(permuteAppAuthInfo(hSubAppInfo));
            // SubApp情報に画面情報mapを設定
            setMapInfo(hmSubAppInfo, hSubAppForApp, hScreenForSubApp, TYPE_SUB_APP);
        }

        //*********************************************************
        // App情報の起動権限判定・設定
        //*********************************************************
        // 起動権限判定後（App情報のみ）：App情報LinkedHashMap
        Map<String, Object> hmAppInfo;
        // App情報（サイト配下用）
        Map<String, Object> hAppForSite = MapUtil.newHashMap(true);
        // App情報（サイト配下用）hAppForSiteの順番を元にもどす
        Map<String, Object> hmapAppForSite = MapUtil.newHashMap(true);
        if (MapUtil.isNotEmpty(hAppInfo)) {
            // hAppInfoの並替え後、App情報の起動権限判定
            hmAppInfo = judgmentAndDataConvert(this.permuteAppAuthInfo(hAppInfo));
            // App情報にサブApp情報mapを設定
            setMapInfo(hmAppInfo, hAppForSite, hSubAppForApp, APP_FOR_SUB);
            // App情報に画面情報mapを設定
            setMapInfo(hmAppInfo, hAppForSite, hScreenForApp, APP_FOR_SCR);
            // hAppForSite(App情報:サイト配下用)のApp情報の順番を元に戻す
            for (String s : hmAppInfo.keySet()) {
                for (int nApp = 0; nApp < hmAppInfo.size(); nApp++) {
                    AppInfo sApp = (AppInfo) hAppForSite.get(s);
                    if (sApp != null) {
                        hmapAppForSite.put(s, sApp);
                    }
                }
            }
            // App情報にトップページ配下のものが含まれている場合、トップページへ設定
            setAppInfoforForToppageInfoSubord(hmapAppForSite, topPageInfo);
        }

        //*********************************************************
        // サイト情報の起動権限判定・設定
        //*********************************************************

        // 一時格納用：サイト情報
        Map<String, Object> hTempSite = MapUtil.newHashMap(true);
        if (MapUtil.isNotEmpty(hSiteInfo)) {
            // 起動権限判定後（サイト情報のみ）：起動サイト情報LinkedHashMap
            Map<String, Object> hmSiteInfo;
            // hSiteInfoの並替え後、サイト情報の起動権限判定
            hmSiteInfo = judgmentAndDataConvert(permuteAppAuthInfo(hSiteInfo));
            // サイト情報にApp情報mapを設定
            setMapInfo(hmSiteInfo, hTempSite, hmapAppForSite, TYPE_SITE);
            if (MapUtil.isNotEmpty(hTempSite)) {
                topPageInfo.setSiteInfo(hTempSite);
            }
        }

        //*********************************************************
        // ダイアログアプリケーション情報の起動権限判定・設定
        //*********************************************************
        if (MapUtil.isNotEmpty(hDialogAppInfo)) {
            // 起動権限判定後（ダイアログ情報のみ）：ダイアログ情報LinkedHashMap
            Map<String, Object> hmDialogAppInfo;
            // 一時格納用：ダイアログ情報
            Map<String, Object> hTempDlgApp = MapUtil.newHashMap(true);
            // hDialogAppInfoの並替え後、ダイアログ情報の起動権限判定
            hmDialogAppInfo = judgmentAndDataConvert(permuteAppAuthInfo(hDialogAppInfo));
            // ダイアログ情報に画面情報mapを設定
            setMapInfo(hmDialogAppInfo, hTempDlgApp, hScreenForDlg, TYPE_DIALOG_APP);
            if (MapUtil.isNotEmpty(hTempDlgApp)) {
                // ダイアログ情報をトップページへ設定
                topPageInfo.setDlgAppInfo(hTempDlgApp);
            }
        }
        return topPageInfo;
    }

    /**
     * トップページ配下のアプリケーション情報をトップページへ設定
     * @param hAppForSite アプリケーション情報
     * @param topPageInfo トップページ
     */
    private void setAppInfoforForToppageInfoSubord(
            Map<String, Object> hAppForSite,
            TopPageInfo topPageInfo) {
        // App情報（トップページ配下用）
        Map<String, Object> hAppForTop = MapUtil.newHashMap(true);
        // 設定済み情報 削除用リスト
        List<String> lDelList =CollUtil.newArrayList();
        for (String s : hAppForSite.keySet()) {
            for (int nApp = 0; nApp < hAppForSite.size(); nApp++) {
                AppInfo sApp = (AppInfo) hAppForSite.get(s);
                if (SITEID_TOP_PAGE.equals(sApp.getSiteID())) {
                    hAppForTop.put(s, sApp);
                    lDelList.add(s); // 設定済み情報のキーをリストに設定。
                }
            }
        }
        if (MapUtil.isNotEmpty(hAppForTop)) {
            topPageInfo.setAppInfo(hAppForTop); // トップページへ設定
        }
        for (String s : lDelList) {
            hAppForSite.remove(s); // TopPageへ設定済み情報 削除
        }
    }

    /**
     * Mapキー同士を比較し、配下かどうか判定する.
     * @param hAppAuthJudgment アプリケーション起動権限判定情報
     * @param sKey mapキー
     * @param sType 配下の情報の種別
     * @param sIdKey IDキー
     * @return LinkedHashMap アプリケーション起動権限判定情報
     */
    private Map<String, Object> comparesMapKey(
            Map<String,Object> hAppAuthJudgment,
            String sKey,
            String sType,
            String sIdKey) {
        // アプリケーション起動権限判定情報
        Map<String, Object> hAppAuthInfo = MapUtil.newHashMap(true);
        List<String> lDelList = CollUtil.newArrayList(); // 設定済み情報 削除用リスト
        for (String s : hAppAuthJudgment.keySet()) {
            for (int nCount = 0; nCount < hAppAuthJudgment.size(); nCount++) {
                String sCompKey = String.valueOf(s);
                Object values = hAppAuthJudgment.get(sCompKey);
                // 対象のキー：sCompKeyにsKeyが含まれているかどうか
                if (sCompKey.contains(sKey)) {
                    // 同一のものかチェック
                    String sValuesKey = null;
                    if (TYPE_SITE.equals(sType)) {
                        // サイト配下のapp情報のサイトID取得
                        sValuesKey = ((AppInfo) values).getSiteID();
                    } else if (TYPE_DIALOG_APP.equals(sType)) {
                        // ダイアログ配下の画面情報のappID取得
                        sValuesKey = ((ScreenInfo) values).getAppID();
                    } else if (APP_FOR_SUB.equals(sType)) {
                        // アプリ配下のサブアプリ情報のappID取得
                        sValuesKey = ((SubAppInfo) values).getAppID();
                    } else if (APP_FOR_SCR.equals(sType)) {
                        // アプリ配下の画面情報のappID取得
                        sValuesKey = ((ScreenInfo) values).getAppID();
                    } else if (TYPE_SUB_APP.equals(sType)) {
                        // サブアプリ配下の画面情報のsubAppID取得
                        sValuesKey = ((ScreenInfo) values).getSubAppID();
                    }
                    // IDが同一かチェック
                    if (StrUtil.equals(sValuesKey,sIdKey)) {
                        hAppAuthInfo.put(sCompKey, values);
                        lDelList.add(sCompKey); // 設定済み情報のキーをリストに設定。
                    }
                }
            }
        }
        for (String s : lDelList) {
            hAppAuthJudgment.remove(s); // 設定済み情報 削除
        }
        return hAppAuthInfo; // LinkedHashMapを返す
    }

    /**
     * 配下のmap情報を設定判定する.
     * @param hmAppAuthJudgment アプリケーション起動権限判定情報
     * @param hAppAuthInfo アプリケーション起動権限判定情報（配下情報設定用）
     * @param hSubord アプリケーション起動権限判定情報（配下用）
     * @param sType 種別
     */
    private void setMapInfo(
            Map<String,Object> hmAppAuthJudgment,
            Map<String, Object> hAppAuthInfo,
            Map hSubord,
            String sType) {
        Map<String, Object> hSubordInfo; // 配下となるmap
        for (String s : hmAppAuthJudgment.keySet()) {
            for (int nCnt = 0; nCnt < hmAppAuthJudgment.size(); nCnt++) {
                if (TYPE_SITE.equals(sType)) {
                    // サイト配下のapp情報を設定
                    SiteInfo appAuth = (SiteInfo) hmAppAuthJudgment.get(s);
                    hSubordInfo = comparesMapKey(hSubord, s,
                            TYPE_SITE,
                            appAuth.getSiteID());
                    if (hSubordInfo.size() > 0) {
                        appAuth.setAppInfo(hSubordInfo);        // 配下のmap情報をクラスへ設定
                        hAppAuthInfo.put(s, appAuth); // クラス情報mapへ設定
                    }
                } else if (TYPE_DIALOG_APP.equals(sType)) {

                    // ダイアログ配下の画面情報を設定
                    AppInfo appAuth = (AppInfo) hmAppAuthJudgment.get(s);
                    hSubordInfo = comparesMapKey(hSubord, s,
                            TYPE_DIALOG_APP,
                            appAuth.getAppID());
                    if (hSubordInfo.size() > 0) {
                        appAuth.setScreenInfo(hSubordInfo);     // 配下のmap情報をクラスへ設定
                        hAppAuthInfo.put(s, appAuth); // クラス情報mapへ設定
                    }
                } else if (APP_FOR_SUB.equals(sType)) {

                    // アプリ配下のサブアプリ情報を設定
                    AppInfo appAuth = (AppInfo) hmAppAuthJudgment.get(s);
                    hSubordInfo = comparesMapKey(hSubord, s,
                            APP_FOR_SUB,
                            appAuth.getAppID());
                    if (hSubordInfo.size() > 0) {
                        appAuth.setSubAppInfo(hSubordInfo);     // 配下のmap情報をクラスへ設定
                        hAppAuthInfo.put(s, appAuth); // クラス情報mapへ設定
                    }
                } else if (APP_FOR_SCR.equals(sType)) {

                    // アプリ配下の画面情報を設定
                    AppInfo appAuth = (AppInfo) hmAppAuthJudgment.get(s);
                    hSubordInfo = comparesMapKey(hSubord, s,
                            APP_FOR_SCR,
                            appAuth.getAppID());
                    if (hSubordInfo.size() > 0) {
                        appAuth.setScreenInfo(hSubordInfo);     // 配下のmap情報をクラスへ設定
                        hAppAuthInfo.put(s, appAuth); // クラス情報mapへ設定
                    }
                } else if (TYPE_SUB_APP.equals(sType)) {

                    // サブアプリ配下の画面情報を設定
                    SubAppInfo appAuth = (SubAppInfo) hmAppAuthJudgment.get(s);
                    hSubordInfo = comparesMapKey(hSubord, s,
                            TYPE_SUB_APP,
                            appAuth.getSubAppID());
                    if (hSubordInfo.size() > 0) {
                        appAuth.setScreenInfo(hSubordInfo);     // 配下のmap情報をクラスへ設定
                        hAppAuthInfo.put(s, appAuth); // クラス情報mapへ設定
                    }
                }
            }
        }
    }

    /**
     * 各ボタン情報を配下の画面情報へ設定
     * @param hmScreenInfo 画面情報
     * @param hmButtonInfo ボタン情報
     * @param hScreenForApp アプリケーション配下の画面情報
     * @param hScreenForSubApp サブアプリケーション配下の画面情報
     * @param hScreenForDlg ダイアログアプリケーション配下の画面情報
     */
    private void setButtonInfoforForScreenInfoSubord(
            Map<String, Object> hmScreenInfo,
            Map<String, Object> hmButtonInfo,
            Map<String, ScreenInfo> hScreenForApp,
            Map<String, ScreenInfo> hScreenForSubApp,
            Map<String, ScreenInfo> hScreenForDlg) {
        // ボタン情報（App配下用）
        Map<String, ButtonInfo> hButtonForApp;
        // ボタン情報（SubApp配下用）
        Map<String, ButtonInfo> hButtonForSubApp;
        // ボタン情報（ダイアログ用）
        Map<String, ButtonInfo> hButtonForDlg;
        // 設定済みApp起動権限情報 削除用
        List<String> lDelList;
        for (String s : hmScreenInfo.keySet()) {
            for (int nScrCnt = 0; nScrCnt < hmScreenInfo.size(); nScrCnt++) {
                ScreenInfo screenIte = (ScreenInfo) hmScreenInfo.get(s);
                hButtonForDlg = MapUtil.newHashMap(true);
                hButtonForApp = MapUtil.newHashMap(true);
                hButtonForSubApp = MapUtil.newHashMap(true);
                lDelList = CollUtil.newArrayList();
                // 各画面情報配下のボタン情報を判別
                for (String value : hmButtonInfo.keySet()) {
                    for (int nButtonCnt = 0; nButtonCnt < hmButtonInfo.size(); nButtonCnt++) {
                        String sButtonKey = String.valueOf(value);
                        ButtonInfo buttonInfo = (ButtonInfo) hmButtonInfo.get(sButtonKey);
                        // ボタンキーに画面キーが含まれているか
                        if (sButtonKey.contains(s)) {
                            // 画面キーが同一かチェック
                            if (buttonInfo.getScreenID().equals(screenIte.getScreenID())) {
                                if (sButtonKey.contains(DLG_APP)) {
                                    // ダイアログ用ボタン
                                    hButtonForDlg.put(sButtonKey, buttonInfo);
                                } else {
                                    if (StrUtil.isBlank(buttonInfo.getSubAppID())) {
                                        // App配下
                                        hButtonForApp.put(sButtonKey, buttonInfo);
                                    } else {
                                        // SubApp配下
                                        hButtonForSubApp.put(sButtonKey, buttonInfo);
                                    }
                                }
                                // 設定済みボタン情報のキーをリストに設定
                                lDelList.add(sButtonKey);
                            }
                        }
                    }
                }
                // mapへ格納
                if (s.contains(DLG_APP)) {
                    screenIte.setButtonInfo(hButtonForDlg); // ダイアログ用
                    hScreenForDlg.put(s, screenIte);     // 画面情報設定：ダイアログ用
                } else if (StrUtil.isBlank(screenIte.getSubAppID())) {
                    screenIte.setButtonInfo(hButtonForApp);    // ボタン情報を画面情報へ設定
                    hScreenForApp.put(s, screenIte);        // 画面情報設定:App配下
                } else {
                    screenIte.setButtonInfo(hButtonForSubApp); // ボタン情報を画面情報へ設定
                    hScreenForSubApp.put(s, screenIte);     // 画面情報設定:SubApp配下
                }
                // 設定済みボタン情報 削除
                for (String value : lDelList) {
                    hmButtonInfo.remove(value);
                }
            }
        }
    }

    /**
     * アプリケーション起動権限情報取得.
     * @param hGroups グループ判定
     * @param sLanguage 言語区分
     * @param hButtonInfo ボタン情報
     * @param hScreenInfo 画面情報
     * @param hSubAppInfo サブアプリケーション
     * @param hAppInfo アプリケーション
     * @param hDialogAppInfo ダイアログアプリケーション
     * @param hSiteInfo サイト情報
     * @return topPageInfo トップページ
     */
    private TopPageInfo getAppAuthInfo(Map<String, List<LoginGroupBO>> hGroups, String sLanguage,
                                       Map<String, AppAuthJudgmentEntity> hButtonInfo,
                                       Map<String, AppAuthJudgmentEntity> hScreenInfo,
                                       Map<String, AppAuthJudgmentEntity> hSubAppInfo,
                                       Map<String, AppAuthJudgmentEntity> hAppInfo,
                                       Map<String, AppAuthJudgmentEntity> hDialogAppInfo,
                                       Map<String, AppAuthJudgmentEntity> hSiteInfo) {
        ScCacheUtil scCacheUtil = SpringUtil.getBean("scCacheUtil");
        TopPageInfo topPageInfo = new TopPageInfo();
        for(List<LoginGroupBO> groupList : hGroups.values()) {
            for (LoginGroupBO loginGroupBO : groupList) {
                // グループ判定結果 チェック
                if (StrUtil.isBlank(loginGroupBO.getGroupCode())
                        || StrUtil.isBlank(loginGroupBO.getSystemCode())) {
                    throw new GlobalException("group empty");
                }
                // 常駐変数から取得
                List<AppAuthJudgmentDTO> lAppAuthJudgInfo = scCacheUtil.getAppAuthJudgmentCache(
                        loginGroupBO.getSystemCode() + "_" +
                        loginGroupBO.getGroupCode() + "_" + sLanguage);
                // ダイアログ用の情報判定フラグ
                boolean bDlgFlag = false;
                // アプリケーション起動権限情報取得分 処理を繰り返す。
                if (lAppAuthJudgInfo != null ) {
                    for (AppAuthJudgmentDTO appEntity : lAppAuthJudgInfo) {
                        // マスタデータのチェック
                        // 種別
                        checkType(appEntity.getMtrCtype());
                        // 実行権限
                        checkPermission(appEntity.getMgpCpermission());
                        // 実行拒否設定
                        checkReject(appEntity.getMgpCreject());
                        // 種別毎に格納
                        if (TYPE_BUTTON.equals(appEntity.getMtrCtype())) {
                            // ボタン情報
                            setButtonKey(appEntity, hButtonInfo, bDlgFlag);
                        } else if (TYPE_SCREEN.equals(appEntity.getMtrCtype())) {
                            // 画面情報
                            setScreenKey(appEntity, hScreenInfo, bDlgFlag);
                        } else if (TYPE_SUB_APP.equals(appEntity.getMtrCtype())) {
                            // SubApp情報
                            setSubAppKey(appEntity, hSubAppInfo);
                            bDlgFlag = false;
                        } else if (TYPE_APP.equals(appEntity.getMtrCtype())) {
                            // App情報
                            setAppKey(appEntity, hAppInfo);
                            bDlgFlag = false;
                        } else if (TYPE_DIALOG_APP.equals(appEntity.getMtrCtype())) {
                            // ダイアログ情報
                            setDialogAppKey(appEntity, hDialogAppInfo);
                            bDlgFlag = true;
                        } else if (TYPE_SITE.equals(appEntity.getMtrCtype())) {
                            // サイト情報
                            setSiteKey(appEntity, hSiteInfo);
                        } else if (TYPE_TOP_PAGE.equals(appEntity.getMtrCtype())) {
                            // トップページ情報
                            topPageInfo = (TopPageInfo) convertAppAuthData(appEntity);
                        }
                    }
                }
            }
        }
        return topPageInfo;
    }

    /**
     * アプリケーション起動権限情報を並び順(MTR_NSEQ)に並び替える。
     * @param hAppAuthInfo アプリケーション起動権限情報
     * @return hmapAppInfo アプリケーション起動権限情報
     */
    private Map<String, AppAuthJudgmentEntity> permuteAppAuthInfo(
            Map<String, AppAuthJudgmentEntity> hAppAuthInfo) {
        Map<String, AppAuthJudgmentEntity> hmapAppAuthInfo =
               MapUtil.newHashMap(true);
        Iterator<String> iKey = hAppAuthInfo.keySet().iterator();
        while (iKey.hasNext()) {
            String sKey = iKey.next();
            AppAuthJudgmentEntity entity = hAppAuthInfo.get(sKey);
            int nSeq = entity.getMtrNseq().intValue();
            String sConKey ;
            AppAuthJudgmentEntity conEntity ;
            for (String s : hAppAuthInfo.keySet()) {
                sConKey = s;
                conEntity = hAppAuthInfo.get(sConKey);
                int nConSeq = conEntity.getMtrNseq().intValue();
                // 並び順(MTR_NSEQ)を比較
                if (nSeq > nConSeq) {
                    nSeq = nConSeq;
                    sKey = sConKey;
                    entity = conEntity;
                }
            }
            hmapAppAuthInfo.put(sKey, entity);
            // 並替え済みのものは削除
            hAppAuthInfo.remove(sKey);
            iKey = hAppAuthInfo.keySet().iterator();
        }
        return hmapAppAuthInfo;
    }

    /**
     * アプリケーション起動権限判定情報 判定.
     * @param hAppAuthJudgment アプリケーション起動権限判定情報（未判定）
     * @return LinkedHashMap アプリケーション起動権限判定情報（判定済み）
     */
    private Map<String, Object> judgmentAndDataConvert(
            Map<String, AppAuthJudgmentEntity> hAppAuthJudgment) {
        // 起動権限判定後（サイト情報のみ）：起動サイト情報LinkedHashMap
        Map<String, Object> hmSiteInfo = MapUtil.newHashMap(true);
        // 起動権限判定後（ダイアログ情報のみ）：ダイアログ情報LinkedHashMap
        Map<String, Object> hmDialogAppInfo = MapUtil.newHashMap(true);
        // 起動権限判定後（App情報のみ）：App情報LinkedHashMap
        Map<String, Object> hmAppInfo = MapUtil.newHashMap(true);
        // 起動権限判定後（SubApp情報のみ）：SubApp判定情報LinkedHashMap
        Map<String, Object> hmSubAppInfo = MapUtil.newHashMap(true);
        // 起動権限判定後（画面情報のみ）：画面判定情報LinkedHashMap
        Map<String, Object> hmScreenInfo = MapUtil.newHashMap(true);
        // ボタン判定情報LinkedHashMap
        Map<String, Object> hmButtonInfo = MapUtil.newHashMap(true);
        // 種別
        String sType = null;
        Iterator<String> keyIterator = hAppAuthJudgment.keySet().iterator();
        Iterator<AppAuthJudgmentEntity> valuesIterator = hAppAuthJudgment.values().iterator();
        while (keyIterator.hasNext()) {
            for (int nCnt = 0; nCnt < hAppAuthJudgment.size(); nCnt++) {
                AppAuthJudgmentEntity appEntity =valuesIterator.next();
                String sKey = keyIterator.next();
                // ボタン情報の場合
                if (TYPE_BUTTON.equals(appEntity.getMtrCtype())) {
                    if (EXECUTE_AUTH_IMPROPRIETY.equals(appEntity.getMgpCpermission()) ||
                            REJECT_REFUSAL.equals(appEntity.getMgpCreject())) {
                        // 実行拒否設定フラグ=1 または実行権限フラグ=0の場合：false
                        // ボタン情報クラスへ設定
                        ButtonInfo buttonInfo = (ButtonInfo)convertAppAuthData(appEntity);
                        buttonInfo.setPermission(false);
                        hmButtonInfo.put(sKey, buttonInfo);
                    } else if (EXECUTE_AUTH_PERMISSION.equals(appEntity.getMgpCpermission())) {
                        // ボタン情報クラスへ設定:実行権限フラグ=1の場合：true
                        ButtonInfo buttonInfo = (ButtonInfo) this.convertAppAuthData(appEntity);
                        buttonInfo.setPermission(true);
                        hmButtonInfo.put(sKey, buttonInfo);
                    }
                }
                // 実行権限フラグ=0または実行拒否設定フラグ=1 の以外の場合、
                // 各情報クラスへ設定（ボタン情報以外）
                if (!(EXECUTE_AUTH_IMPROPRIETY.equals(
                        appEntity.getMgpCpermission())
                        || REJECT_REFUSAL.equals(
                        appEntity.getMgpCreject()))) {
                    if (TYPE_SITE.equals(appEntity.getMtrCtype())) {
                        // サイト情報
                        hmSiteInfo.put(sKey, this.convertAppAuthData(appEntity));
                        if (sType == null) {
                            // LinkedHashMapを戻すため区別用
                            sType = appEntity.getMtrCtype();
                        }
                    } else if (TYPE_DIALOG_APP.equals(
                            appEntity.getMtrCtype())) {
                        // ダイアログ情報
                        hmDialogAppInfo.put(sKey, this.convertAppAuthData(appEntity));
                        if (sType == null) {
                            // LinkedHashMapを戻すため区別用
                            sType = appEntity.getMtrCtype();
                        }
                    } else if (TYPE_APP.equals(appEntity.getMtrCtype())) {
                        // App情報
                        hmAppInfo.put(sKey, this.convertAppAuthData(appEntity));
                        if (sType == null) {
                            // LinkedHashMapを戻すため区別用
                            sType = appEntity.getMtrCtype();
                        }
                    } else if (TYPE_SUB_APP.equals(
                            appEntity.getMtrCtype())) {
                        // SubApp情報
                        hmSubAppInfo.put(sKey, this.convertAppAuthData(appEntity));
                        if (sType == null) {
                            // LinkedHashMapを戻すため区別用
                            sType = appEntity.getMtrCtype();
                        }
                    } else if (TYPE_SCREEN.equals(appEntity.getMtrCtype())) {
                        // 画面情報
                        hmScreenInfo.put(sKey, this.convertAppAuthData(appEntity));
                        if (sType == null) {
                            // LinkedHashMapを戻すため区別用
                            sType = appEntity.getMtrCtype();
                        }
                    }
                }
            }
        }

        // LinkedHashMapを戻す
        if (TYPE_SITE.equals(sType)) {
            // サイト情報
            return hmSiteInfo;
        } else if (TYPE_DIALOG_APP.equals(sType)) {
            // ダイアログ情報
            return hmDialogAppInfo;
        } else if (TYPE_APP.equals(sType)) {
            // App情報
            return hmAppInfo;
        } else if (TYPE_SUB_APP.equals(sType)) {
            // SubApp情報
            return hmSubAppInfo;
        } else if (TYPE_SCREEN.equals(sType)) {
            // 画面情報
            return hmScreenInfo;
        } else {
            // ボタン情報
            return hmButtonInfo;
        }
    }

    /**
     * 種別の整合性チェック.
     * @param sType 種別
     */
    private void checkType(String sType) {

        // 種別のチェック
        if (!(TYPE_TOP_PAGE.equals(sType)
                ||TYPE_SITE.equals(sType)
                || TYPE_DIALOG_APP.equals(sType)
                || TYPE_APP.equals(sType)
                || TYPE_SUB_APP.equals(sType)
                || TYPE_SCREEN.equals(sType)
                || TYPE_BUTTON.equals(sType))) {
            throw new GlobalException(
                    ERR_MAST_APPTREE);
        }
    }

    /**
     * 実行権限の整合性チェック.
     * @param sPermission 実行権限
     * グループ別アプリケーション権限マスタ 実行権限 不正例外
     */
    private void checkPermission(String sPermission) {
        // 実行権限のチェック
        if (!(EXECUTE_AUTH_IMPROPRIETY.equals(sPermission)
                || EXECUTE_AUTH_PERMISSION.equals(sPermission))) {
            throw new GlobalException(
                    ERR_MAST_GROUPAPPPERMISSION);
        }
    }

    /**
     * 実行拒否設定の整合性チェック.
     * @param sReject 実行拒否設定
     * グループ別アプリケーション権限マスタ 実行拒否設定 不正例外
     */
    private void checkReject(String sReject) {
        // 実行拒否設定のチェック
        if (!(REJECT_PERMISSION.equals(sReject)
                || REJECT_REFUSAL.equals(sReject))) {
            throw new GlobalException(
                    ERR_MAST_GROUPAPPPERMISSION);
        }
    }

    /**
     * サイト情報を設定する.
     * @param appEntity アプリケーション起動権限情報
     * @param hSiteInfo サイト情報
     */
    private void setSiteKey(AppAuthJudgmentEntity appEntity,
                                  LinkedHashMap<String, AppAuthJudgmentEntity> hSiteInfo) {
        // キー作成
        String sKey = STR_KEY_SITE + appEntity.getMtrCsiteid();
        // サイト情報mapに設定
        setMapInfoOfType(appEntity, hSiteInfo, sKey);
    }

    /**
     * 各情報毎にmapに設定.
     * @param appEntity アプリケーション起動権限情報
     * @param hAppAuthInfo アプリケーション起動権限情報
     * @param sKey mapキー
     */
    private void setMapInfoOfType(AppAuthJudgmentEntity appEntity,
                                         Map<String, AppAuthJudgmentEntity> hAppAuthInfo,
                                        String sKey) {
        //キーに重複があった場合
        if (hAppAuthInfo.containsKey(sKey)) {
            // 実行権限・実行拒否設定で上書きするか判断
            if (isOverlapsKey(appEntity, hAppAuthInfo.get(sKey).getMgpCpermission(),
                    hAppAuthInfo.get(sKey).getMgpCreject())) {
                hAppAuthInfo.put(sKey, appEntity); // 上書き
            }
        } else {
            hAppAuthInfo.put(sKey, appEntity);     // 追加
        }
    }

    /**
     * mapキーに重複のものがあった場合
     * 権限で判定し、同じmapキーで書き込むかどうか判断.
     * @param appEntity アプリケーション起動権限情報
     * @param sPermission 実行権限
     * @param sReject 実行拒否設定
     * @return boolean
     */
    private boolean isOverlapsKey(AppAuthJudgmentEntity appEntity,
                                        String sPermission,
                                        String sReject) {

        //前データの実行拒否設定フラグが、拒否（１）以外の場合
        if (!REJECT_REFUSAL.equals(sReject)) {
            //前データの実行権限フラグ!=1　または　実行拒否設定フラグ=1の場合
            return !EXECUTE_AUTH_PERMISSION.equals(sPermission)
                    || REJECT_REFUSAL.equals(appEntity.getMgpCreject());
        }
        return false;
    }

    /**
     * ボタン情報を設定する.
     * @param appEntity アプリケーション起動権限情報
     * @param hButtonInfo ボタン情報
     * @param bDialogFlag ダイアログ用の情報判定フラグ
     */
    private void setButtonKey(AppAuthJudgmentEntity appEntity,
                                    Map<String, AppAuthJudgmentEntity> hButtonInfo,
                                    boolean bDialogFlag) {
        // キー作成
        String sKey;
        if (SITEID_TOP_PAGE.equals(appEntity.getMtrCsiteid())) {
            if (bDialogFlag) {

                // トップページ配下のダイアログの画面配下のボタン
                sKey = STR_KEY_SITE
                        + SITEID_TOP_PAGE
                        + STR_KEY_DLG
                        + appEntity.getMtrCappid()
                        + STR_KEY_SCR
                        + appEntity.getMtrCscreenid()
                        + STR_KEY_BTN
                        + appEntity.getMtrCbuttonid();
            } else {

                if (appEntity.getMtrCsubappid() != null
                        && !"".equals(appEntity.getMtrCsubappid())) {

                    // トップページ配下のApp配下のSubAppの画面配下のボタン
                    sKey = STR_KEY_SITE
                            + SITEID_TOP_PAGE
                            + STR_KEY_APP
                            + appEntity.getMtrCappid()
                            + STR_KEY_SUB
                            + appEntity.getMtrCsubappid()
                            + STR_KEY_SCR
                            + appEntity.getMtrCscreenid()
                            + STR_KEY_BTN
                            + appEntity.getMtrCbuttonid();

                } else {

                    // トップページ配下のAppの画面配下のボタン
                    sKey = STR_KEY_SITE
                            + SITEID_TOP_PAGE
                            + STR_KEY_APP
                            + appEntity.getMtrCappid()
                            + STR_KEY_SCR
                            + appEntity.getMtrCscreenid()
                            + STR_KEY_BTN
                            + appEntity.getMtrCbuttonid();
                }
            }
        } else {
            if (appEntity.getMtrCsubappid() != null
                    && !"".equals(appEntity.getMtrCsubappid())) {

                // サイト配下のApp配下のSubAppの画面配下のボタン
                sKey = STR_KEY_SITE
                        + appEntity.getMtrCsiteid()
                        + STR_KEY_APP
                        + appEntity.getMtrCappid()
                        + STR_KEY_SUB
                        + appEntity.getMtrCsubappid()
                        + STR_KEY_SCR
                        + appEntity.getMtrCscreenid()
                        + STR_KEY_BTN
                        + appEntity.getMtrCbuttonid();
            } else {

                // サイト配下のApp配下の画面配下のボタン
                sKey = STR_KEY_SITE
                        + appEntity.getMtrCsiteid()
                        + STR_KEY_APP
                        + appEntity.getMtrCappid()
                        + STR_KEY_SCR
                        + appEntity.getMtrCscreenid()
                        + STR_KEY_BTN
                        + appEntity.getMtrCbuttonid();
            }
        }
        // ボタン情報mapに設定
        setMapInfoOfType(appEntity, hButtonInfo, sKey);
    }

    /**
     * 画面情報を設定する.
     * @param appEntity アプリケーション起動権限情報
     * @param hScreenInfo 画面情報
    　　 * @param bDialogFlag ダイアログ用の情報判定フラグ
     */
    private void setScreenKey(AppAuthJudgmentEntity appEntity,
                                   Map<String, AppAuthJudgmentEntity> hScreenInfo,
                                    boolean bDialogFlag) {
        // キー作成
        String sKey;
        if (appEntity.getMtrCsubappid() != null
                && !"".equals(appEntity.getMtrCsubappid())) {

            // SubApp配下の画面
            sKey = STR_KEY_SITE
                    + appEntity.getMtrCsiteid()
                    + STR_KEY_APP
                    + appEntity.getMtrCappid()
                    + STR_KEY_SUB
                    + appEntity.getMtrCsubappid()
                    + STR_KEY_SCR
                    + appEntity.getMtrCscreenid();
        } else {
            if (SITEID_TOP_PAGE.equals(appEntity.getMtrCsiteid())) {
                if (bDialogFlag) {

                    // トップページ配下に配置されたダイアログの画面
                    sKey = STR_KEY_SITE
                            + SITEID_TOP_PAGE
                            + STR_KEY_DLG
                            + appEntity.getMtrCappid()
                            + STR_KEY_SCR
                            + appEntity.getMtrCscreenid();
                } else {

                    // トップページ配下に配置されたAppの画面
                    sKey = STR_KEY_SITE
                            + SITEID_TOP_PAGE
                            + STR_KEY_APP
                            + appEntity.getMtrCappid()
                            + STR_KEY_SCR
                            + appEntity.getMtrCscreenid();
                }
            } else {

                // サイト配下のApp配下の画面
                sKey = STR_KEY_SITE
                        + appEntity.getMtrCsiteid()
                        + STR_KEY_APP
                        + appEntity.getMtrCappid()
                        + STR_KEY_SCR
                        + appEntity.getMtrCscreenid();
            }
        }
        // 画面情報mapに設定
        setMapInfoOfType(appEntity, hScreenInfo, sKey);
    }

    /**
     * サブアプリケーション情報を設定する.
     * @param appEntity アプリケーション起動権限情報
     * @param hSubAppInfo サブアプリケーション情報
     */
    private void setSubAppKey(AppAuthJudgmentEntity appEntity,
                                    Map<String, AppAuthJudgmentEntity> hSubAppInfo) {
        // キー作成
        String sKey = STR_KEY_SITE
                + appEntity.getMtrCsiteid()
                + STR_KEY_APP
                + appEntity.getMtrCappid()
                + STR_KEY_SUB
                + appEntity.getMtrCsubappid();
        // サブApp情報mapに設定
        setMapInfoOfType(appEntity, hSubAppInfo, sKey);
    }

    /**
     * アプリケーション情報を設定する.
     * @param appEntity アプリケーション起動権限情報
     * @param hAppInfo アプリケーション情報
     */
    private void setAppKey(AppAuthJudgmentEntity appEntity, Map<String, AppAuthJudgmentEntity> hAppInfo) {
        // キー作成
        String sKey;
        if (SITEID_TOP_PAGE.equals(appEntity.getMtrCsiteid())) {
            // トップページ配下に配置されたApp
            sKey = STR_KEY_SITE
                    + SITEID_TOP_PAGE
                    + STR_KEY_APP
                    + appEntity.getMtrCappid();
        } else {

            // サイト配下のApp
            sKey = STR_KEY_SITE
                    + appEntity.getMtrCsiteid()
                    + STR_KEY_APP
                    + appEntity.getMtrCappid();
        }
        // App情報mapに設定
        this.setMapInfoOfType(appEntity, hAppInfo, sKey);
    }

    /**
     * ダイアログアプリケーション情報を設定する.
     * @param appEntity アプリケーション起動権限情報
     * @param hDialogAppInfo ダイアログアプリケーション情報
     */
    private void setDialogAppKey(AppAuthJudgmentEntity appEntity, Map<String, AppAuthJudgmentEntity> hDialogAppInfo) {
        // キー作成
        String sKey = STR_KEY_SITE
                + SITEID_TOP_PAGE
                + STR_KEY_DLG
                + appEntity.getMtrCappid();
        // ダイアログApp情報mapに設定
        setMapInfoOfType(appEntity, hDialogAppInfo, sKey);
    }

    /**
     * サイト情報を設定する.
     * @param appEntity アプリケーション起動権限情報
     * @param hSiteInfo サイト情報
     */
    private void setSiteKey(AppAuthJudgmentEntity appEntity, Map<String, AppAuthJudgmentEntity> hSiteInfo) {
        // キー作成
        String sKey = STR_KEY_SITE + appEntity.getMtrCsiteid();
        // サイト情報mapに設定
        this.setMapInfoOfType(appEntity, hSiteInfo, sKey);
    }

    /**
     * 各情報クラスの情報 設定.
     * @param appEntity 各情報クラス
     * @return 各情報クラス
     */
    private Object convertAppAuthData(AppAuthJudgmentEntity appEntity) {
        // 各情報クラスの情報 設定
        AbstractInfo absInfo;
        if (TYPE_TOP_PAGE.equals(appEntity.getMtrCtype())) {
            absInfo = new TopPageInfo();
        } else if (TYPE_SITE.equals(appEntity.getMtrCtype())) {
            absInfo = new SiteInfo();
            absInfo.setAppAutoLoad(appEntity.getMtrCappautoload());
        } else if (TYPE_DIALOG_APP.equals(appEntity.getMtrCtype())) {
            absInfo = new AppInfo();
        } else if (TYPE_APP.equals(appEntity.getMtrCtype())) {
            absInfo = new AppInfo();
        } else if (TYPE_SUB_APP.equals(appEntity.getMtrCtype())) {
            absInfo = new SubAppInfo();
        } else if (TYPE_SCREEN.equals(appEntity.getMtrCtype())) {
            absInfo = new ScreenInfo();
            absInfo.setOnlineHelpURL(appEntity.getMtrConlinehelpurl());
            absInfo.setHelpWindowOpen(appEntity.getMtrConlinehelpattr());
        } else {
            absInfo = new ButtonInfo();
        }
        absInfo.setSiteID(appEntity.getMtrCsiteid());
        absInfo.setAppID(appEntity.getMtrCappid());
        absInfo.setSubAppID(appEntity.getMtrCsubappid());
        absInfo.setScreenID(appEntity.getMtrCscreenid());
        absInfo.setButtonID(appEntity.getMtrCbuttonid());
        absInfo.setName(appEntity.getMtrObjname());
        absInfo.setVersion(appEntity.getMtrCversion());
        absInfo.setSystem(appEntity.getMtrCsystemid());
        absInfo.setAppTemplateID(appEntity.getMtrCtemplateid());
        absInfo.setURL(appEntity.getMtrCurl());
        absInfo.setImageURL(appEntity.getMtrCimageurl());
        absInfo.setSiteDirections(appEntity.getMtrSitecaption());
        absInfo.setType(appEntity.getMtrCtype());
        absInfo.setDefSearchObj(appEntity.getMtrCdefaulttargetuser());
        absInfo.setBaseDateType(appEntity.getMtrCcriterialdatetype());
        absInfo.setSearchRangeType(appEntity.getMtrCdatapermissiontype());
        absInfo.setDomainId(appEntity.getMtrCdomainid());
        absInfo.setIframeFlag(appEntity.getMtrCiframeflag());
        return absInfo;
    }

}
