package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 該当条件編集画面(組織・役職定義)用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostRowDTO implements Serializable {

    private static final long serialVersionUID = -1064353696139199181L;
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
    private String groupid;
    /** 開始日 */
    private String startDate;
    /** 終了日 */
    private String endDate;
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
    /** VERSIONNO */
    private Integer versionNo;
    /** 削除フラグ  */
    private String delflg;

}
