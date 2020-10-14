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
 * [勤怠]個人別勤務パターン
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
@TableName("tmg_personal_pattern")
public class TmgPersonalPatternDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tppa_ccustomerid")
        private String tppaCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tppa_ccompanyid")
        private String tppaCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "tppa_cemployeeid", type = IdType.AUTO)
                private String tppaCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tppa_dstartdate")
        private Date tppaDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tppa_denddate")
        private Date tppaDenddate;

        /**
         * 更新者
         */
    @TableField("tppa_cmodifieruserid")
        private String tppaCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tppa_dmodifieddate")
        private Date tppaDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tppa_cmodifierprogramid")
        private String tppaCmodifierprogramid;

        /**
         * 週平均勤務時間
         */
    @TableField("tppa_navgworktime")
        private Long tppaNavgworktime;

        /**
         * 予定勤務パターン
         */
    @TableField("tppa_cworkingdays_week")
        private String tppaCworkingdaysWeek;

        /**
         * 勤務コード1
         */
    @TableField("tppa_nkinmu_num1")
        private Long tppaNkinmuNum1;

        /**
         * 勤務開始時刻1 hh:mi → 分数
         */
    @TableField("tppa_nopen1")
        private Long tppaNopen1;

        /**
         * 勤務終了時刻1 hh:mi → 分数
         */
    @TableField("tppa_nclose1")
        private Long tppaNclose1;

        /**
         * 休憩開始時刻1 hh:mi → 分数
         */
    @TableField("tppa_nrestopen1")
        private Long tppaNrestopen1;

        /**
         * 休憩終了時刻1 hh:mi → 分数
         */
    @TableField("tppa_nrestclose1")
        private Long tppaNrestclose1;

        /**
         * 曜日1
         */
    @TableField("tppa_cyobi1")
        private String tppaCyobi1;

        /**
         * 休日フラグ1
         */
    @TableField("tppa_cholflg1")
        private String tppaCholflg1;

        /**
         * 勤務コード2
         */
    @TableField("tppa_nkinmu_num2")
        private Long tppaNkinmuNum2;

        /**
         * 勤務開始時刻2 hh:mi → 分数
         */
    @TableField("tppa_nopen2")
        private Long tppaNopen2;

        /**
         * 勤務終了時刻2 hh:mi → 分数
         */
    @TableField("tppa_nclose2")
        private Long tppaNclose2;

        /**
         * 休憩開始時刻2 hh:mi → 分数
         */
    @TableField("tppa_nrestopen2")
        private Long tppaNrestopen2;

        /**
         * 休憩終了時刻2 hh:mi → 分数
         */
    @TableField("tppa_nrestclose2")
        private Long tppaNrestclose2;

        /**
         * 曜日2
         */
    @TableField("tppa_cyobi2")
        private String tppaCyobi2;

        /**
         * 休日フラグ2
         */
    @TableField("tppa_cholflg2")
        private String tppaCholflg2;

        /**
         * 勤務コード3
         */
    @TableField("tppa_nkinmu_num3")
        private Long tppaNkinmuNum3;

        /**
         * 勤務開始時刻3 hh:mi → 分数
         */
    @TableField("tppa_nopen3")
        private Long tppaNopen3;

        /**
         * 勤務終了時刻3 hh:mi → 分数
         */
    @TableField("tppa_nclose3")
        private Long tppaNclose3;

        /**
         * 休憩開始時刻3 hh:mi → 分数
         */
    @TableField("tppa_nrestopen3")
        private Long tppaNrestopen3;

        /**
         * 休憩終了時刻3 hh:mi → 分数
         */
    @TableField("tppa_nrestclose3")
        private Long tppaNrestclose3;

        /**
         * 曜日3
         */
    @TableField("tppa_cyobi3")
        private String tppaCyobi3;

        /**
         * 休日フラグ3
         */
    @TableField("tppa_cholflg3")
        private String tppaCholflg3;

        /**
         * 勤務コード4
         */
    @TableField("tppa_nkinmu_num4")
        private Long tppaNkinmuNum4;

        /**
         * 勤務開始時刻4 hh:mi → 分数
         */
    @TableField("tppa_nopen4")
        private Long tppaNopen4;

        /**
         * 勤務終了時刻4 hh:mi → 分数
         */
    @TableField("tppa_nclose4")
        private Long tppaNclose4;

        /**
         * 休憩開始時刻4 hh:mi → 分数
         */
    @TableField("tppa_nrestopen4")
        private Long tppaNrestopen4;

        /**
         * 休憩終了時刻4 hh:mi → 分数
         */
    @TableField("tppa_nrestclose4")
        private Long tppaNrestclose4;

        /**
         * 曜日4
         */
    @TableField("tppa_cyobi4")
        private String tppaCyobi4;

        /**
         * 休日フラグ4
         */
    @TableField("tppa_cholflg4")
        private String tppaCholflg4;

        /**
         * 勤務コード5
         */
    @TableField("tppa_nkinmu_num5")
        private Long tppaNkinmuNum5;

        /**
         * 勤務開始時刻5 hh:mi → 分数
         */
    @TableField("tppa_nopen5")
        private Long tppaNopen5;

        /**
         * 勤務終了時刻5 hh:mi → 分数
         */
    @TableField("tppa_nclose5")
        private Long tppaNclose5;

        /**
         * 休憩開始時刻5 hh:mi → 分数
         */
    @TableField("tppa_nrestopen5")
        private Long tppaNrestopen5;

        /**
         * 休憩終了時刻5 hh:mi → 分数
         */
    @TableField("tppa_nrestclose5")
        private Long tppaNrestclose5;

        /**
         * 曜日5
         */
    @TableField("tppa_cyobi5")
        private String tppaCyobi5;

        /**
         * 休日フラグ5
         */
    @TableField("tppa_cholflg5")
        private String tppaCholflg5;

        /**
         * 勤務コード6
         */
    @TableField("tppa_nkinmu_num6")
        private Long tppaNkinmuNum6;

        /**
         * 勤務開始時刻6 hh:mi → 分数
         */
    @TableField("tppa_nopen6")
        private Long tppaNopen6;

        /**
         * 勤務終了時刻6 hh:mi → 分数
         */
    @TableField("tppa_nclose6")
        private Long tppaNclose6;

        /**
         * 休憩開始時刻6 hh:mi → 分数
         */
    @TableField("tppa_nrestopen6")
        private Long tppaNrestopen6;

        /**
         * 休憩終了時刻6 hh:mi → 分数
         */
    @TableField("tppa_nrestclose6")
        private Long tppaNrestclose6;

        /**
         * 曜日6
         */
    @TableField("tppa_cyobi6")
        private String tppaCyobi6;

        /**
         * 休日フラグ6
         */
    @TableField("tppa_cholflg6")
        private String tppaCholflg6;

        /**
         * 勤務コード7
         */
    @TableField("tppa_nkinmu_num7")
        private Long tppaNkinmuNum7;

        /**
         * 勤務開始時刻7 hh:mi → 分数
         */
    @TableField("tppa_nopen7")
        private Long tppaNopen7;

        /**
         * 勤務終了時刻7 hh:mi → 分数
         */
    @TableField("tppa_nclose7")
        private Long tppaNclose7;

        /**
         * 休憩開始時刻7 hh:mi → 分数
         */
    @TableField("tppa_nrestopen7")
        private Long tppaNrestopen7;

        /**
         * 休憩終了時刻7 hh:mi → 分数
         */
    @TableField("tppa_nrestclose7")
        private Long tppaNrestclose7;

        /**
         * 曜日7
         */
    @TableField("tppa_cyobi7")
        private String tppaCyobi7;

        /**
         * 休日フラグ7
         */
    @TableField("tppa_cholflg7")
        private String tppaCholflg7;

        /**
         * 週の所定労働日数
         */
    @TableField("tppa_nworkingdays_week")
        private Long tppaNworkingdaysWeek;


        }