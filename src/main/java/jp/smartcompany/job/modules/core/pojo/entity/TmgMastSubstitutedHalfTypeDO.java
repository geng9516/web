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
 * 振替休日区分マスタ
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
@TableName("tmg_mast_substituted_half_type")
public class TmgMastSubstitutedHalfTypeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tmsh_ccustomerid")
        private String tmshCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmsh_ccompanyid")
        private String tmshCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmsh_dstartdate")
        private Date tmshDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmsh_denddate")
        private Date tmshDenddate;

        /**
         * 更新者
         */
    @TableField("tmsh_cmodifieruserid")
        private String tmshCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmsh_dmodifieddate")
        private Date tmshDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmsh_cmodifierprogramid")
        private String tmshCmodifierprogramid;

        /**
         * 申請区分
         */
                @TableId(value = "tmsh_cntftype", type = IdType.AUTO)
                private String tmshCntftype;


        }