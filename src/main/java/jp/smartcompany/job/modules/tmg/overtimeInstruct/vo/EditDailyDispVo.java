package jp.smartcompany.job.modules.tmg.overtimeInstruct.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EditDailyDispVo {

    private List<DailyOverTimeVo> dailyOverTimeVoList;

    /**
     * 基準日時点において、指定した権限を持っているかどうかを判定します。
     */
    private boolean hasAuthority;

    /**
     * 基準日時点の超勤限度時間取得
     */
    private LimitOfBaseDate limitOfBaseDate;
}
