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
 * [勤怠]月次集計処理ログ(ユーザー自動作成スクリプト用、一時退避テーブル)
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
@TableName("work_tmg_monthly_output_log")
public class WorkTmgMonthlyOutputLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tmol_ccustomerid
         */
    @TableField("tmol_ccustomerid")
        private String tmolCcustomerid;

        /**
         * tmol_ccompanyid
         */
    @TableField("tmol_ccompanyid")
        private String tmolCcompanyid;

        /**
         * tmol_dstartdate
         */
    @TableField("tmol_dstartdate")
        private Date tmolDstartdate;

        /**
         * tmol_denddate
         */
    @TableField("tmol_denddate")
        private Date tmolDenddate;

        /**
         * tmol_cmodifieruserid
         */
    @TableField("tmol_cmodifieruserid")
        private String tmolCmodifieruserid;

        /**
         * tmol_dmodifieddate
         */
    @TableField("tmol_dmodifieddate")
        private Date tmolDmodifieddate;

        /**
         * tmol_cmodifierprogramid
         */
    @TableField("tmol_cmodifierprogramid")
        private String tmolCmodifierprogramid;

        /**
         * tmol_cyyyymm
         */
    @TableField("tmol_cyyyymm")
        private Date tmolCyyyymm;

        /**
         * tmol_cstatusflg
         */
    @TableField("tmol_cstatusflg")
        private String tmolCstatusflg;

        /**
         * tmol_ncount
         */
    @TableField("tmol_ncount")
        private Long tmolNcount;

        /**
         * tnol_cerrcode
         */
    @TableField("tnol_cerrcode")
        private String tnolCerrcode;

        /**
         * tnol_cerrmsg
         */
    @TableField("tnol_cerrmsg")
        private String tnolCerrmsg;


        }