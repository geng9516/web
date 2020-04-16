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
 * プラガブル情報マスタ
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
@TableName("tmg_pluggable")
public class TmgPluggableDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
                @TableId(value = "tpl_ccustomerid", type = IdType.AUTO)
                private String tplCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tpl_ccompanyid")
        private String tplCcompanyid;

        /**
         * 勤怠種別
         */
    @TableField("tpl_cworktypeid")
        private String tplCworktypeid;

        /**
         * 処理フェーズ
         */
    @TableField("tpl_cphase")
        private String tplCphase;

        /**
         * 並び順
         */
    @TableField("tpl_nseq")
        private Long tplNseq;

        /**
         * データ開始日
         */
    @TableField("tpl_dstartdate")
        private Date tplDstartdate;

        /**
         * データ終了日
         */
    @TableField("tpl_denddate")
        private Date tplDenddate;

        /**
         * 更新者
         */
    @TableField("tpl_cmodifieruserid")
        private String tplCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tpl_dmodifieddate")
        private Date tplDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tpl_cmodifierprogramid")
        private String tplCmodifierprogramid;

        /**
         * sql種別
         */
    @TableField("tpl_csqltype")
        private String tplCsqltype;

        /**
         * sql
         */
    @TableField("tpl_csql")
        private String tplCsql;

        /**
         * 使用可否
         */
    @TableField("tpl_cuseflg")
        private String tplCuseflg;

        /**
         * 予備文字1
         */
    @TableField("tpl_csparechar01")
        private String tplCsparechar01;

        /**
         * 予備文字2
         */
    @TableField("tpl_csparechar02")
        private String tplCsparechar02;

        /**
         * 予備文字3
         */
    @TableField("tpl_csparechar03")
        private String tplCsparechar03;

        /**
         * 予備文字4
         */
    @TableField("tpl_csparechar04")
        private String tplCsparechar04;

        /**
         * 予備文字5
         */
    @TableField("tpl_csparechar05")
        private String tplCsparechar05;

        /**
         * 予備文字6
         */
    @TableField("tpl_csparechar06")
        private String tplCsparechar06;

        /**
         * 予備文字7
         */
    @TableField("tpl_csparechar07")
        private String tplCsparechar07;

        /**
         * 予備文字8
         */
    @TableField("tpl_csparechar08")
        private String tplCsparechar08;

        /**
         * 予備文字9
         */
    @TableField("tpl_csparechar09")
        private String tplCsparechar09;

        /**
         * 予備文字10
         */
    @TableField("tpl_csparechar10")
        private String tplCsparechar10;

        /**
         * 予備数字1
         */
    @TableField("tpl_nsparenum01")
        private Long tplNsparenum01;

        /**
         * 予備数字2
         */
    @TableField("tpl_nsparenum02")
        private Long tplNsparenum02;

        /**
         * 予備数字3
         */
    @TableField("tpl_nsparenum03")
        private Long tplNsparenum03;

        /**
         * 予備数字4
         */
    @TableField("tpl_nsparenum04")
        private Long tplNsparenum04;

        /**
         * 予備数字5
         */
    @TableField("tpl_nsparenum05")
        private Long tplNsparenum05;

        /**
         * 予備数字6
         */
    @TableField("tpl_nsparenum06")
        private Long tplNsparenum06;

        /**
         * 予備数字7
         */
    @TableField("tpl_nsparenum07")
        private Long tplNsparenum07;

        /**
         * 予備数字8
         */
    @TableField("tpl_nsparenum08")
        private Long tplNsparenum08;

        /**
         * 予備数字9
         */
    @TableField("tpl_nsparenum09")
        private Long tplNsparenum09;

        /**
         * 予備数字10
         */
    @TableField("tpl_nsparenum10")
        private Long tplNsparenum10;

        /**
         * 予備日付1
         */
    @TableField("tpl_dsparedate01")
        private Date tplDsparedate01;

        /**
         * 予備日付2
         */
    @TableField("tpl_dsparedate02")
        private Date tplDsparedate02;

        /**
         * 予備日付3
         */
    @TableField("tpl_dsparedate03")
        private Date tplDsparedate03;

        /**
         * 予備日付4
         */
    @TableField("tpl_dsparedate04")
        private Date tplDsparedate04;

        /**
         * 予備日付5
         */
    @TableField("tpl_dsparedate05")
        private Date tplDsparedate05;

        /**
         * 予備日付6
         */
    @TableField("tpl_dsparedate06")
        private Date tplDsparedate06;

        /**
         * 予備日付7
         */
    @TableField("tpl_dsparedate07")
        private Date tplDsparedate07;

        /**
         * 予備日付8
         */
    @TableField("tpl_dsparedate08")
        private Date tplDsparedate08;

        /**
         * 予備日付9
         */
    @TableField("tpl_dsparedate09")
        private Date tplDsparedate09;

        /**
         * 予備日付10
         */
    @TableField("tpl_dsparedate10")
        private Date tplDsparedate10;

        /**
         * ファイル名
         */
    @TableField("tpl_filepath")
        private String tplFilepath;


        }