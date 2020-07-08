package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 該当条件編集画面(組織・役職定義(組織情報配下のデータ))用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostRowListDTO implements Serializable {

    /** 組織・役職条件選択情報 格納リスト 役職一覧(指定法人＆指定組織) */
    private List<SectionPostRowDTO> glPostCompList;
    /** 選択済みの役職一覧(指定法人＆指定組織)の件数 */
    private int gnSelectedPostCompCnt = 0;
    /** 組織・役職条件選択情報 格納リスト 社員一覧(指定法人＆指定組織) */
    private List<SectionPostRowDTO> glEmpoyeesCompList;
    /** 選択済みの社員一覧(指定法人＆指定組織)の件数 */
    private int gnSelectedEmpoyeesCompCnt = 0;
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
