package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 該当条件編集画面(組織・役職定義(組織情報配下のデータ))用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostRowListDTO {

    /** 組織・役職条件選択情報 格納リスト 役職一覧(指定法人＆指定組織) */
    private List<SectionPostRowDTO> postList;
    /** 選択済みの役職一覧(指定法人＆指定組織)の件数 */
    private int selectedPostCnt = 0;
    /** 組織・役職条件選択情報 格納リスト 社員一覧(指定法人＆指定組織) */
    private List<SectionPostRowDTO> employList;
    /** 選択済みの社員一覧(指定法人＆指定組織)の件数 */
    private int selectedEmpoyeesCompCnt = 0;
    /** 顧客名称 */
    private String customerName;
    /** 法人名称 */
    private String companyName;
    /** 組織名称 */
    private String sectionName;
    /** 役職名称 */
    private String postName;
    /** 漢字氏名 */
    private String kanJiName;
    /** 顧客コード */
    private String customerId;
    /** システムコード */
    private String systemId;
    /** グループコード */
    private String groupId;
    /** 開始日 */
    private String startdate;
    /** 終了日 */
    private String enddate;
    /** 定義ID */
    private String permissionId;
    /** 定義区分 */
    private String typeId;
    /** 法人コード */
    private String companyId;
    /** 組織コード */
    private String sectionId;
    /** 役職コード */
    private String postId;
    /** 社員番号 */
    private String employeeId;
    /** ID */
    private Long id;
//    /** VERSIONNO */
//    private Integer versionNo;
//    /** 削除フラグ  */
    private Boolean delete;

}
