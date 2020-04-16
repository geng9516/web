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
 * [勤怠]hr連携用(勤怠種別)
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
@TableName("tmg_hist_worktype_sim")
public class TmgHistWorktypeSimDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("thws_ccustomerid")
        private String thwsCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("thws_ccompanyid")
        private String thwsCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "thws_cemployeeid", type = IdType.AUTO)
                private String thwsCemployeeid;

        /**
         * ユーザid
         */
    @TableField("thws_cuserid")
        private String thwsCuserid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("thws_dstartdate")
        private Date thwsDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("thws_denddate")
        private Date thwsDenddate;

        /**
         * 更新者
         */
    @TableField("thws_cmodifieruserid")
        private String thwsCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("thws_dmodifieddate")
        private Date thwsDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("thws_cmodifierprogramid")
        private String thwsCmodifierprogramid;

        /**
         * 勤怠種別                                                        mgd:tmg_worktype
         */
    @TableField("thws_cworktypeid")
        private String thwsCworktypeid;

        /**
         * 勤怠種別名称
         */
    @TableField("thws_cworktypename")
        private String thwsCworktypename;


        }