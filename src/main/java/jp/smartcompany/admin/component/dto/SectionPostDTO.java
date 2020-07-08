package jp.smartcompany.admin.component.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastDatasectionpostmappingDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupsectionpostmappingDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 該当条件編集画面(組織・役職定義)用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostDTO implements Serializable {

    private static final long serialVersionUID = 5779789995663607842L;

    /** 各定義ごと情報一覧 */
    private List<SectionPostSelectDTO> glMenuList;

    /** 組織・役職条件選択情報 格納リスト 法人一覧 */
    private List <SectionPostCompanyRowListDTO> glCompanyList;

    /** 選択された定義ID */
    private String gsPermissionId;

    /** 選択された定義区分 */
    private String gsPeramId;

    /** 選択中の親情報 組織コード */
    /** (検索対象範囲設定の場合は法人コード) */
    private String gsParentId;

    /** 選択中の子情報 組織コード(検索対象範囲設定のみ) */
    private String gsChildId;

    /** 対象のレコード番号 */
    private String gsSelectedSeq;

    /** 組織・役職画面 表示基準日 */
    private String gsApplyDate;

    /** 選択されたコード */
    private String gsSelectedId;

    /** 選択された名称 */
    private String gsSelectedName;

    /** 選択された法人コード */
    private String gsSelectedCompanyId;

    /** 選択された法人名称 */
    private String gsSelectedCompanyName;

    /** 組織・役職設定情報 削除用一覧(グループ定義) */
    private List <MastGroupsectionpostmappingDO> glDeleteSectionPost;

    /** 組織・役職設定情報 削除用一覧(検索対象範囲設定) */
    private List <MastDatasectionpostmappingDO> glDeleteSectionPostCompany;

    /** 表示中のアプリケーションID */
    private String gsDispAppId;

    /** 表示フラグ (0:単一法人 1:複数法人) */
    private int gnDispFlg;

    /** 組織・役職設定情報一覧(JSON形式) */
    private String gsJSONData;

}
