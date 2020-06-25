package jp.smartcompany.job.modules.tmg.schedule.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 勤務予定の変更日VO
 * @objectSource
 * @date 2020/06/24
 **/
@Getter
@Setter
@ToString
public class ModifiedDateVO {
    /**
     * 勤務予定の変更日
     */
    private String modifiedDate;
}
