package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 陳毅力
 * @description 予定作成更新画面からパラメータ
 * @objectSource
 * @date 2020/06/03
 **/
@Getter
@Setter
@ToString
public class MonthlyUScheduleEditParaDTO {
    /**
     * ログインユーザー
     */
    private String loginUserId;

    /**
     * 　目標ユーザー
     */
    private String targetUserId;

    /**
     * 　基準日(開始日)
     */
    private String baseDate;

    /**
     * 　表示開始日
     */
    private String dispStartDate;

    /**
     * 　表示終了日
     */
    private String endDispDate;

    /**
     * 勤務予定 リスト
     */
    private List<MonthlyScheduleEmpInfoDTO> monthlyScheduleEmpInfoDTOS;

}
