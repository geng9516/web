package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 陳毅力
 * @description 週勤務パターンインプットDTO
 * @objectSource
 * @date 2020/07/16
 **/
@Setter
@Getter
@ToString
public class WeekPatternInputDTO {
    /**
     * 適用開始時間
     */
    private String applyStart;

    /**
     * 適用終了時間
     */
    private String applyEnd;

    /**
     * 　曜日　　区分　始業　終業	　休憩开始　休憩结束
     */
    private List<WeekPatternFormDTO> weekPatternFormDTOList;

}
