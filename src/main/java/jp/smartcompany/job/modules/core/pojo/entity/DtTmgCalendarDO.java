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
 * [勤怠]カレンダー
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
@TableName("dt_tmg_calendar")
public class DtTmgCalendarDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tca_ccustomerid")
        private String tcaCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tca_ccompanyid")
        private String tcaCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tca_dstartdate")
        private Date tcaDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tca_denddate")
        private Date tcaDenddate;

        /**
         * 更新者
         */
    @TableField("tca_cmodifieruserid")
        private String tcaCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tca_dmodifieddate")
        private Date tcaDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tca_cmodifierprogramid")
        private String tcaCmodifierprogramid;

        /**
         * 該当年月                          yyyy/mm/01
         */
                @TableId(value = "tca_dyyyymm", type = IdType.AUTO)
                private Date tcaDyyyymm;

        /**
         * 更新フラグ
         */
    @TableField("tca_editflag")
        private String tcaEditflag;

        /**
         * 休日フラグ：1日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg01")
        private String tcaCholflg01;

        /**
         * 休日フラグ：2日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg02")
        private String tcaCholflg02;

        /**
         * 休日フラグ：3日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg03")
        private String tcaCholflg03;

        /**
         * 休日フラグ：4日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg04")
        private String tcaCholflg04;

        /**
         * 休日フラグ：5日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg05")
        private String tcaCholflg05;

        /**
         * 休日フラグ：6日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg06")
        private String tcaCholflg06;

        /**
         * 休日フラグ：7日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg07")
        private String tcaCholflg07;

        /**
         * 休日フラグ：8日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg08")
        private String tcaCholflg08;

        /**
         * 休日フラグ：9日                                                    mgd:tmg_holflg
         */
    @TableField("tca_cholflg09")
        private String tcaCholflg09;

        /**
         * 休日フラグ：10日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg10")
        private String tcaCholflg10;

        /**
         * 休日フラグ：11日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg11")
        private String tcaCholflg11;

        /**
         * 休日フラグ：12日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg12")
        private String tcaCholflg12;

        /**
         * 休日フラグ：13日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg13")
        private String tcaCholflg13;

        /**
         * 休日フラグ：14日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg14")
        private String tcaCholflg14;

        /**
         * 休日フラグ：15日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg15")
        private String tcaCholflg15;

        /**
         * 休日フラグ：16日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg16")
        private String tcaCholflg16;

        /**
         * 休日フラグ：17日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg17")
        private String tcaCholflg17;

        /**
         * 休日フラグ：18日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg18")
        private String tcaCholflg18;

        /**
         * 休日フラグ：19日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg19")
        private String tcaCholflg19;

        /**
         * 休日フラグ：20日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg20")
        private String tcaCholflg20;

        /**
         * 休日フラグ：21日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg21")
        private String tcaCholflg21;

        /**
         * 休日フラグ：22日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg22")
        private String tcaCholflg22;

        /**
         * 休日フラグ：23日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg23")
        private String tcaCholflg23;

        /**
         * 休日フラグ：24日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg24")
        private String tcaCholflg24;

        /**
         * 休日フラグ：25日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg25")
        private String tcaCholflg25;

        /**
         * 休日フラグ：26日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg26")
        private String tcaCholflg26;

        /**
         * 休日フラグ：27日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg27")
        private String tcaCholflg27;

        /**
         * 休日フラグ：28日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg28")
        private String tcaCholflg28;

        /**
         * 休日フラグ：29日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg29")
        private String tcaCholflg29;

        /**
         * 休日フラグ：30日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg30")
        private String tcaCholflg30;

        /**
         * 休日フラグ：31日                                                   mgd:tmg_holflg
         */
    @TableField("tca_cholflg31")
        private String tcaCholflg31;


        }