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
 * public.tmg_notification_check_log
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
@TableName("tmg_notification_check_log")
public class TmgNotificationCheckLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tntf_dcreated
         */
    @TableField("tntf_dcreated")
        private Date tntfDcreated;

        /**
         * tntf_ccustomerid
         */
    @TableField("tntf_ccustomerid")
        private String tntfCcustomerid;

        /**
         * tntf_ccompanyid
         */
    @TableField("tntf_ccompanyid")
        private String tntfCcompanyid;

        /**
         * tntf_cemployeeid
         */
    @TableField("tntf_cemployeeid")
        private String tntfCemployeeid;

        /**
         * tntf_dstartdate
         */
    @TableField("tntf_dstartdate")
        private Date tntfDstartdate;

        /**
         * tntf_denddate
         */
    @TableField("tntf_denddate")
        private Date tntfDenddate;

        /**
         * tntf_cmodifieruserid
         */
    @TableField("tntf_cmodifieruserid")
        private String tntfCmodifieruserid;

        /**
         * tntf_dmodifieddate
         */
    @TableField("tntf_dmodifieddate")
        private Date tntfDmodifieddate;

        /**
         * tntf_cmodifierprogramid
         */
    @TableField("tntf_cmodifierprogramid")
        private String tntfCmodifierprogramid;

        /**
         * tntf_nseq
         */
    @TableField("tntf_nseq")
        private Long tntfNseq;

        /**
         * tntf_cntfno
         */
    @TableField("tntf_cntfno")
        private String tntfCntfno;

        /**
         * tntf_cstatusflg
         */
    @TableField("tntf_cstatusflg")
        private String tntfCstatusflg;

        /**
         * tntf_calteremployeeid
         */
    @TableField("tntf_calteremployeeid")
        private String tntfCalteremployeeid;

        /**
         * tntf_dnotification
         */
    @TableField("tntf_dnotification")
        private Date tntfDnotification;

        /**
         * tntf_dbegin
         */
    @TableField("tntf_dbegin")
        private Date tntfDbegin;

        /**
         * tntf_dend
         */
    @TableField("tntf_dend")
        private Date tntfDend;

        /**
         * tntf_dcancel
         */
    @TableField("tntf_dcancel")
        private Date tntfDcancel;

        /**
         * tntf_ntime_open
         */
    @TableField("tntf_ntime_open")
        private Long tntfNtimeOpen;

        /**
         * tntf_ntime_close
         */
    @TableField("tntf_ntime_close")
        private Long tntfNtimeClose;

        /**
         * tntf_ntimezone_open
         */
    @TableField("tntf_ntimezone_open")
        private Long tntfNtimezoneOpen;

        /**
         * tntf_ntimezone_close
         */
    @TableField("tntf_ntimezone_close")
        private Long tntfNtimezoneClose;

        /**
         * tntf_nnoreserved
         */
    @TableField("tntf_nnoreserved")
        private Long tntfNnoreserved;

        /**
         * tntf_nmon
         */
    @TableField("tntf_nmon")
        private Long tntfNmon;

        /**
         * tntf_ntue
         */
    @TableField("tntf_ntue")
        private Long tntfNtue;

        /**
         * tntf_nwed
         */
    @TableField("tntf_nwed")
        private Long tntfNwed;

        /**
         * tntf_nthu
         */
    @TableField("tntf_nthu")
        private Long tntfNthu;

        /**
         * tntf_nfri
         */
    @TableField("tntf_nfri")
        private Long tntfNfri;

        /**
         * tntf_nsat
         */
    @TableField("tntf_nsat")
        private Long tntfNsat;

        /**
         * tntf_nsun
         */
    @TableField("tntf_nsun")
        private Long tntfNsun;

        /**
         * tntf_ndayofweek
         */
    @TableField("tntf_ndayofweek")
        private Long tntfNdayofweek;

        /**
         * tntf_ctype
         */
    @TableField("tntf_ctype")
        private String tntfCtype;

        /**
         * tntf_cowncomment
         */
    @TableField("tntf_cowncomment")
        private String tntfCowncomment;

        /**
         * tntf_cboss
         */
    @TableField("tntf_cboss")
        private String tntfCboss;

        /**
         * tntf_cbosscomment
         */
    @TableField("tntf_cbosscomment")
        private String tntfCbosscomment;

        /**
         * tntf_dboss
         */
    @TableField("tntf_dboss")
        private Date tntfDboss;

        /**
         * tntf_ccancel
         */
    @TableField("tntf_ccancel")
        private String tntfCcancel;

        /**
         * tntf_ccancelcomment
         */
    @TableField("tntf_ccancelcomment")
        private String tntfCcancelcomment;

        /**
         * tntf_ddaikyu
         */
    @TableField("tntf_ddaikyu")
        private Date tntfDdaikyu;

        /**
         * tntf_csick_type
         */
    @TableField("tntf_csick_type")
        private String tntfCsickType;

        /**
         * tntf_csick_name
         */
    @TableField("tntf_csick_name")
        private String tntfCsickName;

        /**
         * tntf_csame_sick_type
         */
    @TableField("tntf_csame_sick_type")
        private String tntfCsameSickType;

        /**
         * tntf_cdisaster
         */
    @TableField("tntf_cdisaster")
        private String tntfCdisaster;

        /**
         * tntf_dperiod_date
         */
    @TableField("tntf_dperiod_date")
        private Date tntfDperiodDate;

        /**
         * tntf_nuapper_addition
         */
    @TableField("tntf_nuapper_addition")
        private Long tntfNuapperAddition;

        /**
         * tntf_cntfno_im
         */
    @TableField("tntf_cntfno_im")
        private String tntfCntfnoIm;

        /**
         * tntf_nrestopen
         */
    @TableField("tntf_nrestopen")
        private Long tntfNrestopen;

        /**
         * tntf_nrestclose
         */
    @TableField("tntf_nrestclose")
        private Long tntfNrestclose;

        /**
         * tntf_ckanjiname
         */
    @TableField("tntf_ckanjiname")
        private String tntfCkanjiname;

        /**
         * tntf_crelation
         */
    @TableField("tntf_crelation")
        private String tntfCrelation;

        /**
         * tntf_ddateofbirth
         */
    @TableField("tntf_ddateofbirth")
        private Date tntfDdateofbirth;

        /**
         * 対象の人数
         */
    @TableField("tntf_nnumber_of_target")
        private Long tntfNnumberOfTarget;

        /**
         * 分割前申請番号
         */
    @TableField("tntf_cntfno_moto")
        private String tntfCntfnoMoto;

        /**
         * 解除：終了日
         */
    @TableField("tntf_dcancelend")
        private Date tntfDcancelend;


        }