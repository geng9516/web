package jp.smartcompany.job.modules.tmg.overtimeInstruct.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class OneMonthDetailVo {
    /***/
    private String seq;
    /**曜日*/
    private String dayOfWeek;
    /**日付*/
    private String day;
    /**休暇区分*/
    private String tcaCholflg;

}
