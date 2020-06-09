package jp.smartcompany.framework.sysboot.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 検索範囲取得DTOクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SearchRangeInfoDTO implements Serializable {

    private static final long serialVersionUID = -4883950364892774697L;

    // 検索対象範囲条件定義マスタ(組織、役職)
    // テーブル(物理名)：MAST_DATASECTIONPOSTMAPPING
    /** 定義ID */
    private String mdspCpermissionid;

    /** 定義区分 */
    private String mdspCtypeid;

    /** 法人コード */
    private String mdspCcompanyid;

    /** 組織コード */
    private String mdspCsectionid;

    /** 役職コード */
    private String mdspCpostid;

    /** 社員番号 */
    private String mdspCemployeeid;

    // 検索対象範囲条件定義マスタ(条件式)
    // テーブル(物理名)：MAST_DATAPERMISSIONDEFS
    /** シーケンス番号 */
    private Double mdpdNseq;

    /** 定義ID */
    private String mdpdCpermissionid;

    /** 論理演算子 */
    private String mdpdCandor;

    /** 左カッコ */
    private String mdpdCopenedparenthsis;

    /** テーブルID */
    private String mdpdCtableid;

    /** カラムID */
    private String mdpdCcolumnid;

    /** カラム名 */
    private String mdpdCcolumnname;

    /** データ型 */
    private String mdpdCtypeofcolumn;

    /** 比較演算子 */
    private String mdpdCoperator;

    /** 法人コード */
    private String mdpdCcompanyid;

    /** 比較値 */
    private String mdpdCvalue;

    /** 自分のフラグ */
    private String mdpdCmyflag;

    /** 右カッコ */
    private String mdpdCclosedparenthsis;

    // データディクショナリマスタ
    // テーブル(物理名)：MAST_DATADICTIONARY
    /** マスタテーブル区分 */
    private String mdCmastertblname;

}
