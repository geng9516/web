package jp.smartcompany.job.modules.tmg.patternsetting.vo;

import jp.smartcompany.job.modules.tmg.patternsetting.util.PatternSettingConst;
import jp.smartcompany.job.modules.tmg.patternsetting.util.PatternSettingUtil;

import java.util.List;
import java.util.Map;

/**
 * @author 陳毅力
 * @description 勤務パターンパラメータ
 * @objectSource ps.c01.tmg.PatternSetting.PatternSettingParam
 * @date 2020/06/16
 **/
public class PatternSettingParam {
    /**
     * Actionコード取得
     */
    private String sActionCode = "";

    /**
     * 基準日
     */
    private String sBaseDate = "";

    /**
     * 顧客コード
     */
    private String sCustomerId = "";

    /**
     * 法人コード
     */
    private String sCompanyId = "";

    /**
     * 部局(組織)
     */
    private String sSectionId = "";

    private String sSectionName = "";

    /**
     * グループ
     */
    private String sGroupId = "";

    private String sGroupName = "";

    /**
     * 勤務パターンコード
     */
    private String sPatternId = "";

    /**
     * 更新者 / 更新日 / 更新プログラム
     */
    private String sModifierUserId = "";

    private String sModifiedDate = "";

    private String sModifierProgramId = "";

    private String sSelectedView = "";

    /**
     * ▼【阪大対応】2009/11/27 isolootsuki 追加ここから▼ ======
     * CSV取り込み処理の追加のため メッセージボックス表示フラグ追加
     * メッセージボックス表示フラグ : 初期値 false
     */
    private boolean bMsgFlg = false;

    /**
     * メッセージリスト
     */
    private List lMsgList;

    /**
     * CSVデータリスト
     */
    private List lpatternSettingList;

    /**
     * 終業・終業時刻
     */
    private Map mWorkingTime;

    /**
     * 勤務パターン名称
     */
    private String sPatternName;

    /**
     * デフォルトフラグ
     */
    private String sDefaultFlg;

    /**
     * 日付切替時刻
     */
    private String sDateChangeTime;

    /**
     * 翌日勤務パターン
     */
    private String sNextPatternId;

    /**
     * CSVデータ登録成功フラグ配列
     */
    private boolean[] insertFlg;

    /**
     * 勤務パターンコード(退避用)
     */
    private String sSubPatternId = "";

    /**
     * 休憩の登録数のMAX値
     */
    private int nRestMaxSize;

    /**
     * 時刻の規定範囲
     */
    private Map mTCOTime;

    /* ====== ▲【阪大対応】2009/11/27 isolootsuki 追加ここまで▲ ====== */

    /**
     * @return sSelectedView
     */
    public String getSelectedView() {

        return sSelectedView;
    }

    /**
     * @param selectedView
     */
    public void setSelectedView(String selectedView) {

        sSelectedView = selectedView;
    }

    /**
     * Actionコードを取得します
     *
     * @return string Actionコード
     */
    public String getActionCode() {

        return sActionCode;
    }

    /**
     * Actionコードをセットします
     *
     * @param sActionCode Actionコード
     */
    public void setActionCode(String sActionCode) {

        if (PatternSettingUtil.isEmpty(sActionCode)) {
            this.sActionCode = PatternSettingConst.ACTION_DISP_DISP;
        } else {
            this.sActionCode = sActionCode;
        }
    }

    /**
     * 基準日を取得します
     *
     * @return String 基準日
     */
    public String getBaseDate() {

        return sBaseDate;
    }

    /**
     * 基準日をセットします
     *
     * @param sBaseDate 基準日
     */
    public void setBaseDate(String sBaseDate) {

        this.sBaseDate = sBaseDate;
    }

    /**
     * 顧客コードを取得します
     *
     * @return String 顧客コード
     */
    public String getCustomerId() {

        return sCustomerId;
    }

    /**
     * 顧客コードをセットします
     *
     * @param sCustomerId 顧客コード
     */
    public void setCustomerId(String sCustomerId) {

        this.sCustomerId = sCustomerId;
    }

    /**
     * 法人コードを取得します
     *
     * @return string 法人コード
     */
    public String getCompanyId() {

        return sCompanyId;
    }

    /**
     * 法人コードをセットします
     *
     * @param sCompanyId 法人コード
     */
    public void setCompanyId(String sCompanyId) {

        this.sCompanyId = sCompanyId;
    }

    /**
     * 部局(組織)コードを取得します
     *
     * @return String 部局(組織)コード
     */
    public String getSectionId() {

        return sSectionId;
    }

    /**
     * 部局(組織)コードをセットします
     *
     * @param sSectionId 部局(組織)コード
     */
    public void setSectionId(String sSectionId) {

        this.sSectionId = sSectionId;
    }

    /**
     * 部局(組織)名称を取得します
     *
     * @return String 部局(組織)名称
     */
    public String getSectionName() {

        return sSectionName;
    }

    /**
     * 部局(組織)名称をセットします
     *
     * @param sectionName 部局(組織)名称
     */
    public void setSectionName(String sectionName) {

        sSectionName = sectionName;
    }

    /**
     * グループコードを取得します
     *
     * @return string グループコード
     */
    public String getGroupId() {

        return sGroupId;
    }

    /**
     * グループコードをセットします
     *
     * @param sGroupId グループコード
     */
    public void setGroupId(String sGroupId) {

        this.sGroupId = sGroupId;
    }

    /**
     * グループ名称を取得します
     *
     * @return String グループ名称
     */
    public String getGroupName() {

        return sGroupName;
    }

    /**
     * グループ名称をセットします
     *
     * @param groupName グループ名称
     */
    public void setGroupName(String groupName) {

        sGroupName = groupName;
    }

    /**
     * 勤務パターンコードを取得します
     *
     * @return String 勤務パターンコード
     */
    public String getPatternId() {

        return sPatternId;
    }

    /**
     * 勤務パターンコードを取得します
     *
     * @return String 勤務パターンコード
     */
    public String getPatternGroupId() {

        return sPatternId.split(PatternSettingConst.TXT_MASTGENERIC_SPLIT)[0];
    }

    /**
     * 勤務パターンコードを取得します
     *
     * @return String 勤務パターンコード
     */
    public String getPatternDetailId() {

        return sPatternId.split(PatternSettingConst.TXT_MASTGENERIC_SPLIT)[1];
    }

    /**
     * 勤務パターンコードをセットします
     *
     * @param sPatternId 勤務パターンコード
     */
    public void setPatternId(String sPatternId) {

        this.sPatternId = sPatternId;
    }

    /**
     * @return sModifierUserId
     */
    public String getModifierUserId() {

        return sModifierUserId;
    }

    /**
     * @param sModifierUserId
     */
    public void setModifierUserId(String sModifierUserId) {

        this.sModifierUserId = sModifierUserId;
    }

    /**
     * @return sModifiedDate
     */
    public String getModifiedDate() {

        return sModifiedDate;
    }

    /**
     * @param sModifiedDate
     */
    public void setModifiedDate(String sModifiedDate) {

        this.sModifiedDate = sModifiedDate;
    }

    /**
     * @return sModifierProgramId
     */
    public String getModifierProgramId() {

        return sModifierProgramId;
    }

    /**
     * @param sModifierProgramId
     */
    public void setModifierProgramId(String sModifierProgramId) {

        this.sModifierProgramId = sModifierProgramId;
    }

    /* ====== ▼【阪大対応】2009/11/27 isolootsuki 追加ここから▼ ======
     CSV取り込み処理の追加のため メッセージボックス表示フラグ追加  */

    /**
     * @return bMsgFl
     */
    public boolean isMsgFlg() {

        return bMsgFlg;
    }

    /**
     * @param bMsgFlg セットする sMsgFlg
     */
    public void setMsgFlg(boolean bMsgFlg) {

        this.bMsgFlg = bMsgFlg;
    }

    /**
     * @return sPatternName
     */
    public String getPatternName() {

        return sPatternName;
    }

    /**
     * @param sPatternName セットする sPatternName
     */
    public void setPatternName(String sPatternName) {

        this.sPatternName = sPatternName;
    }

    /**
     * @return sDefaultFlg
     */
    public String getDefaultFlg() {

        return sDefaultFlg;
    }

    /**
     * @param sDefaultFlg セットする sDefaultFlg
     */
    public void setDefaultFlg(String sDefaultFlg) {

        this.sDefaultFlg = sDefaultFlg;
    }

    /**
     * @return insertFlg
     */
    public boolean[] getInsertFlg() {

        return insertFlg;
    }

    /**
     * @param insertFlg セットする insertFlg
     */
    public void setInsertFlg(boolean[] insertFlg) {

        this.insertFlg = insertFlg;
    }

    /**
     * @return sDateChangeTime
     */
    public String getDateChangeTime() {

        return sDateChangeTime;
    }

    /**
     * @param sDateChangeTime セットする sDateChangeTime
     */
    public void setDateChangeTime(String sDateChangeTime) {

        this.sDateChangeTime = sDateChangeTime;
    }

    /**
     * @return sSubPatternId
     */
    public String getSubPatternId() {

        return sSubPatternId;
    }

    /**
     * @param sSubPatternId セットする sSubPatternId
     */
    public void setSubPatternId(String sSubPatternId) {

        this.sSubPatternId = sSubPatternId;
    }

    /**
     * @return nRestMaxSize
     */
    public int getRestMaxSize() {

        return nRestMaxSize;
    }

    /**
     * @param nRestMaxSize セットする nRestMaxSize
     */
    public void setRestMaxSize(int nRestMaxSize) {

        this.nRestMaxSize = nRestMaxSize;
    }

    /**
     * @param lMsgList
     */
    public void setMsgList(List lMsgList) {

        this.lMsgList = lMsgList;
    }

    /**
     * @return lMsgList
     */
    public List getMsgList() {

        return lMsgList;
    }

    /**
     * @return lpatternSettingList
     */
    public List getPatternSettingList() {

        return lpatternSettingList;
    }

    /**
     * @param lpatternSettingList セットする lpatternSettingList
     */
    public void setPatternSettingList(List lpatternSettingList) {

        this.lpatternSettingList = lpatternSettingList;
    }

    /**
     * @return mWorkingTime
     */
    public Map getWorkingTime() {

        return mWorkingTime;
    }

    /**
     * @param mWorkingTime セットする mWorkingTime
     */
    public void setWorkingTime(Map mWorkingTime) {

        this.mWorkingTime = mWorkingTime;
    }

    /**
     * @return mTCOTime
     */
    public Map getTCOTime() {

        return mTCOTime;
    }

    /**
     * @param mTCOTime セットする mTCOTime
     */
    public void setTCOTime(Map mTCOTime) {

        this.mTCOTime = mTCOTime;
    }

    /**
     * @return sNextPatternId
     */
    public String getNextPatternId() {

        return sNextPatternId;
    }

    /**
     * @param sNextPatternId セットする sNextPatternId
     */
    public void setNextPatternId(String sNextPatternId) {

        this.sNextPatternId = sNextPatternId;
    }

    /* ====== ▲【阪大対応】2009/11/27 isolootsuki 追加ここまで▲ ======  */

}
