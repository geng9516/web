package jp.smartcompany.job.modules.tmg.schedule.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 週勤務パターン
 * @objectSource
 * @date 2020/06/04
 **/
@Getter
@Setter
@ToString
public class TmgWeekPatternVO {

    /**
     * twp_nid
     */
    private String twp_nid;

    /**
     * 週勤務社員ID
     */
    private String twp_cemployeeid;
    /**
     * 　データ開始時間
     */
    private String twp_dstartdate;
    /**
     * 　データ終了時間
     */
    private String twp_denddate;
    /**
     * 　時間期間
     */
    private String period;
    /**
     * 　勤務タイプ
     */
    private String workname1;
    /**
     * 　出勤時間
     */
    private String twp_copen1;
    /**
     * 退勤時間
     */
    private String twp_cclose1;
    /**
     * 　休憩時間（期間）
     */
    private String rest1;
    /**
     *
     */
    private String workname2;
    /**
     *
     */
    private String twp_copen2;
    /**
     *
     */
    private String twp_cclose2;
    /**
     *
     */
    private String rest2;
    /**
     *
     */
    private String workname3;
    /**
     *
     */
    private String twp_copen3;
    /**
     *
     */
    private String twp_cclose3;
    /**
     *
     */
    private String rest3;
    /**
     *
     */
    private String workname4;
    /**
     *
     */
    private String twp_copen4;
    /**
     *
     */
    private String twp_cclose4;
    /**
     *
     */
    private String rest4;
    /**
     *
     */
    private String workname5;
    /**
     *
     */
    private String twp_copen5;
    /**
     *
     */
    private String twp_cclose5;
    /**
     *
     */
    private String rest5;
    /**
     *
     */
    private String workname6;
    /**
     *
     */
    private String twp_copen6;
    /**
     *
     */
    private String twp_cclose6;
    /**
     *
     */
    private String rest6;
    /**
     *
     */
    private String workname7;
    /**
     *
     */
    private String twp_copen7;
    /**
     *
     */
    private String twp_cclose7;
    /**
     *
     */
    private String rest7;


}
