package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * データディクショナリマスタ
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mast_datadictionary")
public class MastDatadictionaryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "md_id", type = IdType.AUTO)
                private Long mdId;

        /**
         * 顧客コード
         */
    @TableField("md_ccustomerid")
        private String mdCcustomerid;

        /**
         * テーブルid
         */
    @TableField("md_ctablename")
        private String mdCtablename;

        /**
         * テーブル並び順
         */
    @TableField("md_ntableseq")
        private Long mdNtableseq;

        /**
         * カラムid
         */
    @TableField("md_ccolumnname")
        private String mdCcolumnname;

        /**
         * カラム名称（別名用ダミー）
         */
    @TableField("md_ccolumndesc")
        private String mdCcolumndesc;

        /**
         * カラム名称（日本語）
         */
    @TableField("md_ccolumndescja")
        private String mdCcolumndescja;

        /**
         * カラム名称（英語）
         */
    @TableField("md_ccolumndescen")
        private String mdCcolumndescen;

        /**
         * カラム名称（中国語）
         */
    @TableField("md_ccolumndescch")
        private String mdCcolumndescch;

        /**
         * カラム名称（予備１）
         */
    @TableField("md_ccolumndesc01")
        private String mdCcolumndesc01;

        /**
         * カラム名称（予備２）
         */
    @TableField("md_ccolumndesc02")
        private String mdCcolumndesc02;

        /**
         * カラムタイプ
         */
    @TableField("md_ctypeofcolumn")
        private String mdCtypeofcolumn;

        /**
         * カラムタイプ
         */
    @TableField("md_cexcepteddatatype")
        private String mdCexcepteddatatype;

        /**
         * 並び順
         */
    @TableField("md_nseq")
        private Long mdNseq;

        /**
         * セキュリティーレベル
         */
    @TableField("md_clevelid")
        private String mdClevelid;

        /**
         * マスタテーブル区分
         */
    @TableField("md_cmastertblname")
        private String mdCmastertblname;

        /**
         * クロス集計軸使用フラグ
         */
    @TableField("md_cavlforaxesincr")
        private String mdCavlforaxesincr;

        /**
         * クロス集計集計項目フラグ
         */
    @TableField("md_cavlforctnincr")
        private String mdCavlforctnincr;

        /**
         * クロス集計条件項目フラグ
         */
    @TableField("md_cavlforconditionincr")
        private String mdCavlforconditionincr;

        /**
         * グループ定義フラグ
         */
    @TableField("md_cavlgroups")
        private String mdCavlgroups;

        /**
         * 条件検索使用フラグ
         */
    @TableField("md_cavlforckstart")
        private String mdCavlforckstart;

        /**
         * 全文検索用フラグ
         */
    @TableField("md_cfulltextflg")
        private String mdCfulltextflg;

        /**
         * 全文検索結果表示アプリケーション
         */
    @TableField("md_cresultappid")
        private String mdCresultappid;

        /**
         * 全文検索結果表示サブアプリケーション
         */
    @TableField("md_cresultsubappid")
        private String mdCresultsubappid;

        /**
         * 全文検索結果表示画面
         */
    @TableField("md_cresultscreenid")
        private String mdCresultscreenid;

        /**
         * permalinkキー文字列
         */
    @TableField("md_cpermalinkkeyid")
        private String mdCpermalinkkeyid;

        /**
         * 計算式
         */
    @TableField("md_ccalculatecolumn")
        private String mdCcalculatecolumn;

        /**
         * （未使用）
         */
    @TableField("md_cmasttblcolumn")
        private String mdCmasttblcolumn;

        /**
         * （未使用）
         */
    @TableField("md_cmatcheswith")
        private String mdCmatcheswith;

        /**
         * 取得元idカラム
         */
    @TableField("md_csrcid")
        private String mdCsrcid;

        /**
         * 最終更新者
         */
    @TableField("md_cmodifieruserid")
        private String mdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("md_dmodifieddate")
        private Date mdDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }