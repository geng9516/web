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
 * フレックス制身分マスタ
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
@TableName("tmg_mast_worker4flex")
public class TmgMastWorker4flexDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmwf_ccustomerid")
        private String tmwfCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmwf_ccompanyid")
        private String tmwfCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmwf_dstartdate")
        private Date tmwfDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmwf_denddate")
        private Date tmwfDenddate;

        /**
         * 更新者
         */
    @TableField("tmwf_cmodifieruserid")
        private String tmwfCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmwf_dmodifieddate")
        private Date tmwfDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmwf_cmodifierprogramid")
        private String tmwfCmodifierprogramid;

        /**
         * 勤怠種別
         */
                @TableId(value = "tmwf_cworktypeid", type = IdType.AUTO)
                private String tmwfCworktypeid;


        }