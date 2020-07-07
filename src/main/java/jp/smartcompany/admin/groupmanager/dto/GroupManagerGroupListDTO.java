package jp.smartcompany.admin.groupmanager.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastLanguageDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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

    /**
     * バージョンno
     */
    private Long versionno;

    /** 画面遷移用のURL(パラメータ付加) */
    private String gsAddParmURL;

    /** 法人名称(MAC_CCOMPANYNAME：名称取得ファンクションより取得) */
    private String gsCompanyName;

    /** 法人名称一覧 */
    private List<MastCompanyDO> glCompanyNameList;

    /** 順序表示フラグ */
    private int gnDispOrder;

    /** 削除フラグ */
    private boolean gbDelFlg;

    /** 順序入れ替えフラグ */
    private int gnChengeOrder;

    /** 順序入れ替え(上位) */
    private int gnChengeUpper;

    /** 順序入れ替え(下位) */
    private int gnChengeLower;

    /** 表示・非表示フラグ(true:非表示/false:表示) */
    private boolean gbDisabled;

    /** 初期表示開始日付 */
    private Timestamp goStartdate;

    /** 初期表示終了日付 */
    private Timestamp goEnddate;

    /** グループ定義種別フラグ(0:組織・役職設定で定義/1:条件式で定義) */
    private String gsBaseFlg;

    /** 現在登録されている言語区分情報 */
    private List<MastLanguageDO> glLanguageList;

    /** 変更フラグ */
    private Boolean chgFlag;

    /** 戻り画面フラグ */
    private String gsPrevFlag;

    /** グループ定義条件マスタ ID */
    private BigDecimal mgpId;

    /** グループ定義条件マスタ VersionNo */
    private Integer mgpversionNo;

    /** グループ判定クエリ妥当性チェック結果 */
    private int gnCheckResult = 0;

    /** リロード用初期表示処理読み飛ばしフラグ */
    private String psAction;

    /** 法人選択フラグ */
    private String companySelectedFlg;

    /** 自動スクロール用リロードチェックフラグ */
    private String reloadCheck;

    /** グループ名称の入力項目サイズ */
    private Integer nameSize = 0;

    /** 全社区分フラグ */
    private Boolean allCompaniesFlg = Boolean.valueOf(false);

    /** MG_DSTARTDATE を YYYY/MM/DD 形式で */
    private String mgDstartdateName;
    /** MG_DENDDATE を YYYY/MM/DD 形式で */
    private String mgDenddateName;

    /** 編集可能フラグ */
    private boolean gbEditFlag;

}
