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
 * public.dt_hist_announcement_salary
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
@TableName("dt_hist_announcement_salary")
public class DtHistAnnouncementSalaryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("has_ccustomerid")
        private String hasCcustomerid;

        /**
         * 法人コード
         */
    @TableField("has_ccompanyid")
        private String hasCcompanyid;

        /**
         * 社員番号
         */
    @TableField("has_cemployeeid")
        private String hasCemployeeid;

        /**
         * 開始日付
         */
    @TableField("has_dstartdate")
        private Date hasDstartdate;

        /**
         * 終了日付
         */
    @TableField("has_denddate")
        private Date hasDenddate;

        /**
         * 更新者
         */
    @TableField("has_dmodifieruserid")
        private String hasDmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("has_dmodifieddate")
        private Date hasDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("has_cmodifierprogramid")
        private String hasCmodifierprogramid;

        /**
         * 発令日
         */
    @TableField("has_dhatureist")
        private Date hasDhatureist;

        /**
         * 発令区分
         */
    @TableField("has_chatureikb")
        private String hasChatureikb;

        /**
         * 発令名称
         */
    @TableField("has_chatureinm")
        private String hasChatureinm;


        }