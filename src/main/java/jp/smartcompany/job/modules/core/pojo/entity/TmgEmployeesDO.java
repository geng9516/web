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
 * [勤怠]基本情報
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
@TableName("tmg_employees")
public class TmgEmployeesDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ     固定：01
         */
    @TableField("tem_ccustomerid")
        private String temCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tem_ccompanyid")
        private String temCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tem_cemployeeid", type = IdType.AUTO)
                private String temCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tem_dstartdate")
        private Date temDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tem_denddate")
        private Date temDenddate;

        /**
         * 更新者
         */
    @TableField("tem_cmodifieruserid")
        private String temCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tem_dmodifieddate")
        private Date temDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tem_cmodifierprogramid")
        private String temCmodifierprogramid;

        /**
         * 勤怠種別     mgd:tmg_worktype
         */
    @TableField("tem_cworktypeid")
        private String temCworktypeid;

        /**
         * 勤怠種別名称
         */
    @TableField("tem_cworktypename")
        private String temCworktypename;

        /**
         * 予備文字列１
         */
    @TableField("tem_csparechar1")
        private String temCsparechar1;

        /**
         * 予備文字列２
         */
    @TableField("tem_csparechar2")
        private String temCsparechar2;

        /**
         * 予備文字列３
         */
    @TableField("tem_csparechar3")
        private String temCsparechar3;

        /**
         * 予備文字列４
         */
    @TableField("tem_csparechar4")
        private String temCsparechar4;

        /**
         * 予備文字列５
         */
    @TableField("tem_csparechar5")
        private String temCsparechar5;

        /**
         * 予備数値１
         */
    @TableField("tem_nsparenum1")
        private Long temNsparenum1;

        /**
         * 予備数値２
         */
    @TableField("tem_nsparenum2")
        private Long temNsparenum2;

        /**
         * 予備数値３
         */
    @TableField("tem_nsparenum3")
        private Long temNsparenum3;

        /**
         * 予備数値４
         */
    @TableField("tem_nsparenum4")
        private Long temNsparenum4;

        /**
         * 予備数値５
         */
    @TableField("tem_nsparenum5")
        private Long temNsparenum5;

        /**
         * 予備日付１
         */
    @TableField("tem_dsparedate1")
        private Date temDsparedate1;

        /**
         * 予備日付２
         */
    @TableField("tem_dsparedate2")
        private Date temDsparedate2;

        /**
         * 予備日付３
         */
    @TableField("tem_dsparedate3")
        private Date temDsparedate3;

        /**
         * 予備日付４
         */
    @TableField("tem_dsparedate4")
        private Date temDsparedate4;

        /**
         * 予備日付５
         */
    @TableField("tem_dsparedate5")
        private Date temDsparedate5;

        /**
         * 管理対象者フラグ
         */
    @TableField("tem_cmanageflg")
        private String temCmanageflg;

        /**
         * 日直対象フラグ
         */
    @TableField("tem_cday_duty_flg")
        private String temCdayDutyFlg;

        /**
         * 宿直対象フラグ
         */
    @TableField("tem_cnight_duty_flg")
        private String temCnightDutyFlg;


        }