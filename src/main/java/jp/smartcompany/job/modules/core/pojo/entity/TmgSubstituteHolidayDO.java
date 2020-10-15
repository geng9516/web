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
 * [勤怠]代休情報
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
@TableName("tmg_substitute_holiday")
public class TmgSubstituteHolidayDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tsh_ccustomerid")
        private String tshCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tsh_ccompanyid")
        private String tshCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "tsh_cemployeeid", type = IdType.AUTO)
                private String tshCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tsh_dstartdate")
        private Date tshDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tsh_denddate")
        private Date tshDenddate;

        /**
         * 更新者
         */
    @TableField("tsh_cmodifieruserid")
        private String tshCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tsh_dmodifieddate")
        private Date tshDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tsh_cmodifierprogramid")
        private String tshCmodifierprogramid;

        /**
         * 休日出勤日
         */
    @TableField("tsh_dwork")
        private Date tshDwork;

        /**
         * 代休取得日
         */
    @TableField("tsh_ddayoff")
        private Date tshDdayoff;

        /**
         * 消化期限
         */
    @TableField("tsh_ddayofflimit")
        private Date tshDdayofflimit;

        /**
         * 予備数値１
         */
    @TableField("tsh_nsparenum1")
        private Long tshNsparenum1;

        /**
         * 予備数値２
         */
    @TableField("tsh_nsparenum2")
        private Long tshNsparenum2;

        /**
         * 予備数値３
         */
    @TableField("tsh_nsparenum3")
        private Long tshNsparenum3;

        /**
         * 予備数値４
         */
    @TableField("tsh_nsparenum4")
        private Long tshNsparenum4;

        /**
         * 予備数値５
         */
    @TableField("tsh_nsparenum5")
        private Long tshNsparenum5;

        /**
         * 予備日付１
         */
    @TableField("tsh_dsparedate1")
        private Date tshDsparedate1;

        /**
         * 予備日付２
         */
    @TableField("tsh_dsparedate2")
        private Date tshDsparedate2;

        /**
         * 予備日付３
         */
    @TableField("tsh_dsparedate3")
        private Date tshDsparedate3;

        /**
         * 予備日付４
         */
    @TableField("tsh_dsparedate4")
        private Date tshDsparedate4;

        /**
         * 予備日付５
         */
    @TableField("tsh_dsparedate5")
        private Date tshDsparedate5;


        }