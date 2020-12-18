package jp.smartcompany.job.modules.tmg.tmgnotification.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author wucj
 * parameter for  NotificationList
 */
@Getter
@Setter
@ToString
public class ParamNotificationCheckOverhoursListDto {

    /**承認者*/
    private String evaluator;

    /**顧客コード*/
    private String custId;
    /**法人コード*/
    private String compId;
    /**職員番号*/
    private String userCode;

    /**
     * 開始日/出勤にする休日
     */
    private String begin;
    /**
     * 終了日/振休日とする出勤日
     */
    private String end;

    /**指定なし*/
    private String noreserved;
    /**曜日*/
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;
}
