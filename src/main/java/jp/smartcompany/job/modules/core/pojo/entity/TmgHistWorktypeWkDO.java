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
 * [勤怠]hr連携用(勤怠種別)(前回分)
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
@TableName("tmg_hist_worktype_wk")
public class TmgHistWorktypeWkDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("thw_ccustomerid")
        private String thwCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("thw_ccompanyid")
        private String thwCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "thw_cemployeeid", type = IdType.AUTO)
                private String thwCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("thw_dstartdate")
        private Date thwDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("thw_denddate")
        private Date thwDenddate;

        /**
         * 更新者
         */
    @TableField("thw_cmodifieruserid")
        private String thwCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("thw_dmodifieddate")
        private Date thwDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("thw_cmodifierprogramid")
        private String thwCmodifierprogramid;

        /**
         * 勤怠種別                                                        mgd:tmg_worktype
         */
    @TableField("thw_cworktypeid")
        private String thwCworktypeid;

        /**
         * 勤怠種別名称
         */
    @TableField("thw_cworktypename")
        private String thwCworktypename;


        }