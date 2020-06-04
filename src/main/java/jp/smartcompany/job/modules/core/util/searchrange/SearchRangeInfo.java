package jp.smartcompany.job.modules.core.util.searchrange;

public class SearchRangeInfo {

    /**
     * Default Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    // 検索対象範囲条件定義マスタ(組織、役職)
    // テーブル(物理名)：MAST_DATASECTIONPOSTMAPPING
    /** 定義ID */
    private String mdspCpermissionid;

    /** 定義区分 */
    private String mdspCtypeid;

    /** 法人コード */
    private String mdspCcompanyid;

    /** 組織コード */
    private String mdspCsectionid;

    /** 役職コード */
    private String mdspCpostid;

    /** 社員番号 */
    private String mdspCemployeeid;

    // 検索対象範囲条件定義マスタ(条件式)
    // テーブル(物理名)：MAST_DATAPERMISSIONDEFS
    /** シーケンス番号 */
    private Double mdpdNseq;

    /** 定義ID */
    private String mdpdCpermissionid;

    /** 論理演算子 */
    private String mdpdCandor;

    /** 左カッコ */
    private String mdpdCopenedparenthsis;

    /** テーブルID */
    private String mdpdCtableid;

    /** カラムID */
    private String mdpdCcolumnid;

    /** カラム名 */
    private String mdpdCcolumnname;

    /** データ型 */
    private String mdpdCtypeofcolumn;

    /** 比較演算子 */
    private String mdpdCoperator;

    /** 法人コード */
    private String mdpdCcompanyid;

    /** 比較値 */
    private String mdpdCvalue;

    /** 自分のフラグ */
    private String mdpdCmyflag;

    /** 右カッコ */
    private String mdpdCclosedparenthsis;

    // データディクショナリマスタ
    // テーブル(物理名)：MAST_DATADICTIONARY
    /** マスタテーブル区分 */
    private String mdCmastertblname;

    /**
     * @return mdpdNseq を取得します。
     */
    public Double getMdpdNseq() {
        return this.mdpdNseq;
    }

    /**
     * @param mdpdNseq を設定します。 mdpdNseq。
     */
    public void setMdpdNseq(Double mdpdNseq) {
        this.mdpdNseq = mdpdNseq;
    }

    /**
     * @return mdpdCpermissionid を取得します。
     */
    public String getMdpdCpermissionid() {
        return this.mdpdCpermissionid;
    }

    /**
     * @param mdpdCpermissionid を設定します。 mdpdCpermissionid。
     */
    public void setMdpdCpermissionid(String mdpdCpermissionid) {
        this.mdpdCpermissionid = mdpdCpermissionid;
    }

    /**
     * @return mdspCpermissionid を取得します。
     */
    public String getMdspCpermissionid() {
        return this.mdspCpermissionid;
    }

    /**
     * @param mdspCpermissionid を設定します。 mdspCpermissionid。
     */
    public void setMdspCpermissionid(String mdspCpermissionid) {
        this.mdspCpermissionid = mdspCpermissionid;
    }

    /**
     * @return this.mdCmastertblname を取得します。
     */
    public String getMdCmastertblname() {
        return this.mdCmastertblname;
    }

    /**
     * @param mdCmastertblname を設定します。 mdCmastertblname。
     */
    public void setMdCmastertblname(String mdCmastertblname) {
        this.mdCmastertblname = mdCmastertblname;
    }

    /**
     * @return this.mdpdCandor を取得します。
     */
    public String getMdpdCandor() {
        return this.mdpdCandor;
    }

    /**
     * @param mdpdCandor を設定します。 mdpdCandor。
     */
    public void setMdpdCandor(String mdpdCandor) {
        this.mdpdCandor = mdpdCandor;
    }

    /**
     * @return this.mdpdCclosedparenthsis を取得します。
     */
    public String getMdpdCclosedparenthsis() {
        return this.mdpdCclosedparenthsis;
    }

    /**
     * @param mdpdCclosedparenthsis を設定します。 mdpdCclosedparenthsis。
     */
    public void setMdpdCclosedparenthsis(String mdpdCclosedparenthsis) {
        this.mdpdCclosedparenthsis = mdpdCclosedparenthsis;
    }

    /**
     * @return this.mdpdCcolumnid を取得します。
     */
    public String getMdpdCcolumnid() {
        return this.mdpdCcolumnid;
    }

    /**
     * @param mdpdCcolumnid を設定します。 mdpdCcolumnid。
     */
    public void setMdpdCcolumnid(String mdpdCcolumnid) {
        this.mdpdCcolumnid = mdpdCcolumnid;
    }

    /**
     * @return this.mdpdCcolumnname を取得します。
     */
    public String getMdpdCcolumnname() {
        return this.mdpdCcolumnname;
    }

    /**
     * @param mdpdCcolumnname を設定します。 mdpdCcolumnname。
     */
    public void setMdpdCcolumnname(String mdpdCcolumnname) {
        this.mdpdCcolumnname = mdpdCcolumnname;
    }

    /**
     * @return this.mdpdCcompanyid を取得します。
     */
    public String getMdpdCcompanyid() {
        return this.mdpdCcompanyid;
    }

    /**
     * @param mdpdCcompanyid を設定します。 mdpdCcompanyid。
     */
    public void setMdpdCcompanyid(String mdpdCcompanyid) {
        this.mdpdCcompanyid = mdpdCcompanyid;
    }

    /**
     * @return this.mdpdCmyflag を取得します。
     */
    public String getMdpdCmyflag() {
        return this.mdpdCmyflag;
    }

    /**
     * @param mdpdCmyflag を設定します。 mdpdCmyflag。
     */
    public void setMdpdCmyflag(String mdpdCmyflag) {
        this.mdpdCmyflag = mdpdCmyflag;
    }

    /**
     * @return this.mdpdCopenedparenthsis を取得します。
     */
    public String getMdpdCopenedparenthsis() {
        return this.mdpdCopenedparenthsis;
    }

    /**
     * @param mdpdCopenedparenthsis を設定します。 mdpdCopenedparenthsis。
     */
    public void setMdpdCopenedparenthsis(String mdpdCopenedparenthsis) {
        this.mdpdCopenedparenthsis = mdpdCopenedparenthsis;
    }

    /**
     * @return this.mdpdCoperator を取得します。
     */
    public String getMdpdCoperator() {
        return this.mdpdCoperator;
    }

    /**
     * @param mdpdCoperator を設定します。 mdpdCoperator。
     */
    public void setMdpdCoperator(String mdpdCoperator) {
        this.mdpdCoperator = mdpdCoperator;
    }

    /**
     * @return this.mdpdCtableid を取得します。
     */
    public String getMdpdCtableid() {
        return this.mdpdCtableid;
    }

    /**
     * @param mdpdCtableid を設定します。 mdpdCtableid。
     */
    public void setMdpdCtableid(String mdpdCtableid) {
        this.mdpdCtableid = mdpdCtableid;
    }

    /**
     * @return this.mdpdCtypeofcolumn を取得します。
     */
    public String getMdpdCtypeofcolumn() {
        return this.mdpdCtypeofcolumn;
    }

    /**
     * @param mdpdCtypeofcolumn を設定します。 mdpdCtypeofcolumn。
     */
    public void setMdpdCtypeofcolumn(String mdpdCtypeofcolumn) {
        this.mdpdCtypeofcolumn = mdpdCtypeofcolumn;
    }

    /**
     * @return this.mdpdCvalue を取得します。
     */
    public String getMdpdCvalue() {
        return this.mdpdCvalue;
    }

    /**
     * @param mdpdCvalue を設定します。 mdpdCvalue。
     */
    public void setMdpdCvalue(String mdpdCvalue) {
        this.mdpdCvalue = mdpdCvalue;
    }

    /**
     * @return this.mdspCcompanyid を取得します。
     */
    public String getMdspCcompanyid() {
        return this.mdspCcompanyid;
    }

    /**
     * @param mdspCcompanyid を設定します。 mdspCcompanyid。
     */
    public void setMdspCcompanyid(String mdspCcompanyid) {
        this.mdspCcompanyid = mdspCcompanyid;
    }

    /**
     * @return this.mdspCemployeeid を取得します。
     */
    public String getMdspCemployeeid() {
        return this.mdspCemployeeid;
    }

    /**
     * @param mdspCemployeeid を設定します。 mdspCemployeeid。
     */
    public void setMdspCemployeeid(String mdspCemployeeid) {
        this.mdspCemployeeid = mdspCemployeeid;
    }

    /**
     * @return this.mdspCpostid を取得します。
     */
    public String getMdspCpostid() {
        return this.mdspCpostid;
    }

    /**
     * @param mdspCpostid を設定します。 mdspCpostid。
     */
    public void setMdspCpostid(String mdspCpostid) {
        this.mdspCpostid = mdspCpostid;
    }

    /**
     * @return this.mdspCsectionid を取得します。
     */
    public String getMdspCsectionid() {
        return this.mdspCsectionid;
    }

    /**
     * @param mdspCsectionid を設定します。 mdspCsectionid。
     */
    public void setMdspCsectionid(String mdspCsectionid) {
        this.mdspCsectionid = mdspCsectionid;
    }

    /**
     * @return this.mdspCtypeid を取得します。
     */
    public String getMdspCtypeid() {
        return this.mdspCtypeid;
    }

    /**
     * @param mdspCtypeid を設定します。 mdspCtypeid。
     */
    public void setMdspCtypeid(String mdspCtypeid) {
        this.mdspCtypeid = mdspCtypeid;
    }

}
