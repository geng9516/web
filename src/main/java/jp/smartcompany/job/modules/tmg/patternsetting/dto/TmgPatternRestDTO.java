package jp.smartcompany.job.modules.tmg.patternsetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 勤務パターン休憩時間DTO
 * @objectSource
 * @date 2020/06/17
 **/
@Getter
@Setter
@ToString
public class TmgPatternRestDTO {

    private String custId;
    private String compCode;
    private String sectionId;
    private String groupId;
    private String minDate;
    private String maxDate;
    private String employeeId;
    private String modifierprogramid;
    private String patternId;
    /**
     * 休憩開始時間
     */
    private String restOpen;
    /**
     * 休憩終了時間
     */
    private String restClose;


}
