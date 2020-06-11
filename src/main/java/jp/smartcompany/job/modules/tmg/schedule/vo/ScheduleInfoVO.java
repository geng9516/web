package jp.smartcompany.job.modules.tmg.schedule.vo;

import jp.smartcompany.job.modules.tmg.schedule.dto.ScheduleDataDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 陳毅力
 * @description スケジュールリスト情報
 * @objectSource
 * @date 2020/06/01
 **/
@Getter
@Setter
@ToString
public class ScheduleInfoVO {

    /**
     * 勤務予定データ期間
     */
    private String period;

    /**
     * 前月開始時間
     */
    private String preStart;

    /**
     *　前月終了時間
     */
    private String preEnd;

    /**
     *　翌月開始時間
     */
    private String nextStart;

    /**
     *　翌月終了時間
     */
    private String nextEnd;

    /**
     * 公休日数	基準日数	基準時間	年次休暇残
     */
    private PaidHolidayVO paidHolidayVO;


    /**
     * 勤務予定 リスト
     */
    private List<ScheduleDataDTO> scheduleDataDTOList;
}
