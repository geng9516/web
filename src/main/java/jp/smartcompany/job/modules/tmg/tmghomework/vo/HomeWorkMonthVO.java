package jp.smartcompany.job.modules.tmg.tmghomework.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author 顧成斌
 * @description 在宅勤務Monthvo
 * @objectSource
 * @date 2020/11/25
 **/
@Getter
@Setter
@ToString
public class HomeWorkMonthVO {

    /**
     * 0 EMPID
     */
    private String empname;

    /**
     * 1 通勤対象
     */
    private String commute;

    /**
     *　2 在宅終日
     */
    private String day;

    /**
     * 3 在宅午前
     */
    private String am;

    /**
     * 4 在宅午後
     */
    private String pm;

    /**
     * 5 在宅時間
     */
    private String time;

}
