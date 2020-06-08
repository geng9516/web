package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 週勤務パターン
 * @objectSource
 * @date 2020/05/26
 **/
@Getter
@Setter
@ToString
public class TmgWeekPatternDTO {
    /**
     * ﾃﾞｰﾀ開始日
     */
    private String twp_dstartdate;

    /**
     * ﾃﾞｰﾀ終了日
     */
    private String twp_denddate;

    /**
     * データid
     */
    private String twp_nid;

}
