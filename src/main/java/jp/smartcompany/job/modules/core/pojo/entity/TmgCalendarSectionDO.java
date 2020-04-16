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
 * [勤怠]カレンダー :
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
@TableName("tmg_calendar_section")
public class TmgCalendarSectionDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ 固定：01
         */
                @TableId(value = "tcas_ccustomerid", type = IdType.AUTO)
                private String tcasCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tcas_ccompanyid")
        private String tcasCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日  固定：1900/01/01
         */
    @TableField("tcas_dstartdate")
        private Date tcasDstartdate;

        /**
         * ﾃﾞｰﾀ終了日 固定：2222/12/31
         */
    @TableField("tcas_denddate")
        private Date tcasDenddate;

        /**
         * 部署ｺｰﾄﾞ
         */
    @TableField("tcas_csectionid")
        private String tcasCsectionid;

        /**
         * 更新者
         */
    @TableField("tcas_cmodifieruserid")
        private String tcasCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tcas_dmodifieddate")
        private Date tcasDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tcas_cmodifierprogramid")
        private String tcasCmodifierprogramid;

        /**
         * グループコード
         */
    @TableField("tcas_cgroupid")
        private String tcasCgroupid;

        /**
         * 該当年月 yyyy/mm/01
         */
    @TableField("tcas_dyyyymm")
        private Date tcasDyyyymm;

        /**
         * 更新フラグ
         */
    @TableField("tcas_editflag")
        private String tcasEditflag;

        /**
         * 休日フラグ：1日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg01")
        private String tcasCholflg01;

        /**
         * 休日フラグ：2日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg02")
        private String tcasCholflg02;

        /**
         * 休日フラグ：3日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg03")
        private String tcasCholflg03;

        /**
         * 休日フラグ：4日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg04")
        private String tcasCholflg04;

        /**
         * 休日フラグ：5日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg05")
        private String tcasCholflg05;

        /**
         * 休日フラグ：6日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg06")
        private String tcasCholflg06;

        /**
         * 休日フラグ：7日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg07")
        private String tcasCholflg07;

        /**
         * 休日フラグ：8日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg08")
        private String tcasCholflg08;

        /**
         * 休日フラグ：9日 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg09")
        private String tcasCholflg09;

        /**
         * 休日フラグ：10 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg10")
        private String tcasCholflg10;

        /**
         * 休日フラグ：11 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg11")
        private String tcasCholflg11;

        /**
         * 休日フラグ：12 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg12")
        private String tcasCholflg12;

        /**
         * 休日フラグ：13 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg13")
        private String tcasCholflg13;

        /**
         * 休日フラグ：14 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg14")
        private String tcasCholflg14;

        /**
         * 休日フラグ：15 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg15")
        private String tcasCholflg15;

        /**
         * 休日フラグ：16 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg16")
        private String tcasCholflg16;

        /**
         * 休日フラグ：17 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg17")
        private String tcasCholflg17;

        /**
         * 休日フラグ：18 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg18")
        private String tcasCholflg18;

        /**
         * 休日フラグ：19 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg19")
        private String tcasCholflg19;

        /**
         * 休日フラグ：20 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg20")
        private String tcasCholflg20;

        /**
         * 休日フラグ：21 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg21")
        private String tcasCholflg21;

        /**
         * 休日フラグ：22 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg22")
        private String tcasCholflg22;

        /**
         * 休日フラグ：23 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg23")
        private String tcasCholflg23;

        /**
         * 休日フラグ：24 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg24")
        private String tcasCholflg24;

        /**
         * 休日フラグ：25 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg25")
        private String tcasCholflg25;

        /**
         * 休日フラグ：26 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg26")
        private String tcasCholflg26;

        /**
         * 休日フラグ：27 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg27")
        private String tcasCholflg27;

        /**
         * 休日フラグ：28 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg28")
        private String tcasCholflg28;

        /**
         * 休日フラグ：29 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg29")
        private String tcasCholflg29;

        /**
         * 休日フラグ：30 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg30")
        private String tcasCholflg30;

        /**
         * 休日フラグ：31 mgd:tmg_holflg
         */
    @TableField("tcas_cholflg31")
        private String tcasCholflg31;


        }