package jp.smartcompany.admin.component.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastDatasectionpostmappingDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupsectionpostmappingDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 該当条件編集画面(組織・役職定義)用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostDTO {


    /** 各定義ごと情報一覧 */
    private List<SectionPostSelectDTO> menuList;

    /** 組織・役職条件選択情報 格納リスト 法人一覧 */
    private List <SectionPostListDTO> companyList;

    /** 選択された定義ID */
    private String permissionId;

    /** 選択中の親情報 組織コード */
    /** (検索対象範囲設定の場合は法人コード) */
    private String parentId;

    /** 選択中の子情報 組織コード(検索対象範囲設定のみ) */
    private String childId;

    /** 対象のレコード番号 */
    private String selectedSeq;

    /** 組織・役職画面 表示基準日 */
    private String applyDate;

    /** 選択されたコード */
    private String selectedId;

    /** 選択された名称 */
    private String selectedName;

    /** 選択された法人コード */
    private String selectedCompanyId;

    /** 選択された法人名称 */
    private String selectedCompanyName;

    /** 表示中のアプリケーションID */
    private String dispAppId;

    /** 表示フラグ (0:単一法人 1:複数法人) */
    private int dispFlg;

    /** 組織・役職設定情報 削除用一覧(グループ定義) */
    private List<MastGroupsectionpostmappingDO> deleteSectionPost;
    // #自定义加入，原先smartcompany并没有此属性
    /** 職員選択設定情報 削除用一覧(グループ定義) */
    private List<MastGroupsectionpostmappingDO> deleteEmployList;

    /** 組織・役職設定情報 削除用一覧(検索対象範囲設定) */
    private List<MastDatasectionpostmappingDO> deleteSectionPostCompany;
}
