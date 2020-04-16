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
 * [勤怠]未承認通知
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
@TableName("tmg_noticeconsent")
public class TmgNoticeconsentDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("tnc_ccustomerid")
        private String tncCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tnc_ccompanyid")
        private String tncCcompanyid;

        /**
         * 職員番号
         */
    @TableField("tnc_cemployeeid")
        private String tncCemployeeid;

        /**
         * 開始日
         */
    @TableField("tnc_dstartdate")
        private Date tncDstartdate;

        /**
         * 終了日
         */
    @TableField("tnc_denddate")
        private Date tncDenddate;

        /**
         * 更新者
         */
    @TableField("tnc_cmodifieruserid")
        private String tncCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tnc_dmodifieddate")
        private Date tncDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tnc_cmodifierprogramid")
        private String tncCmodifierprogramid;

        /**
         * 日付
         */
    @TableField("tnc_ddate")
        private Date tncDdate;

        /**
         * 本文文字列01
         */
    @TableField("tnc_ctext01")
        private String tncCtext01;

        /**
         * 本文文字列02
         */
    @TableField("tnc_ctext02")
        private String tncCtext02;

        /**
         * 本文文字列03
         */
    @TableField("tnc_ctext03")
        private String tncCtext03;

        /**
         * 本文文字列04
         */
    @TableField("tnc_ctext04")
        private String tncCtext04;

        /**
         * 本文文字列05
         */
    @TableField("tnc_ctext05")
        private String tncCtext05;

        /**
         * 本文文字列06
         */
    @TableField("tnc_ctext06")
        private String tncCtext06;

        /**
         * 本文文字列07
         */
    @TableField("tnc_ctext07")
        private String tncCtext07;

        /**
         * 本文文字列08
         */
    @TableField("tnc_ctext08")
        private String tncCtext08;

        /**
         * 本文文字列09
         */
    @TableField("tnc_ctext09")
        private String tncCtext09;

        /**
         * 本文文字列10
         */
    @TableField("tnc_ctext10")
        private String tncCtext10;

        /**
         * 抽出元table
         */
    @TableField("tnc_ctablename")
        private String tncCtablename;

        /**
         * メール定義id
         */
    @TableField("tnc_cmailid")
        private String tncCmailid;

        /**
         * 承認者職員番号
         */
    @TableField("tnc_cconsentemployeeid")
        private String tncCconsentemployeeid;

        /**
         * 組織コード
         */
    @TableField("tnc_csectionid")
        private String tncCsectionid;

        /**
         * メールアドレス
         */
    @TableField("tnc_cmailaddress")
        private String tncCmailaddress;


        }