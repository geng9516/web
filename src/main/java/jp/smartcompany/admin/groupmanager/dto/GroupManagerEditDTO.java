package jp.smartcompany.admin.groupmanager.dto;

import jp.smartcompany.admin.component.dto.SectionPostListDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupManagerEditDTO {

    private Long mgId;
    private String groupId;
    private Date startDate;
    private String baseFlag;
    private Long weightAge;
    private String groupName;
    private Long peopleCount;
    private String remark;
    // 組織・役職条件選択情報 格納リスト 法人一覧
    private SectionPostListDTO sectionPostList;

}
//public class GroupManagerEditDTO extends MastGroupDO {
//
//
//    /** グループ定義条件マスタ ID */
//    private Long mgpId;
//
//
//    private String msCsystemidPk;
//
//    /** グループ定義種別フラグ(0:組織・役職設定で定義/1:条件式で定義) */
//    private String baseFlg;
//
//    // 組織・役職条件選択情報 格納リスト 法人一覧
//    private List<SectionPostCompanyRowListDTO> sectionPostCompanyList;
//    // 条件式定義コンポーネント用Dtoクラス
//    private List<QueryConditionRowDTO> queryConditionList;
//    // 該当条件編集 - 定義情報取得(法人＆社員リスト)
//    private List<SectionPostRowDTO> selectingEmployeesList;
//    // 該当条件編集画面(基点組織定義(行単位))用Dtoクラス
//    private List<BaseSectionRowDTO> baseSectionList;
//
//    /** 組織・役職設定情報 削除用一覧(グループ定義) */
//    private List <MastGroupsectionpostmappingDO> deleteSectionPost;
//
//    /** 組織・役職設定情報 削除用一覧(検索対象範囲設定) */
//    private List <MastDatasectionpostmappingDO> deleteSectionPostCompany;
//
//    /** 条件式設定情報(単一法人用) 削除用一覧 */
//    private List <HistGroupdefinitionsDO> deleteDefinitions;
//
//    /** 基点組織設定情報 削除用一覧 */
//    private List <MastGroupbasesectionDO> deleteBaseSection;
//
//
//}
