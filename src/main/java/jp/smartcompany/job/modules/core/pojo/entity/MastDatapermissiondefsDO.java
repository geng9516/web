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
 * 検索対象範囲条件定義マスタ(条件式)
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
@TableName("mast_datapermissiondefs")
public class MastDatapermissiondefsDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mdpd_id", type = IdType.AUTO)
                private Long mdpdId;

        /**
         * 定義id
         */
    @TableField("mdpd_cpermissionid")
        private String mdpdCpermissionid;

        /**
         * シーケンス番号
         */
    @TableField("mdpd_nseq")
        private Long mdpdNseq;

        /**
         * 論理演算子
         */
    @TableField("mdpd_candor")
        private String mdpdCandor;

        /**
         * 左カッコ
         */
    @TableField("mdpd_copenedparenthsis")
        private String mdpdCopenedparenthsis;

        /**
         * テーブルid
         */
    @TableField("mdpd_ctableid")
        private String mdpdCtableid;

        /**
         * カラムid
         */
    @TableField("mdpd_ccolumnid")
        private String mdpdCcolumnid;

        /**
         * カラム名
         */
    @TableField("mdpd_ccolumnname")
        private String mdpdCcolumnname;

        /**
         * データ型
         */
    @TableField("mdpd_ctypeofcolumn")
        private String mdpdCtypeofcolumn;

        /**
         * 比較演算子
         */
    @TableField("mdpd_coperator")
        private String mdpdCoperator;

        /**
         * 法人コード
         */
    @TableField("mdpd_ccompanyid")
        private String mdpdCcompanyid;

        /**
         * 比較値
         */
    @TableField("mdpd_cvalue")
        private String mdpdCvalue;

        /**
         * 表示文字列
         */
    @TableField("mdpd_cdisplayvalue")
        private String mdpdCdisplayvalue;

        /**
         * 自分のフラグ
         */
    @TableField("mdpd_cmyflag")
        private String mdpdCmyflag;

        /**
         * 右カッコ
         */
    @TableField("mdpd_cclosedparenthsis")
        private String mdpdCclosedparenthsis;

        /**
         * 最終更新者
         */
    @TableField("mdpd_cmodifieruserid")
        private String mdpdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mdpd_dmodifieddate")
        private Date mdpdDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }