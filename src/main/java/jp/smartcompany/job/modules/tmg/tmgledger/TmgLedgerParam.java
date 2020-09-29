package jp.smartcompany.job.modules.tmg.tmgledger;

import cn.hutool.core.util.StrUtil;

/**
 * 帳票出力アプリケーションの定数を管理するクラス
 *
 * @author nisshin-sci
 *
 */
public class TmgLedgerParam {

    private String sLanguage    = "";
    private String sAction      = "";
    private String sCompanyID   = "";
    private String sCustomerID  = "";
    private String sSection     = "";
    private String sSectionName = "";
    private String sEmpID       = "";
    private String sOrgID       = "";
    private String sYYYY        = "";
    private String sYYYYMM      = "";
    private String sTermFrom    = "";
    private String sTermTo      = "";
    private String sAtdBookTermFrom = "";
    private String sAtdBookTermTo   = "";
    private String sIncludeOt100Flg = "0";
    private String sLedgerSheetId = "";

    public String getLanguage() {
        return this.sLanguage;
    }

    public void setLanguage(String psLanguage) {
        this.sLanguage = psLanguage;
    }

    public String getAction() {
        // 初回起動時：権限設定状況表示
        if(StrUtil.isBlank(this.sAction)){
            return TmgLedgerConst.ACT_LEDGER_SELECT_DISP;
        } else {
            return this.sAction;
        }
    }

    public void setAction(String psAction) {
        this.sAction = psAction;
    }

    public String getCompanyID() {
        return sCompanyID;
    }

    public void setCompanyID(String sCompanyID) {
        this.sCompanyID = sCompanyID;
    }

    public String getCustomerID() {
        return sCustomerID;
    }

    public void setCustomerID(String sCustomerID) {
        this.sCustomerID = sCustomerID;
    }

    public String getSection() {
        return this.sSection;
    }

    public void setSection(String psSection) {
        this.sSection = psSection;
    }

    public String getSectionName() {
        return this.sSectionName;
    }

    public void setSectionName(String psSectionName) {
        this.sSectionName = psSectionName;
    }

    public String getEmpID() {
        return this.sEmpID;
    }

    public void setEmpID(String psEmpID) {
        this.sEmpID = psEmpID;
    }

    public String getOrgID() {
        return sOrgID;
    }

    public void setOrgID(String sOrgID) {
        this.sOrgID = sOrgID;
    }

    public String getYYYY() {
        return sYYYY;
    }

    public void setYYYY(String sYYYY) {
        this.sYYYY = sYYYY;
    }

    public String getYYYYMM() {
        return sYYYYMM;
    }

    public void setYYYYMM(String sYYYYMM) {
        this.sYYYYMM = sYYYYMM;
    }

    public String getTermFrom() {
        return sTermFrom;
    }

    public void setTermFrom(String sTermFrom) {
        this.sTermFrom = sTermFrom;
    }

    public String getTermTo() {
        return sTermTo;
    }

    public void setTermTo(String sTermTo) {
        this.sTermTo = sTermTo;
    }

    public String getAtdBookTermFrom() {
        return this.sAtdBookTermFrom;
    }

    public void setAtdBookTermFrom(String sAtdBookTermFrom) {
        this.sAtdBookTermFrom = sAtdBookTermFrom;
    }

    public String getAtdBookTermTo() {
        return this.sAtdBookTermTo;
    }

    public void setAtdBookTermTo(String sAtdBookTermTo) {
        this.sAtdBookTermTo = sAtdBookTermTo;
    }

    public String getsIncludeOt100Flg() {
        return sIncludeOt100Flg;
    }

    public void setsIncludeOt100Flg(String sIncludeOt100Flg) {
        this.sIncludeOt100Flg = sIncludeOt100Flg;
    }

    public String getsLedgerSheetId() {
        return sLedgerSheetId;
    }

    public void setsLedgerSheetId(String sLedgerSheetId) {
        this.sLedgerSheetId = sLedgerSheetId;
    }

}
