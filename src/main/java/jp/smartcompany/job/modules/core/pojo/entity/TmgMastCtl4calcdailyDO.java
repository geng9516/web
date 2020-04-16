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
 * 日次集計制御マスタ
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
@TableName("tmg_mast_ctl4calcdaily")
public class TmgMastCtl4calcdailyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmcc_ccustomerid")
        private String tmccCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmcc_ccompanyid")
        private String tmccCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmcc_dstartdate")
        private Date tmccDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmcc_denddate")
        private Date tmccDenddate;

        /**
         * 更新者
         */
    @TableField("tmcc_cmodifieruserid")
        private String tmccCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmcc_dmodifieddate")
        private Date tmccDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmcc_cmodifierprogramid")
        private String tmccCmodifierprogramid;

        /**
         * ステータスフラグ
         */
                @TableId(value = "tmcc_cstatusflg", type = IdType.AUTO)
                private String tmccCstatusflg;


        }