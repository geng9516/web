package jp.smartcompany.admin.groupappmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 起動権限設定 グループマスタDtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class GroupAppManagerGroupDTO  implements Serializable {

   private static final long serialVersionUID = -6387030978412564395L;

    /** 法人略称(ない場合は名称) */
   private String companyNick;
    /**
     * ｉｄカラム
     */
    private Long mgId;

    /**
     * 顧客コード
     */
    private String mgCcustomerid;

    /**
     * システムコード
     */
    private String mgCsystemidCkFk;

    /**
     * グループid
     */
    private String mgCgroupidPk;

    /**
     * 言語区分
     */
    private String mgClanguage;

    /**
     * データ開始日
     */
    private Date mgDstartdate;

    /**
     * データ終了日
     */
    private Date mgDenddate;

    /**
     * グループ名称
     */
    private String mgCgroupdescription;

    /**
     * グループ名称（日本語）
     */
    private String mgCgroupdescriptionja;

    /**
     * グループ名称（英語）
     */
    private String mgCgroupdescriptionen;

    /**
     * グループ名称（中国語）
     */
    private String mgCgroupdescriptionch;

    /**
     * グループ名称（予備01）
     */
    private String mgCgroupdescription01;

    /**
     * グループ名称（予備02）
     */
    private String mgCgroupdescription02;

    /**
     * 法人コード（未使用）
     */
    private String mgCcompanyid;

    /**
     * 適正数
     */
    private Long mgNpartinentnumber;

    /**
     * 優先順
     */
    private Long mgNweightage;

    /**
     * 備考欄
     */
    private String mgCtext;


}
