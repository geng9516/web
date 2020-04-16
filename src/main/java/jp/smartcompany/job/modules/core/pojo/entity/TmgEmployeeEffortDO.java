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
 * public.tmg_employee_effort
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
@TableName("tmg_employee_effort")
public class TmgEmployeeEffortDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("tef_ccustomerid")
        private String tefCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tef_ccompanyid")
        private String tefCcompanyid;

        /**
         * 職員番号
         */
    @TableField("tef_cemployeeid")
        private String tefCemployeeid;

        /**
         * 開始日付
         */
    @TableField("tef_dstartdate")
        private Date tefDstartdate;

        /**
         * 終了日付
         */
    @TableField("tef_denddate")
        private Date tefDenddate;

        /**
         * 更新者
         */
    @TableField("tef_cmodifieruserid")
        private String tefCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tef_dmodifieddate")
        private Date tefDmodifieddate;

        /**
         * 更新プログラム
         */
    @TableField("tef_cmodifierprogramid")
        private String tefCmodifierprogramid;

        /**
         * 項目区分
         */
    @TableField("tef_ctype")
        private String tefCtype;

        /**
         * 予算項id
         */
    @TableField("tef_cbudgetid")
        private String tefCbudgetid;

        /**
         * 予算項名称
         */
    @TableField("tef_cbudgetname")
        private String tefCbudgetname;

        /**
         * 予算枠
         */
    @TableField("tef_nbudgetratio")
        private Long tefNbudgetratio;

        /**
         * 経費id
         */
    @TableField("tef_cexpenseid")
        private String tefCexpenseid;

        /**
         * 経費名称
         */
    @TableField("tef_cexpensename")
        private String tefCexpensename;


        }