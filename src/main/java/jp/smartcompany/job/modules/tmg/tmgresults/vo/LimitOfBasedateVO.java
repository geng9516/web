package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 基準日時点の超勤限度時間取得用用VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class LimitOfBasedateVO {

    /**
     * OT_DAILY_01
     */
    private String otDaily;
    /**
     * DAILY_CONV_MINUTE(OT_DAILY_01 * 60)
     */
    private String dailyConvMinute;
}
