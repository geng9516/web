package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * Vo for  paidHolidayThisMonthInfo
 */
@Getter
@Setter
@ToString
public class PaidHolidayThisMonthInfoVo {
    /**付与後：年休月初残日数/付与前：年休残日数*/
    private String tmoNpaidBeginingDays;
    /**付与後：年休月初残時間数/付与前：年休残時間数*/
    private String tmoNpaidBeginingHours;
    /**付与後：年休残日数*/
    private String tmoNpaidRestDays;
    /**付与後：年休残時間数*/
    private String tmoNpaidRestHours;

    private String tphDyyyymmdd;

}
