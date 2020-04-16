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
 * 裁量労働身分マスタ
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
@TableName("tmg_mast_worker4discretionary")
public class TmgMastWorker4discretionaryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmwd_ccustomerid")
        private String tmwdCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmwd_ccompanyid")
        private String tmwdCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmwd_dstartdate")
        private Date tmwdDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmwd_denddate")
        private Date tmwdDenddate;

        /**
         * 更新者
         */
    @TableField("tmwd_cmodifieruserid")
        private String tmwdCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmwd_dmodifieddate")
        private Date tmwdDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmwd_cmodifierprogramid")
        private String tmwdCmodifierprogramid;

        /**
         * 勤怠種別
         */
                @TableId(value = "tmwd_cworktypeid", type = IdType.AUTO)
                private String tmwdCworktypeid;


        }