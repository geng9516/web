package jp.smartcompany.framework.component.entity;

import jp.smartcompany.framework.component.behavior.EmployeeInfoSearchBehavior;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class EmployeeInfoSearchEntity implements Serializable {

    private static final long serialVersionUID = 7141438846326320517L;

    // プロパティ変数 //
    /** 社員情報検索結果件数 */
    private String rownum; // rownum

    /** 異動歴"HIST_DESIGNATION"社員番号 */
    private String hdCemployeeidCk; // HD_CEMPLOYEEID_CK

    /** 異動歴"HIST_DESIGNATION"ユーザーID */
    private String hdCuserId; // HD_CUSERID

    /** 異動歴"HIST_DESIGNATION"所属コード */
    private String hdCsectionidFk; // HD_CSECTIONID_FK

    /** 異動歴"HIST_DESIGNATION"法人コード */
    private String hdCcompanyidCk; // HD_CCOMPANYID_CK

    /** 異動歴"HIST_DESIGNATION"役職コード */
    private String hdCpostidFk; // HD_CPOSTID_FK

    /** FUNCTION 社員氏名 */
    private String empName; // EMP_NAME

    /** 社員氏名COLUMNアノテーション */
    public static final String empName_COLUMN = "ME_CKANJINAME";

    /** FUNCTION 組織名称 */
    private String sectionName; // SECTION_NAME

    /** 組織名称COLUMNアノテーション */
    public static final String sectionName_COLUMN = "HD_CSECTIONNAME";

    /** 組織略称 */
    private String sectionNick = "";

    /** FUNCTION 役職名称 */
    private String postName; // POST_NAME

    /** 役職名称COLUMNアノテーション */
    public static final String postName_COLUMN = "HD_CPOSTNAME";

    /** 役職略称 */
    private String postNick = "";

    /** FUNCTION 法人名称 */
    private String compName; // COMP_NAME

    /** 法人名称COLUMNアノテーション */
    public static final String compName_COLUMN = "MAC_CCOMPANYNAME";

    /** 法人略称 */
    private String companyNick = "";

    /** 社員基本情報 MAST_EMPLOYEES"カナ名称" */
    private String meCkananame; // ME_CKANANAME

    /** Behaviorクラス */
    private EmployeeInfoSearchBehavior behavior;

    /** リレーション */
    private String relation;

    /** 基本情報"HIST_DESIGNATION"社員番号 */
    private String meCemployeeidCk; // ME_CEMPLOYEEID_CK

    /** 基本情報"HIST_DESIGNATION"ユーザID */
    private String meCuserid; // ME_CUSERID

    /** 在職・退職(2009/10/26 K.Monden 追加) */
    private String meCifStillEmployedId;

    /** 在職･退職COLUMNアノテーション(2009/10/26 K.Monden 追加) */
    public static final String meCifStillEmployedId_COLUMN = "ME_CIFSTILLEMPLOYEDID";

    /** 在職・退職 名称 */
    private String meCifStillEmployedName;

    /** 本務兼務区分 */
    private String hdCifkeyoradditionalrole;

    /** 異動歴ID */
    private Long hdId;

}
