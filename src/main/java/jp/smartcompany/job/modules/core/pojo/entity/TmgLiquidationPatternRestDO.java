package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 勤務パターン休憩情報
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
@TableName("tmg_liquidation_pattern_rest")
public class TmgLiquidationPatternRestDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tpr_ccustomerid")
        private String tprCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tpr_ccompanyid")
        private String tprCcompanyid;

        /**
         * 部局ｺｰﾄﾞ
         */
    @TableField("tpr_csectionid")
        private String tprCsectionid;

        /**
         * グループコード
         */
    @TableField("tpr_cgroupid")
        private String tprCgroupid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tpr_dstartdate")
        private Date tprDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tpr_denddate")
        private Date tprDenddate;

        /**
         * 更新者
         */
    @TableField("tpr_cmodifieruserid")
        private String tprCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tpr_dmodifieddate")
        private Date tprDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tpr_cmodifierprogramid")
        private String tprCmodifierprogramid;

        /**
         * 勤務パターンid
         */
        @TableId(value="tpr_cpatternid",type = IdType.INPUT)
        private String tprCpatternid;

        /**
         * 並び順
         */
    @TableField("tpr_seq")
        private Long tprSeq;

        /**
         * 休憩開始時間
         */
    @TableField("tpr_nrestopen")
        private Long tprNrestopen;

        /**
         * 休憩終了時間
         */
    @TableField("tpr_nrestclose")
        private Long tprNrestclose;


        }