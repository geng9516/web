package jp.smartcompany.framework.sysboot.dto;

import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdatapermissionDO;
import lombok.ToString;

@ToString
public class AppSearchRangeInfoDTO extends HistGroupdatapermissionDO {
    /** カラム名(顧客コード) */
    private String customerid;

    /** カラム名(ユーザID) **/
    private String userid;

    /** カラム名(法人ID) **/
    private String companyid;

    /** カラム名(組織ID) **/
    private String sectionid;

    /** カラム名(データ開始日) */
    private String startdate;

    /** カラム名(データ終了日) */
    private String enddate;

    /**
     * @return customerid を取得します。
     */
    public String getCustomerid() {
        return this.customerid;
    }

    /**
     * @param customerid を設定します。 customerid。
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    /**
     * @return enddate を取得します。
     */
    public String getEnddate() {
        return this.enddate;
    }

    /**
     * @param enddate を設定します。 enddate。
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    /**
     * @return startdate を取得します。
     */
    public String getStartdate() {
        return this.startdate;
    }

    /**
     * @param startdate を設定します。 startdate。
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    /**
     * @return userid を取得します。
     */
    public String getUserid() {
        return this.userid;
    }

    /**
     * @param userid を設定します。 userid。
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * companyid 取得する。
     *
     * @return companyid
     */
    public String getCompanyid() {
        return this.companyid;
    }

    /**
     * companyid 設定する。
     *
     * @param companyid を設定
     */
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    /**
     * sectionid 取得する。
     *
     * @return sectionid
     */
    public String getSectionid() {
        return this.sectionid;
    }

    /**
     * sectionid 設定する。
     *
     * @param sectionid を設定
     */
    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

}
