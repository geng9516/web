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
 * クロス集計検索設定保存データ（検索条件定義）
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
@TableName("hist_crosssearch_where")
public class HistCrosssearchWhereDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hsw_id", type = IdType.AUTO)
                private Long hswId;

        /**
         * 顧客コード
         */
    @TableField("hsw_ccustomerid_ck")
        private String hswCcustomeridCk;

        /**
         * 法人コード
         */
    @TableField("hsw_ccompanyid_ck")
        private String hswCcompanyidCk;

        /**
         * 社員番号
         */
    @TableField("hsw_cemployeeid_ck")
        private String hswCemployeeidCk;

        /**
         * 設定名称
         */
    @TableField("hsw_cfilename_ck")
        private String hswCfilenameCk;

        /**
         * 共有フラグ
         */
    @TableField("hsw_cifpublic")
        private String hswCifpublic;

        /**
         * 並び順
         */
    @TableField("hsw_nseq")
        private Long hswNseq;

        /**
         * カラム識別コード
         */
    @TableField("hsw_citemseq")
        private String hswCitemseq;

        /**
         * and/or
         */
    @TableField("hsw_candor")
        private String hswCandor;

        /**
         * 左カッコ
         */
    @TableField("hsw_clparenthesis")
        private String hswClparenthesis;

        /**
         * 比較演算子
         */
    @TableField("hsw_coperator")
        private String hswCoperator;

        /**
         * 比較値
         */
    @TableField("hsw_cvalue")
        private String hswCvalue;

        /**
         * 右カッコ
         */
    @TableField("hsw_crparenthesis")
        private String hswCrparenthesis;

        /**
         * 最終更新者
         */
    @TableField("hsw_cmodifieruserid")
        private String hswCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hsw_dmodifieddate")
        private Date hswDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }