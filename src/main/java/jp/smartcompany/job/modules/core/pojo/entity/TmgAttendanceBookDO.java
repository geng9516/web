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
 * [勤怠]出勤簿情報
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
@TableName("tmg_attendance_book")
public class TmgAttendanceBookDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableId(value="tma_ccustomerid",type = IdType.INPUT)
        private String tmaCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tma_ccompanyid")
        private String tmaCcompanyid;

        /**
         * 社員番号
         */
                @TableField(value = "tma_cemployeeid")
                private String tmaCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tma_dstartdate")
        private Date tmaDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tma_denddate")
        private Date tmaDenddate;

        /**
         * 更新者
         */
    @TableField("tma_cmodifieruserid")
        private String tmaCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tma_dmodifieddate")
        private Date tmaDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tma_cmodifierprogramid")
        private String tmaCmodifierprogramid;

        /**
         * 該当年                           yyyy
         */
    @TableField("tma_nyyyy")
        private Long tmaNyyyy;

        /**
         * 該当年月                          yyyy/mm/01
         */
    @TableField("tma_dyyyymm")
        private Date tmaDyyyymm;

        /**
         * 出勤区分：1日
         */
    @TableField("tma_worktypeid01")
        private String tmaWorktypeid01;

        /**
         * 出勤内容：1日
         */
    @TableField("tma_workcontent01")
        private String tmaWorkcontent01;

        /**
         * 出勤区分：2日
         */
    @TableField("tma_worktypeid02")
        private String tmaWorktypeid02;

        /**
         * 出勤内容：2日
         */
    @TableField("tma_workcontent02")
        private String tmaWorkcontent02;

        /**
         * 出勤区分：3日
         */
    @TableField("tma_worktypeid03")
        private String tmaWorktypeid03;

        /**
         * 出勤内容：3日
         */
    @TableField("tma_workcontent03")
        private String tmaWorkcontent03;

        /**
         * 出勤区分：4日
         */
    @TableField("tma_worktypeid04")
        private String tmaWorktypeid04;

        /**
         * 出勤内容：4日
         */
    @TableField("tma_workcontent04")
        private String tmaWorkcontent04;

        /**
         * 出勤区分：5日
         */
    @TableField("tma_worktypeid05")
        private String tmaWorktypeid05;

        /**
         * 出勤内容：5日
         */
    @TableField("tma_workcontent05")
        private String tmaWorkcontent05;

        /**
         * 出勤区分：6日
         */
    @TableField("tma_worktypeid06")
        private String tmaWorktypeid06;

        /**
         * 出勤内容：6日
         */
    @TableField("tma_workcontent06")
        private String tmaWorkcontent06;

        /**
         * 出勤区分：7日
         */
    @TableField("tma_worktypeid07")
        private String tmaWorktypeid07;

        /**
         * 出勤内容：7日
         */
    @TableField("tma_workcontent07")
        private String tmaWorkcontent07;

        /**
         * 出勤区分：8日
         */
    @TableField("tma_worktypeid08")
        private String tmaWorktypeid08;

        /**
         * 出勤内容：8日
         */
    @TableField("tma_workcontent08")
        private String tmaWorkcontent08;

        /**
         * 出勤区分：9日
         */
    @TableField("tma_worktypeid09")
        private String tmaWorktypeid09;

        /**
         * 出勤内容：9日
         */
    @TableField("tma_workcontent09")
        private String tmaWorkcontent09;

        /**
         * 出勤区分：10日
         */
    @TableField("tma_worktypeid10")
        private String tmaWorktypeid10;

        /**
         * 出勤内容：10日
         */
    @TableField("tma_workcontent10")
        private String tmaWorkcontent10;

        /**
         * 出勤区分：11日
         */
    @TableField("tma_worktypeid11")
        private String tmaWorktypeid11;

        /**
         * 出勤内容：11日
         */
    @TableField("tma_workcontent11")
        private String tmaWorkcontent11;

        /**
         * 出勤区分：12日
         */
    @TableField("tma_worktypeid12")
        private String tmaWorktypeid12;

        /**
         * 出勤内容：12日
         */
    @TableField("tma_workcontent12")
        private String tmaWorkcontent12;

        /**
         * 出勤区分：13日
         */
    @TableField("tma_worktypeid13")
        private String tmaWorktypeid13;

        /**
         * 出勤内容：13日
         */
    @TableField("tma_workcontent13")
        private String tmaWorkcontent13;

        /**
         * 出勤区分：14日
         */
    @TableField("tma_worktypeid14")
        private String tmaWorktypeid14;

        /**
         * 出勤内容：14日
         */
    @TableField("tma_workcontent14")
        private String tmaWorkcontent14;

        /**
         * 出勤区分：15日
         */
    @TableField("tma_worktypeid15")
        private String tmaWorktypeid15;

        /**
         * 出勤内容：15日
         */
    @TableField("tma_workcontent15")
        private String tmaWorkcontent15;

        /**
         * 出勤区分：16日
         */
    @TableField("tma_worktypeid16")
        private String tmaWorktypeid16;

        /**
         * 出勤内容：16日
         */
    @TableField("tma_workcontent16")
        private String tmaWorkcontent16;

        /**
         * 出勤区分：17日
         */
    @TableField("tma_worktypeid17")
        private String tmaWorktypeid17;

        /**
         * 出勤内容：17日
         */
    @TableField("tma_workcontent17")
        private String tmaWorkcontent17;

        /**
         * 出勤区分：18日
         */
    @TableField("tma_worktypeid18")
        private String tmaWorktypeid18;

        /**
         * 出勤内容：18日
         */
    @TableField("tma_workcontent18")
        private String tmaWorkcontent18;

        /**
         * 出勤区分：19日
         */
    @TableField("tma_worktypeid19")
        private String tmaWorktypeid19;

        /**
         * 出勤内容：19日
         */
    @TableField("tma_workcontent19")
        private String tmaWorkcontent19;

        /**
         * 出勤区分：20日
         */
    @TableField("tma_worktypeid20")
        private String tmaWorktypeid20;

        /**
         * 出勤内容：20日
         */
    @TableField("tma_workcontent20")
        private String tmaWorkcontent20;

        /**
         * 出勤区分：21日
         */
    @TableField("tma_worktypeid21")
        private String tmaWorktypeid21;

        /**
         * 出勤内容：21日
         */
    @TableField("tma_workcontent21")
        private String tmaWorkcontent21;

        /**
         * 出勤区分：22日
         */
    @TableField("tma_worktypeid22")
        private String tmaWorktypeid22;

        /**
         * 出勤内容：22日
         */
    @TableField("tma_workcontent22")
        private String tmaWorkcontent22;

        /**
         * 出勤区分：23日
         */
    @TableField("tma_worktypeid23")
        private String tmaWorktypeid23;

        /**
         * 出勤内容：23日
         */
    @TableField("tma_workcontent23")
        private String tmaWorkcontent23;

        /**
         * 出勤区分：24日
         */
    @TableField("tma_worktypeid24")
        private String tmaWorktypeid24;

        /**
         * 出勤内容：24日
         */
    @TableField("tma_workcontent24")
        private String tmaWorkcontent24;

        /**
         * 出勤区分：25日
         */
    @TableField("tma_worktypeid25")
        private String tmaWorktypeid25;

        /**
         * 出勤内容：25日
         */
    @TableField("tma_workcontent25")
        private String tmaWorkcontent25;

        /**
         * 出勤区分：26日
         */
    @TableField("tma_worktypeid26")
        private String tmaWorktypeid26;

        /**
         * 出勤内容：26日
         */
    @TableField("tma_workcontent26")
        private String tmaWorkcontent26;

        /**
         * 出勤区分：27日
         */
    @TableField("tma_worktypeid27")
        private String tmaWorktypeid27;

        /**
         * 出勤内容：27日
         */
    @TableField("tma_workcontent27")
        private String tmaWorkcontent27;

        /**
         * 出勤区分：28日
         */
    @TableField("tma_worktypeid28")
        private String tmaWorktypeid28;

        /**
         * 出勤内容：28日
         */
    @TableField("tma_workcontent28")
        private String tmaWorkcontent28;

        /**
         * 出勤区分：29日
         */
    @TableField("tma_worktypeid29")
        private String tmaWorktypeid29;

        /**
         * 出勤内容：29日
         */
    @TableField("tma_workcontent29")
        private String tmaWorkcontent29;

        /**
         * 出勤区分：30日
         */
    @TableField("tma_worktypeid30")
        private String tmaWorktypeid30;

        /**
         * 出勤内容：30日
         */
    @TableField("tma_workcontent30")
        private String tmaWorkcontent30;

        /**
         * 出勤区分：31日
         */
    @TableField("tma_worktypeid31")
        private String tmaWorktypeid31;

        /**
         * 出勤内容：31日
         */
    @TableField("tma_workcontent31")
        private String tmaWorkcontent31;

        /**
         * 年休取得日数
         */
    @TableField("tma_npaid_used_days")
        private Long tmaNpaidUsedDays;

        /**
         * 年休残日数
         */
    @TableField("tma_npaid_rest_days")
        private Long tmaNpaidRestDays;

        /**
         * 病休取得日数
         */
    @TableField("tma_nsichness_used_days")
        private Long tmaNsichnessUsedDays;

        /**
         * 特休取得日数
         */
    @TableField("tma_nspecial_used_days")
        private Long tmaNspecialUsedDays;

        /**
         * 年休取得時間
         */
    @TableField("tma_npaid_used_hours")
        private Long tmaNpaidUsedHours;

        /**
         * 年休残時間
         */
    @TableField("tma_npaid_rest_hours")
        private Long tmaNpaidRestHours;

        /**
         * 病休取得時間数
         */
    @TableField("tma_nsichness_used_hours")
        private Long tmaNsichnessUsedHours;

        /**
         * 特休取得時間数
         */
    @TableField("tma_nspecial_used_hours")
        private Long tmaNspecialUsedHours;

        /**
         * 欠勤日数
         */
    @TableField("tma_nabsence_days")
        private Long tmaNabsenceDays;

        /**
         * 職専免(無給)取得日数
         */
    @TableField("tma_nshoku_used_days")
        private Long tmaNshokuUsedDays;

        /**
         * 職専免(無給)取得時間数
         */
    @TableField("tma_nshoku_rest_hours")
        private Long tmaNshokuRestHours;

        /**
         * 育児休業取得日数
         */
    @TableField("tma_nikuji_used_days")
        private Long tmaNikujiUsedDays;

        /**
         * 育児部分休業取得時間数
         */
    @TableField("tma_nikuji_rest_hours")
        private Long tmaNikujiRestHours;

        /**
         * 介護休業取得日数
         */
    @TableField("tma_nkaigo_used_days")
        private Long tmaNkaigoUsedDays;

        /**
         * 介護部分休業取得時間数
         */
    @TableField("tma_nkaigo_rest_hours")
        private Long tmaNkaigoRestHours;

        /**
         * 自己啓発休業取得日数
         */
    @TableField("tma_njiko_used_days")
        private Long tmaNjikoUsedDays;

        /**
         * 自己啓発部分休業取得時間数
         */
    @TableField("tma_njiko_rest_hours")
        private Long tmaNjikoRestHours;

        /**
         * 職専免(有給)取得日数
         */
    @TableField("tma_nshoku_yu_used_days")
        private Long tmaNshokuYuUsedDays;

        /**
         * 職専免(有給)取得時数
         */
    @TableField("tma_nshoku_yu_rest_hours")
        private Long tmaNshokuYuRestHours;

        /**
         * 就業禁止(終日)取得日数
         */
    @TableField("tma_nkingyoh_used_days")
        private Long tmaNkingyohUsedDays;

        /**
         * 就業禁止(時間)取得時数
         */
    @TableField("tma_nkingyoh_rest_hours")
        private Long tmaNkingyohRestHours;

        /**
         * 勤務時間数合計
         */
    @TableField("tma_nsum_working_hours")
        private Long tmaNsumWorkingHours;


        }