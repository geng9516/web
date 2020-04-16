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
 * [勤怠]入社取消用バックアップ・日別情報詳細      2007/02/23 予定実績を再統合。また、申請番号のカラ
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
@TableName("tmg_bak_daily_detail")
public class TmgBakDailyDetailDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 作成日
         */
    @TableField("tdad_dcreate")
        private Date tdadDcreate;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tdad_ccustomerid")
        private String tdadCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tdad_ccompanyid")
        private String tdadCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tdad_cemployeeid")
        private String tdadCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tdad_dstartdate")
        private Date tdadDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tdad_denddate")
        private Date tdadDenddate;

        /**
         * 更新者
         */
    @TableField("tdad_cmodifieruserid")
        private String tdadCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tdad_dmodifieddate")
        private Date tdadDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tdad_cmodifierprogramid")
        private String tdadCmodifierprogramid;

        /**
         * 該当年                           yyyy
         */
    @TableField("tdad_nyyyy")
        private Long tdadNyyyy;

        /**
         * 該当年月                          yyyy/mm/01
         */
    @TableField("tdad_dyyyymm")
        private Date tdadDyyyymm;

        /**
         * 該当年月日                         yyyy/mm/dd
         */
    @TableField("tdad_dyyyymmdd")
        private Date tdadDyyyymmdd;

        /**
         * 非勤務区分                         mgd:tmg_notwork
         */
    @TableField("tdad_cnotworkid")
        private String tdadCnotworkid;

        /**
         * 非勤務名称
         */
    @TableField("tdad_cnotworkname")
        private String tdadCnotworkname;

        /**
         * 非勤務開始時刻
         */
    @TableField("tdad_nopen")
        private Long tdadNopen;

        /**
         * 非勤務終了時刻
         */
    @TableField("tdad_nclose")
        private Long tdadNclose;

        /**
         * 削除フラグ                         0：有効、1：削除済
         */
    @TableField("tdad_ndeleteflg")
        private Long tdadNdeleteflg;

        /**
         * seq
         */
    @TableField("tdad_seq")
        private Long tdadSeq;

        /**
         * 予備文字列1
         */
    @TableField("tdad_csparechar1")
        private String tdadCsparechar1;

        /**
         * 予備文字列2
         */
    @TableField("tdad_csparechar2")
        private String tdadCsparechar2;

        /**
         * 予備文字列3
         */
    @TableField("tdad_csparechar3")
        private String tdadCsparechar3;

        /**
         * 予備文字列4
         */
    @TableField("tdad_csparechar4")
        private String tdadCsparechar4;

        /**
         * 予備文字列5
         */
    @TableField("tdad_csparechar5")
        private String tdadCsparechar5;

        /**
         * 予備数値1
         */
    @TableField("tdad_nsparenum1")
        private Long tdadNsparenum1;

        /**
         * 予備数値2
         */
    @TableField("tdad_nsparenum2")
        private Long tdadNsparenum2;

        /**
         * 予備数値3
         */
    @TableField("tdad_nsparenum3")
        private Long tdadNsparenum3;

        /**
         * 予備数値4
         */
    @TableField("tdad_nsparenum4")
        private Long tdadNsparenum4;

        /**
         * 予備数値5
         */
    @TableField("tdad_nsparenum5")
        private Long tdadNsparenum5;

        /**
         * 予備日付1
         */
    @TableField("tdad_dsparedate1")
        private Date tdadDsparedate1;

        /**
         * 予備日付2
         */
    @TableField("tdad_dsparedate2")
        private Date tdadDsparedate2;

        /**
         * 予備日付3
         */
    @TableField("tdad_dsparedate3")
        private Date tdadDsparedate3;

        /**
         * 予備日付4
         */
    @TableField("tdad_dsparedate4")
        private Date tdadDsparedate4;

        /**
         * 予備日付5
         */
    @TableField("tdad_dsparedate5")
        private Date tdadDsparedate5;

        /**
         * 予備コード1
         */
    @TableField("tdad_ccode01")
        private String tdadCcode01;

        /**
         * 予備コード1コード
         */
    @TableField("tdad_ccodenm01")
        private String tdadCcodenm01;

        /**
         * 予備コード2
         */
    @TableField("tdad_ccode02")
        private String tdadCcode02;

        /**
         * 予備コード2コード
         */
    @TableField("tdad_ccodenm02")
        private String tdadCcodenm02;

        /**
         * 予備コード3
         */
    @TableField("tdad_ccode03")
        private String tdadCcode03;

        /**
         * 予備コード3コード
         */
    @TableField("tdad_ccodenm03")
        private String tdadCcodenm03;

        /**
         * 予備コード4
         */
    @TableField("tdad_ccode04")
        private String tdadCcode04;

        /**
         * 予備コード4コード
         */
    @TableField("tdad_ccodenm04")
        private String tdadCcodenm04;

        /**
         * 予備コード5
         */
    @TableField("tdad_ccode05")
        private String tdadCcode05;

        /**
         * 予備コード5コード
         */
    @TableField("tdad_ccodenm05")
        private String tdadCcodenm05;

        /**
         * 申請反映元申請番号
         */
    @TableField("tdad_crefntfno")
        private String tdadCrefntfno;


        }