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
@TableName("tmg_mast_substitutedtype")
public class TmgMastSubstitutedtypeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmsb_ccustomerid")
        private String tmsbCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmsb_ccompanyid")
        private String tmsbCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmsb_dstartdate")
        private Date tmsbDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmsb_denddate")
        private Date tmsbDenddate;

        /**
         * 更新者
         */
    @TableField("tmsb_cmodifieruserid")
        private String tmsbCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmsb_dmodifieddate")
        private Date tmsbDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmsb_cmodifierprogramid")
        private String tmsbCmodifierprogramid;

        /**
         * 就業区分ｺｰﾄﾞ
         */
                @TableId(value = "tmsb_cworkingid", type = IdType.AUTO)
                private String tmsbCworkingid;


        }