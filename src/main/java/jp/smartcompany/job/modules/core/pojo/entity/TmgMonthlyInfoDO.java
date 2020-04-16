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
 * [勤怠]月単位日別情報                   tmg_dailyのビュー代わり。承認状況一覧、超過勤務指示
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
@TableName("tmg_monthly_info")
public class TmgMonthlyInfoDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmi_ccustomerid")
        private String tmiCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmi_ccompanyid")
        private String tmiCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tmi_cemployeeid", type = IdType.AUTO)
                private String tmiCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tmi_dstartdate")
        private Date tmiDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tmi_denddate")
        private Date tmiDenddate;

        /**
         * 更新者
         */
    @TableField("tmi_cmodifieruserid")
        private String tmiCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmi_dmodifieddate")
        private Date tmiDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmi_cmodifierprogramid")
        private String tmiCmodifierprogramid;

        /**
         * 該当年月                          yyyy/mm/01
         */
    @TableField("tmi_dyyyymm")
        private Date tmiDyyyymm;

        /**
         * コンテンツタイプ                                                    mgd:tmg_contentid
         */
    @TableField("tmi_ccontentid")
        private String tmiCcontentid;

        /**
         * 日別情報：1日
         */
    @TableField("tmi_cinfo01")
        private String tmiCinfo01;

        /**
         * 日別情報：2日
         */
    @TableField("tmi_cinfo02")
        private String tmiCinfo02;

        /**
         * 日別情報：3日
         */
    @TableField("tmi_cinfo03")
        private String tmiCinfo03;

        /**
         * 日別情報：4日
         */
    @TableField("tmi_cinfo04")
        private String tmiCinfo04;

        /**
         * 日別情報：5日
         */
    @TableField("tmi_cinfo05")
        private String tmiCinfo05;

        /**
         * 日別情報：6日
         */
    @TableField("tmi_cinfo06")
        private String tmiCinfo06;

        /**
         * 日別情報：7日
         */
    @TableField("tmi_cinfo07")
        private String tmiCinfo07;

        /**
         * 日別情報：8日
         */
    @TableField("tmi_cinfo08")
        private String tmiCinfo08;

        /**
         * 日別情報：9日
         */
    @TableField("tmi_cinfo09")
        private String tmiCinfo09;

        /**
         * 日別情報：10日
         */
    @TableField("tmi_cinfo10")
        private String tmiCinfo10;

        /**
         * 日別情報：11日
         */
    @TableField("tmi_cinfo11")
        private String tmiCinfo11;

        /**
         * 日別情報：12日
         */
    @TableField("tmi_cinfo12")
        private String tmiCinfo12;

        /**
         * 日別情報：13日
         */
    @TableField("tmi_cinfo13")
        private String tmiCinfo13;

        /**
         * 日別情報：14日
         */
    @TableField("tmi_cinfo14")
        private String tmiCinfo14;

        /**
         * 日別情報：15日
         */
    @TableField("tmi_cinfo15")
        private String tmiCinfo15;

        /**
         * 日別情報：16日
         */
    @TableField("tmi_cinfo16")
        private String tmiCinfo16;

        /**
         * 日別情報：17日
         */
    @TableField("tmi_cinfo17")
        private String tmiCinfo17;

        /**
         * 日別情報：18日
         */
    @TableField("tmi_cinfo18")
        private String tmiCinfo18;

        /**
         * 日別情報：19日
         */
    @TableField("tmi_cinfo19")
        private String tmiCinfo19;

        /**
         * 日別情報：20日
         */
    @TableField("tmi_cinfo20")
        private String tmiCinfo20;

        /**
         * 日別情報：21日
         */
    @TableField("tmi_cinfo21")
        private String tmiCinfo21;

        /**
         * 日別情報：22日
         */
    @TableField("tmi_cinfo22")
        private String tmiCinfo22;

        /**
         * 日別情報：23日
         */
    @TableField("tmi_cinfo23")
        private String tmiCinfo23;

        /**
         * 日別情報：24日
         */
    @TableField("tmi_cinfo24")
        private String tmiCinfo24;

        /**
         * 日別情報：25日
         */
    @TableField("tmi_cinfo25")
        private String tmiCinfo25;

        /**
         * 日別情報：26日
         */
    @TableField("tmi_cinfo26")
        private String tmiCinfo26;

        /**
         * 日別情報：27日
         */
    @TableField("tmi_cinfo27")
        private String tmiCinfo27;

        /**
         * 日別情報：28日
         */
    @TableField("tmi_cinfo28")
        private String tmiCinfo28;

        /**
         * 日別情報：29日
         */
    @TableField("tmi_cinfo29")
        private String tmiCinfo29;

        /**
         * 日別情報：30日
         */
    @TableField("tmi_cinfo30")
        private String tmiCinfo30;

        /**
         * 日別情報：31日
         */
    @TableField("tmi_cinfo31")
        private String tmiCinfo31;


        }