package jp.smartcompany.admin.groupmanager.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastLanguageDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * グループ定義(グループリスト部品)用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class GroupManagerGroupListDTO implements Serializable {

    private static final long serialVersionUID = -4253195163214911345L;

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

    /**
     * 最終更新者
     */
    private String mgCmodifieruserid;
    /**
     * 最終更新日
     */
    private Date mgDmodifieddate;


    /** 画面遷移用のURL(パラメータ付加) */
    private String gsAddParmURL;

    /** 法人名称(MAC_CCOMPANYNAME：名称取得ファンクションより取得) */
    private String companyName;

    /** 法人名称一覧 */
    private List<MastCompanyDO> glCompanyNameList;

    private Long mgpId;

    /** グループ定義種別フラグ(0:組織・役職設定で定義/1:条件式で定義) */
    private String gsBaseFlg;


    /** 全社区分フラグ */
    private boolean allCompaniesFlg;

}
