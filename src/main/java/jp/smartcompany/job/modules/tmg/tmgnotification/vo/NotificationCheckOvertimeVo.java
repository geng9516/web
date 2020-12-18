package jp.smartcompany.job.modules.tmg.tmgnotification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wucj
 * Vo for  NotificationList
 */
@Getter
@Setter
@ToString
public class NotificationCheckOvertimeVo {

    /**休暇申請日*/
    private String applyDays;
    /**休暇申請日のコード 日：1,月：2,火：3,水：4,木：5,金：6,土：7 * */
    private String daysOfWeekCode;
    /**休暇申請日の曜日*/
    private String daysOfWeek;
    /**超過申請命令・申請判断フラグ*/
    private String flg;
}
