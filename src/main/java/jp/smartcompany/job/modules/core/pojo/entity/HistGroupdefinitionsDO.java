package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
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
 * グループ定義条件式データ
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
@KeySequence("HIST_GROUPDEFINITIONS_SEQ ")
@TableName("hist_groupdefinitions")
public class HistGroupdefinitionsDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hgd_id")
                private Long hgdId;

        /**
         * 顧客コード
         */
    @TableField("hgd_ccustomerid")
        private String hgdCcustomerid;

        /**
         * システムコード
         */
    @TableField("hgd_csystemid")
        private String hgdCsystemid;

        /**
         * グループid
         */
    @TableField("hgd_cgroupid")
        private String hgdCgroupid;

        /**
         * シーケンス番号
         */
    @TableField("hgd_nseq")
        private Long hgdNseq;

        /**
         * データ開始日
         */
    @TableField("hgd_dstartdate")
        private Date hgdDstartdate;

        /**
         * データ終了日
         */
    @TableField("hgd_denddate")
        private Date hgdDenddate;

        /**
         * 法人コード
         */
    @TableField("hgd_ccompanyid")
        private String hgdCcompanyid;

        /**
         * 論理演算子
         */
    @TableField("hgd_candor")
        private String hgdCandor;

        /**
         * カッコ
         */
    @TableField("hgd_copenedparenthsis")
        private String hgdCopenedparenthsis;

        /**
         * テーブルid
         */
    @TableField("hgd_ctableid")
        private String hgdCtableid;

        /**
         * カラムid
         */
    @TableField("hgd_ccolumnid")
        private String hgdCcolumnid;

        /**
         * カラム名
         */
    @TableField("hgd_ccolumnname")
        private String hgdCcolumnname;

        /**
         * データ型
         */
    @TableField("hgd_ctypeofcolumn")
        private String hgdCtypeofcolumn;

        /**
         * 演算子
         */
    @TableField("hgd_coperator")
        private String hgdCoperator;

        /**
         * 比較値
         */
    @TableField("hgd_cvalue")
        private String hgdCvalue;

        /**
         * 表示文字列
         */
    @TableField("hgd_cdisplayvalue")
        private String hgdCdisplayvalue;

        /**
         * 閉じカッコ
         */
    @TableField("hgd_cclosedparenthsis")
        private String hgdCclosedparenthsis;

        /**
         * 最終更新者
         */
    @TableField("hgd_cmodifieruserid")
        private String hgdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hgd_dmodifieddate")
        private Date hgdDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }