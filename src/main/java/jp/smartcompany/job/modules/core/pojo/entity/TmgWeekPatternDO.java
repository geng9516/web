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
 * [勤怠]週次勤務パターンテーブル(エラーチェック用)
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
@TableName("tmg_week_pattern")
public class TmgWeekPatternDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id
         */
    @TableField("twp_nid")
        private Long twpNid;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("twp_ccustomerid")
        private String twpCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("twp_ccompanyid")
        private String twpCcompanyid;

        /**
         * 社員番号
         */
    @TableField("twp_cemployeeid")
        private String twpCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("twp_dstartdate")
        private Date twpDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("twp_denddate")
        private Date twpDenddate;

        /**
         * 更新者
         */
    @TableField("twp_cmodifieruserid")
        private String twpCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("twp_dmodifieddate")
        private Date twpDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("twp_cmodifierprogramid")
        private String twpCmodifierprogramid;

        /**
         * 勤務開始時刻(日曜)hh:mi               休日はnull指定
         */
    @TableField("twp_copen1")
        private String twpCopen1;

        /**
         * 勤務終了時刻(日曜)hh:mi               休日はnull指定
         */
    @TableField("twp_cclose1")
        private String twpCclose1;

        /**
         * 休憩開始時刻(日曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestopen1")
        private String twpCrestopen1;

        /**
         * 休憩終了時刻(日曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestclose1")
        private String twpCrestclose1;

        /**
         * 休日フラグ1
         */
    @TableField("twp_cholflg1")
        private String twpCholflg1;

        /**
         * 勤務開始時刻(月曜)hh:mi               休日はnull指定
         */
    @TableField("twp_copen2")
        private String twpCopen2;

        /**
         * 勤務終了時刻(月曜)hh:mi               休日はnull指定
         */
    @TableField("twp_cclose2")
        private String twpCclose2;

        /**
         * 休憩開始時刻(月曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestopen2")
        private String twpCrestopen2;

        /**
         * 休憩終了時刻(月曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestclose2")
        private String twpCrestclose2;

        /**
         * 休日フラグ2
         */
    @TableField("twp_cholflg2")
        private String twpCholflg2;

        /**
         * 勤務開始時刻(火曜)hh:mi               休日はnull指定
         */
    @TableField("twp_copen3")
        private String twpCopen3;

        /**
         * 勤務終了時刻(火曜)hh:mi               休日はnull指定
         */
    @TableField("twp_cclose3")
        private String twpCclose3;

        /**
         * 休憩開始時刻(火曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestopen3")
        private String twpCrestopen3;

        /**
         * 休憩終了時刻(火曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestclose3")
        private String twpCrestclose3;

        /**
         * 休日フラグ3
         */
    @TableField("twp_cholflg3")
        private String twpCholflg3;

        /**
         * 勤務開始時刻(水曜)hh:mi               休日はnull指定
         */
    @TableField("twp_copen4")
        private String twpCopen4;

        /**
         * 勤務終了時刻(水曜)hh:mi               休日はnull指定
         */
    @TableField("twp_cclose4")
        private String twpCclose4;

        /**
         * 休憩開始時刻(水曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestopen4")
        private String twpCrestopen4;

        /**
         * 休憩終了時刻(水曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestclose4")
        private String twpCrestclose4;

        /**
         * 休日フラグ4
         */
    @TableField("twp_cholflg4")
        private String twpCholflg4;

        /**
         * 勤務開始時刻(木曜)hh:mi               休日はnull指定
         */
    @TableField("twp_copen5")
        private String twpCopen5;

        /**
         * 勤務終了時刻(木曜)hh:mi               休日はnull指定
         */
    @TableField("twp_cclose5")
        private String twpCclose5;

        /**
         * 休憩開始時刻(木曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestopen5")
        private String twpCrestopen5;

        /**
         * 休憩終了時刻(木曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestclose5")
        private String twpCrestclose5;

        /**
         * 休日フラグ5
         */
    @TableField("twp_cholflg5")
        private String twpCholflg5;

        /**
         * 勤務開始時刻(金曜)hh:mi               休日はnull指定
         */
    @TableField("twp_copen6")
        private String twpCopen6;

        /**
         * 勤務終了時刻(金曜)hh:mi               休日はnull指定
         */
    @TableField("twp_cclose6")
        private String twpCclose6;

        /**
         * 休憩開始時刻(金曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestopen6")
        private String twpCrestopen6;

        /**
         * 休憩終了時刻(金曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestclose6")
        private String twpCrestclose6;

        /**
         * 休日フラグ6
         */
    @TableField("twp_cholflg6")
        private String twpCholflg6;

        /**
         * 勤務開始時刻(土曜)hh:mi               休日はnull指定
         */
    @TableField("twp_copen7")
        private String twpCopen7;

        /**
         * 勤務終了時刻(土曜)hh:mi               休日はnull指定
         */
    @TableField("twp_cclose7")
        private String twpCclose7;

        /**
         * 休憩開始時刻(土曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestopen7")
        private String twpCrestopen7;

        /**
         * 休憩終了時刻(土曜)hh:mi               休日はnull指定
         */
    @TableField("twp_crestclose7")
        private String twpCrestclose7;

        /**
         * 休日フラグ7
         */
    @TableField("twp_cholflg7")
        private String twpCholflg7;


        }