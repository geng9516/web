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
 * (非職)勤務予定情報
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
@TableName("dhjkinmu")
public class DhjkinmuDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 会社区分
         */
                @TableId(value = "ccompkb", type = IdType.AUTO)
                private String ccompkb;

        /**
         * 職員番号
         */
    @TableField("cshainno")
        private String cshainno;

        /**
         * カナ氏名
         */
    @TableField("cnamekna")
        private String cnamekna;

        /**
         * 漢字氏名
         */
    @TableField("cnameknj")
        private String cnameknj;

        /**
         * 任用年月日
         */
    @TableField("ninyo_dte")
        private String ninyoDte;

        /**
         * 任用年月日（西暦）
         */
    @TableField("dninyo_dte")
        private Date dninyoDte;

        /**
         * 任期満了年月日
         */
    @TableField("nnki_mr_dte")
        private String nnkiMrDte;

        /**
         * 任期満了年月日（西暦）
         */
    @TableField("dnnki_mr_dte")
        private Date dnnkiMrDte;

        /**
         * 所属コード
         */
    @TableField("szk_cde")
        private String szkCde;

        /**
         * 所属
         */
    @TableField("szk_nme")
        private String szkNme;

        /**
         * 部局コード
         */
    @TableField("bkyk_cde")
        private String bkykCde;

        /**
         * 部局
         */
    @TableField("bkyk_nme")
        private String bkykNme;

        /**
         * ソート順
         */
    @TableField("sort_jyn")
        private Long sortJyn;

        /**
         * 定期／不定期区分
         */
    @TableField("teiki_cde")
        private String teikiCde;

        /**
         * 定期／不定期名称
         */
    @TableField("teiki_nme")
        private String teikiNme;

        /**
         * 勤務時間帯区分
         */
    @TableField("jikan_cde")
        private String jikanCde;

        /**
         * 勤務時間帯名称
         */
    @TableField("jikan_nme")
        private String jikanNme;

        /**
         * 勤務時間(月)
         */
    @TableField("num_w1")
        private Long numW1;

        /**
         * 勤務時間(火)
         */
    @TableField("num_w2")
        private Long numW2;

        /**
         * 勤務時間(水)
         */
    @TableField("num_w3")
        private Long numW3;

        /**
         * 勤務時間(木)
         */
    @TableField("num_w4")
        private Long numW4;

        /**
         * 勤務時間(金)
         */
    @TableField("num_w5")
        private Long numW5;

        /**
         * 勤務時間(土)
         */
    @TableField("num_w6")
        private Long numW6;

        /**
         * 勤務時間(日)
         */
    @TableField("num_w7")
        private Long numW7;

        /**
         * 勤務時間1                         以下、最大５行分
         */
    @TableField("kinmu_num1")
        private Long kinmuNum1;

        /**
         * 勤務開始時刻11
         */
    @TableField("kinmu_start11")
        private String kinmuStart11;

        /**
         * 勤務終了時刻11
         */
    @TableField("kinmu_end11")
        private String kinmuEnd11;

        /**
         * 勤務開始時刻12
         */
    @TableField("kinmu_start12")
        private String kinmuStart12;

        /**
         * 勤務終了時刻12
         */
    @TableField("kinmu_end12")
        private String kinmuEnd12;

        /**
         * 休憩開始時刻1
         */
    @TableField("kyukei_start1")
        private String kyukeiStart1;

        /**
         * 休憩終了時刻1
         */
    @TableField("kyukei_end1")
        private String kyukeiEnd1;

        /**
         * 曜日1                           各桁で月～日とし、対象曜日"1
         */
    @TableField("yobi1")
        private String yobi1;

        /**
         * 勤務時間2
         */
    @TableField("kinmu_num2")
        private Long kinmuNum2;

        /**
         * 勤務開始時刻21
         */
    @TableField("kinmu_start21")
        private String kinmuStart21;

        /**
         * 勤務終了時刻21
         */
    @TableField("kinmu_end21")
        private String kinmuEnd21;

        /**
         * 勤務開始時刻22
         */
    @TableField("kinmu_start22")
        private String kinmuStart22;

        /**
         * 勤務終了時刻22
         */
    @TableField("kinmu_end22")
        private String kinmuEnd22;

        /**
         * 休憩開始時刻2
         */
    @TableField("kyukei_start2")
        private String kyukeiStart2;

        /**
         * 休憩終了時刻2
         */
    @TableField("kyukei_end2")
        private String kyukeiEnd2;

        /**
         * 曜日2                           各桁で月～日とし、対象曜日"1
         */
    @TableField("yobi2")
        private String yobi2;

        /**
         * 勤務時間3
         */
    @TableField("kinmu_num3")
        private Long kinmuNum3;

        /**
         * 勤務開始時刻31
         */
    @TableField("kinmu_start31")
        private String kinmuStart31;

        /**
         * 勤務終了時刻31
         */
    @TableField("kinmu_end31")
        private String kinmuEnd31;

        /**
         * 勤務開始時刻32
         */
    @TableField("kinmu_start32")
        private String kinmuStart32;

        /**
         * 勤務終了時刻32
         */
    @TableField("kinmu_end32")
        private String kinmuEnd32;

        /**
         * 休憩開始時刻3
         */
    @TableField("kyukei_start3")
        private String kyukeiStart3;

        /**
         * 休憩終了時刻3
         */
    @TableField("kyukei_end3")
        private String kyukeiEnd3;

        /**
         * 曜日3                           各桁で月～日とし、対象曜日"1
         */
    @TableField("yobi3")
        private String yobi3;

        /**
         * 勤務時間4
         */
    @TableField("kinmu_num4")
        private Long kinmuNum4;

        /**
         * 勤務開始時刻41
         */
    @TableField("kinmu_start41")
        private String kinmuStart41;

        /**
         * 勤務終了時刻41
         */
    @TableField("kinmu_end41")
        private String kinmuEnd41;

        /**
         * 勤務開始時刻42
         */
    @TableField("kinmu_start42")
        private String kinmuStart42;

        /**
         * 勤務終了時刻42
         */
    @TableField("kinmu_end42")
        private String kinmuEnd42;

        /**
         * 休憩開始時刻4
         */
    @TableField("kyukei_start4")
        private String kyukeiStart4;

        /**
         * 休憩終了時刻4
         */
    @TableField("kyukei_end4")
        private String kyukeiEnd4;

        /**
         * 曜日4                           各桁で月～日とし、対象曜日"1
         */
    @TableField("yobi4")
        private String yobi4;

        /**
         * 勤務時間5
         */
    @TableField("kinmu_num5")
        private Long kinmuNum5;

        /**
         * 勤務開始時刻51
         */
    @TableField("kinmu_start51")
        private String kinmuStart51;

        /**
         * 勤務終了時刻51
         */
    @TableField("kinmu_end51")
        private String kinmuEnd51;

        /**
         * 勤務開始時刻52
         */
    @TableField("kinmu_start52")
        private String kinmuStart52;

        /**
         * 勤務終了時刻52
         */
    @TableField("kinmu_end52")
        private String kinmuEnd52;

        /**
         * 休憩開始時刻5
         */
    @TableField("kyukei_start5")
        private String kyukeiStart5;

        /**
         * 休憩終了時刻5
         */
    @TableField("kyukei_end5")
        private String kyukeiEnd5;

        /**
         * 曜日5                           各桁で月～日とし、対象曜日"1
         */
    @TableField("yobi5")
        private String yobi5;

        /**
         * 週勤務時間数
         */
    @TableField("num_week")
        private Long numWeek;

        /**
         * 年勤務日数
         */
    @TableField("days_year")
        private Long daysYear;

        /**
         * 年勤務時間数
         */
    @TableField("num_year")
        private Long numYear;

        /**
         * 更新pgm
         */
    @TableField("last_pgm")
        private String lastPgm;

        /**
         * ﾕｰｻﾞｰ
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新日
         */
    @TableField("dmndate")
        private Date dmndate;


        }