package jp.smartcompany.job.modules.tmg.overtimeInstruct.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class DailyDetailOverHoursVo {

    private String tdaCemployeeid;
    private String tdadDyyyymm;
    private String tdadDyyyymmdd;
    private String tdadCnotworkid;// 非勤務区分
    private String tdadNopen;// 開始時刻
    private String tdadNclose;// 終了時刻
    private String tdadCsparechar1;// 予備文字列1
    private String tdadCsparechar2;// 予備文字列2(超過勤務の申請ステータス)
}
