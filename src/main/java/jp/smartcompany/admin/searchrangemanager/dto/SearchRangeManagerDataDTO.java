package jp.smartcompany.admin.searchrangemanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SearchRangeManagerDataDTO  {

    private Long hgpId;

    /**
     * 顧客コード
     */
    private String hgpCcustomerid;

    /**
     * オブジェクトid
     */
    private String hgpCobjectid;

    /**
     * サイトid
     */
    private String hgpCsiteid;

    /**
     * アプリケーションid
     */
    private String hgpCappid;

    /**
     * システムコード
     */
    private String hgpCsystemid;

    /**
     * グループid
     */
    private String hgpCgroupid;

    /**
     * データ開始日
     */
    private Date hgpDstartdate;

    /**
     * データ終了日
     */
    private Date hgpDenddate;

    /**
     * 必要条件定義id
     */
    private String hgpCpermnecessity;

    /**
     * 必須条件定義id
     */
    private String hgpCpermmust;

    /**
     * 必要条件基点組織使用フラグ
     */
    private String hgpCbasesectionFlagNeed;

    /**
     * 必須条件基点組織使用フラグ
     */
    private String hgpCbasesectionFlagMust;


    /**
     * 退職者検索対象範囲
     */
    private String hgpCpermRetired;



    private String mgCgroupdescription;
    private String mtrCobjname;
    private String hgpCpermnecessityname;
    private String hgpCpermmustname;
    private Integer CreateHistory;
    private String fullPermisson;
    private String companyNick;

}
