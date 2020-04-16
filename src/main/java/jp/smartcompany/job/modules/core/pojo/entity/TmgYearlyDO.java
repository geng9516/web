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
 * [勤怠]年別情報
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
@TableName("tmg_yearly")
public class TmgYearlyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmy_ccustomerid")
        private String tmyCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmy_ccompanyid")
        private String tmyCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tmy_cemployeeid", type = IdType.AUTO)
                private String tmyCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tmy_dstartdate")
        private Date tmyDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tmy_denddate")
        private Date tmyDenddate;

        /**
         * 更新者
         */
    @TableField("tmy_cmodifieruserid")
        private String tmyCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmy_dmodifieddate")
        private Date tmyDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmy_cmodifierprogramid")
        private String tmyCmodifierprogramid;

        /**
         * 該当年                           yyyy
         */
    @TableField("tmy_nyyyy")
        private Long tmyNyyyy;

        /**
         * 摘要欄
         */
    @TableField("tmy_comment")
        private String tmyComment;

        /**
         * 予備文字列１
         */
    @TableField("tmy_csparechar01")
        private String tmyCsparechar01;

        /**
         * 予備文字列２
         */
    @TableField("tmy_csparechar02")
        private String tmyCsparechar02;

        /**
         * 予備文字列３
         */
    @TableField("tmy_csparechar03")
        private String tmyCsparechar03;

        /**
         * 予備文字列４
         */
    @TableField("tmy_csparechar04")
        private String tmyCsparechar04;

        /**
         * 予備文字列５
         */
    @TableField("tmy_csparechar05")
        private String tmyCsparechar05;

        /**
         * 予備文字列６
         */
    @TableField("tmy_csparechar06")
        private String tmyCsparechar06;

        /**
         * 予備文字列７
         */
    @TableField("tmy_csparechar07")
        private String tmyCsparechar07;

        /**
         * 予備文字列８
         */
    @TableField("tmy_csparechar08")
        private String tmyCsparechar08;

        /**
         * 予備文字列９
         */
    @TableField("tmy_csparechar09")
        private String tmyCsparechar09;

        /**
         * 予備文字列１０
         */
    @TableField("tmy_csparechar10")
        private String tmyCsparechar10;

        /**
         * 予備数値１
         */
    @TableField("tmy_nsparenum01")
        private Long tmyNsparenum01;

        /**
         * 予備数値２
         */
    @TableField("tmy_nsparenum02")
        private Long tmyNsparenum02;

        /**
         * 予備数値３
         */
    @TableField("tmy_nsparenum03")
        private Long tmyNsparenum03;

        /**
         * 予備数値４
         */
    @TableField("tmy_nsparenum04")
        private Long tmyNsparenum04;

        /**
         * 予備数値５
         */
    @TableField("tmy_nsparenum05")
        private Long tmyNsparenum05;

        /**
         * 予備数値６
         */
    @TableField("tmy_nsparenum06")
        private Long tmyNsparenum06;

        /**
         * 予備数値７
         */
    @TableField("tmy_nsparenum07")
        private Long tmyNsparenum07;

        /**
         * 予備数値８
         */
    @TableField("tmy_nsparenum08")
        private Long tmyNsparenum08;

        /**
         * 予備数値９
         */
    @TableField("tmy_nsparenum09")
        private Long tmyNsparenum09;

        /**
         * 予備数値１０
         */
    @TableField("tmy_nsparenum10")
        private Long tmyNsparenum10;


        }