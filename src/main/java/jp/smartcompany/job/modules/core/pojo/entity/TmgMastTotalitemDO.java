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
 * 集計項目マスタ
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
@TableName("tmg_mast_totalitem")
public class TmgMastTotalitemDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmti_ccustomerid")
        private String tmtiCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmti_ccompanyid")
        private String tmtiCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmti_dstartdate")
        private Date tmtiDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmti_denddate")
        private Date tmtiDenddate;

        /**
         * 更新者
         */
    @TableField("tmti_cmodifieruserid")
        private String tmtiCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmti_dmodifieddate")
        private Date tmtiDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmti_cmodifierprogramid")
        private String tmtiCmodifierprogramid;

        /**
         * 集計カテゴリｺｰﾄﾞ
         */
                @TableId(value = "tmti_ccategoryid", type = IdType.AUTO)
                private String tmtiCcategoryid;

        /**
         * 集計単位ｺｰﾄﾞ
         */
    @TableField("tmti_cunitid")
        private String tmtiCunitid;

        /**
         * 集計項目ｺｰﾄﾞ
         */
    @TableField("tmti_ctotalizationid")
        private String tmtiCtotalizationid;

        /**
         * 集計項目名称
         */
    @TableField("tmti_ctotalizationnm")
        private String tmtiCtotalizationnm;

        /**
         * 自動承認除外フラグ　０：除外しない　１：除外する
         */
    @TableField("tmti_nauto_approve_exempt_flg")
        private Long tmtiNautoApproveExemptFlg;


        }