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
 * [勤怠]日別詳細情報変更ログ
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
@TableName("tmg_daily_detail_changelog")
public class TmgDailyDetailChangelogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 更新id
         */
    @TableId(value="tddcl_nid",type = IdType.INPUT)
        private Long tddclNid;

        /**
         * 前後区分                          ( 0：前 1：後)
         */
    @TableField("tddcl_nifbeforeorafter")
        private Long tddclNifbeforeorafter;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tddcl_ccustomerid")
        private String tddclCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tddcl_ccompanyid")
        private String tddclCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tddcl_cemployeeid")
        private String tddclCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tddcl_dstartdate")
        private Date tddclDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tddcl_denddate")
        private Date tddclDenddate;

        /**
         * 更新者
         */
    @TableField("tddcl_cmodifieruserid")
        private String tddclCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tddcl_dmodifieddate")
        private Date tddclDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tddcl_cmodifierprogramid")
        private String tddclCmodifierprogramid;

        /**
         * 該当年月日                         yyyy/mm/dd
         */
    @TableField("tddcl_dyyyymmdd")
        private Date tddclDyyyymmdd;

        /**
         * 非勤務区分                         mgd:tmg_notwork
         */
    @TableField("tddcl_cnotworkid")
        private String tddclCnotworkid;

        /**
         * 非勤務開始時刻
         */
    @TableField("tddcl_nopen")
        private Long tddclNopen;

        /**
         * 非勤務終了時刻
         */
    @TableField("tddcl_nclose")
        private Long tddclNclose;

        /**
         * 予備文字列1
         */
    @TableField("tddcl_csparechar1")
        private String tddclCsparechar1;

        /**
         * 予備文字列2
         */
    @TableField("tddcl_csparechar2")
        private String tddclCsparechar2;

        /**
         * 予備文字列3
         */
    @TableField("tddcl_csparechar3")
        private String tddclCsparechar3;

        /**
         * 予備文字列4
         */
    @TableField("tddcl_csparechar4")
        private String tddclCsparechar4;

        /**
         * 予備文字列5
         */
    @TableField("tddcl_csparechar5")
        private String tddclCsparechar5;

        /**
         * 予備数値1
         */
    @TableField("tddcl_nsparenum1")
        private Long tddclNsparenum1;

        /**
         * 予備数値2
         */
    @TableField("tddcl_nsparenum2")
        private Long tddclNsparenum2;

        /**
         * 予備数値3
         */
    @TableField("tddcl_nsparenum3")
        private Long tddclNsparenum3;

        /**
         * 予備数値4
         */
    @TableField("tddcl_nsparenum4")
        private Long tddclNsparenum4;

        /**
         * 予備数値5
         */
    @TableField("tddcl_nsparenum5")
        private Long tddclNsparenum5;

        /**
         * 予備日付1
         */
    @TableField("tddcl_dsparedate1")
        private Date tddclDsparedate1;

        /**
         * 予備日付2
         */
    @TableField("tddcl_dsparedate2")
        private Date tddclDsparedate2;

        /**
         * 予備日付3
         */
    @TableField("tddcl_dsparedate3")
        private Date tddclDsparedate3;

        /**
         * 予備日付4
         */
    @TableField("tddcl_dsparedate4")
        private Date tddclDsparedate4;

        /**
         * 予備日付5
         */
    @TableField("tddcl_dsparedate5")
        private Date tddclDsparedate5;

        /**
         * 予備コード1
         */
    @TableField("tddcl_ccode01")
        private String tddclCcode01;

        /**
         * 予備コード2
         */
    @TableField("tddcl_ccode02")
        private String tddclCcode02;

        /**
         * 予備コード3
         */
    @TableField("tddcl_ccode03")
        private String tddclCcode03;

        /**
         * 予備コード4
         */
    @TableField("tddcl_ccode04")
        private String tddclCcode04;

        /**
         * 予備コード5
         */
    @TableField("tddcl_ccode05")
        private String tddclCcode05;


        }