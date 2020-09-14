package jp.smartcompany.framework.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryConditionRowDTO  {

    // プロパティ変数
    /** ID */
    private Long id;
    /** 顧客コード */
    private String customerid;
    /** システムコード */
    private String systemid;
    /** グループコード */
    private String groupid;
    /** 定義コード */
    private String permissionid;
    /** 開始日 */
    private String startdate;
    /** 終了日 */
    private String enddate;
    /** シーケンス番号 */
    private Integer seq;
    /** 論理演算子 */
    private String andor;
    /** 開始カッコ */
    private String openedparenthsis;
    /** テーブルID */
    private String tableid;
    /** 選択済のマスタ区分 */
    private String mastertablename;
    /** カラムID */
    private String columnid;
    /** カラム名(テーブル.カラム名) */
    private String columnname;
    /** データ型 */
    private String typeofcolumn;
    /** 表示演算子 */
    private String displayoperator;
    /** 演算子 */
    private String operator;
    /** 法人コード */
    private String companyid;
    /** 比較値 */
    private String value;
    /** 表示文字列 */
    private String displayvalue;
    /** 閉じカッコ */
    private String closedparenthsis;
    /** クエリ(結合式)  */
    private String joinquery;
    /** 自分のフラグ */
//    private String myflag;
    /** VERSIONNO */
//    private Integer versionNo;
    /** 削除フラグ  */
    private Boolean delete;
    /** HSD_NSETTINGID */
    private Long settingid;

}
