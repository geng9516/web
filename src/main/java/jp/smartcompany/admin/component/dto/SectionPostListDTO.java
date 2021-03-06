package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 該当条件編集画面(組織・役職定義(法人報配下のデータ))用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostListDTO {

    private String companyId;
    private String companyName;
    /** 組織・役職条件選択情報 格納リスト 組織一覧(指定法人) */
    private List<SectionPostRowListDTO> sectionList;
    /** 組織・役職条件選択情報 格納リスト 役職一覧(指定法人) */
    private List<SectionPostRowDTO> postList;
    /** 組織・役職条件選択情報 格納リスト 職員一覧(指定法人) */
    private List<SectionPostRowDTO> employList;
    /** 組織・役職条件選択情報 格納リスト 所属長一覧(指定法人＆指定組織) */
    private List<SectionPostRowDTO> bossSectionList;

//    /** 選択済みの役職一覧(法人＆組織)の件数 */
//    private int gnSelectedSectionCnt = 0;
//    /** 選択済みの役職一覧(法人＆役職)の件数 */
//    private int gnSelectedPostCompCnt = 0;
//    /** 選択済みの役職一覧(法人＆所属長)の件数 */
//    private int gnSelectedBossCompSecCnt = 0;
//    /** 選択済みの役職一覧(法人＆職員)の件数 */
//    private int gnSelectedEmpCompCnt = 0;
//    /** 選択中の親情報 組織コード */
//    private String gsSecParentId;
//    /** 対象のレコード番号 */
//    private String gsSelectedSeq;
//    /** 顧客名称 */
//    private String gsCustomerName;
//    /** 法人名称 */
//    private String gsCompanyName;
//    /** 組織名称 */
//    private String gsSectionName;
//    /** 役職名称 */
//    private String gsPostName;
//    /** 漢字氏名 */
//    private String gsKanjiname;
//    /** 顧客コード */
//    private String gsCustomerid;
//    /** システムコード */
//    private String gsSystemid;
//    /** グループコード */
//    private String gsGroupid;
//    /** 開始日 */
//    private String gsStartdate;
//    /** 終了日 */
//    private String gsEnddate;
//    /** 定義ID */
//    private String gsPermissionid;
//    /** 定義区分 */
//    private String gsTypeid;
//    /** 法人コード */
//    private String gsCompanyid;
//    /** 組織コード */
//    private String gsSectionid;
//    /** 役職コード */
//    private String gsPostid;
//    /** 職員番号 */
//    private String gsEmployeeid;
//    /** ID */
    private Long id;
//    /** VERSIONNO */
//    private Integer versionNo;
    /** 削除フラグ  */
    private Boolean delete = false;
}
