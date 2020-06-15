package jp.smartcompany.job.modules.tmg.PatternSetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 勤務時間・休憩時間の制限値をマスタから取得しリストDTO
 * @objectSource
 * @date 2020/06/15
 **/
@Getter
@Setter
@ToString
public class RestTimeLimitDTO {

    /**
     * RESTTIME6  RESTTIME8
     */
    private String genericCode;
    /**
     * 　6時間を超える勤務に対して45分以上の休憩が必要です。
     * 　8時間を超える勤務に対して1時間以上の休憩が必要です。
     */
    private String genericDesc;
    /**
     * 休憩時間下限
     */
    private String limit_rest_lower;
    /**
     * 勤務時：間下限
     */
    private String limit_work_lower;
    /**
     * 勤務時：間上限
     */
    private String limit_work_upper;


}
