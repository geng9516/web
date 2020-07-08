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

    /** 顧客名称 */
    private String gsCustomerName;
    /** 法人名称 */
    private String gsCompanyName;
    /** 組織名称 */
    private String gsSectionName;
    /** 役職名称 */
    private String gsPostName;
    /** 漢字氏名 */
    private String gsKanjiname;
    /** 顧客コード */
    private String gsCustomerid;
    /** システムコード */
    private String gsSystemid;
    /** グループコード */
    private String gsGroupid;
    /** 開始日 */
    private String gsStartdate;
    /** 終了日 */
    private String gsEnddate;
    /** 定義ID */
    private String gsPermissionid;
    /** 定義区分 */
    private String gsTypeid;
    /** 法人コード */
    private String gsCompanyid;
    /** 組織コード */
    private String gsSectionid;
    /** 役職コード */
    private String gsPostid;
    /** 社員番号 */
    private String gsEmployeeid;
    /** ID */
    private Integer id;
    /** VERSIONNO */
    private Integer versionNo;
    /** 削除フラグ  */
    private String delflg;

}
