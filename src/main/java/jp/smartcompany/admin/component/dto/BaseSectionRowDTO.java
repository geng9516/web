package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 該当条件編集画面(基点組織定義(行単位))用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class BaseSectionRowDTO implements Serializable {

    private static final long serialVersionUID = -3115843172677875563L;
    /**
     * ｉｄカラム
     */
    private Long mgbsId;

    /**
     * 顧客コード
     */
    private String mgbsCcustomerid;

    /**
     * システムコード
     */
    private String mgbsCsystemid;

    /**
     * グループid
     */
    private String mgbsCgroupid;

    /**
     * 開始日
     */
    private Date mgbsDstartdate;

    /**
     * 終了日
     */
    private Date mgbsDenddate;

    /**
     * 法人コード
     */
    private String mgbsCcompanyid;

    /**
     * 組織コード
     */
    private String mgbsCsectionid;

    /**
     * 組織階層コード
     */
    private String mgbsClayeredsectionid;

    /**
     * 最終更新者
     */
    private String mgbsCmodifieruserid;

    /**
     * 最終更新日
     */
    private Date mgbsDmodifieddate;

    /**
     * バージョンno
     */
    private Long versionno;

    /**
     * 以下・のみフラグ
     */
    private String mgbsCbeloworsingle;


    /** 法人名称 */
    private String companyName;
    /** 組織名称 */
    private String sectionName;
    /** 組織一覧(指定法人) */
    private List<BaseSectionRowListDTO> sectionList;
    /** 選択済みの組織一覧(指定法人)の件数 */
    private int gnSelectedSectionCnt = 0;
    /** 削除フラグ  */
    private String delflg;

}
