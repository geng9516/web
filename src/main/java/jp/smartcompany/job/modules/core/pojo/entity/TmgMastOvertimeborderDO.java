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
 * 超勤閾値マスタ
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
@TableName("tmg_mast_overtimeborder")
public class TmgMastOvertimeborderDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmob_ccustomerid")
        private String tmobCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmob_ccompanyid")
        private String tmobCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmob_dstartdate")
        private Date tmobDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmob_denddate")
        private Date tmobDenddate;

        /**
         * 更新者
         */
    @TableField("tmob_cmodifieruserid")
        private String tmobCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmob_dmodifieddate")
        private Date tmobDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmob_cmodifierprogramid")
        private String tmobCmodifierprogramid;

        /**
         * 超勤閾値コード
         */
                @TableId(value = "tmob_covertimeborderid", type = IdType.AUTO)
                private String tmobCovertimeborderid;

        /**
         * 閾値（以上）
         */
    @TableField("tmob_nrangefrom")
        private Long tmobNrangefrom;

        /**
         * 閾値（以下）
         */
    @TableField("tmob_nrangeto")
        private Long tmobNrangeto;


        }