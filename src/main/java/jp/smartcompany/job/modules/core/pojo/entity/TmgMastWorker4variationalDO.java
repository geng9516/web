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
 * 変形労働時間制1ヶ月
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
@TableName("tmg_mast_worker4variational")
public class TmgMastWorker4variationalDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmwv_ccustomerid")
        private String tmwvCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmwv_ccompanyid")
        private String tmwvCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmwv_dstartdate")
        private Date tmwvDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmwv_denddate")
        private Date tmwvDenddate;

        /**
         * 更新者
         */
    @TableField("tmwv_cmodifieruserid")
        private String tmwvCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmwv_dmodifieddate")
        private Date tmwvDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmwv_cmodifierprogramid")
        private String tmwvCmodifierprogramid;

        /**
         * 勤怠種別
         */
                @TableId(value = "tmwv_cworktypeid", type = IdType.AUTO)
                private String tmwvCworktypeid;


        }