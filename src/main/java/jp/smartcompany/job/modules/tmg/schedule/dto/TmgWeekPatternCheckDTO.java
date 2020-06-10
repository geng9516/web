package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 週次勤務パターン（エラーチェック用）DTO
 * @objectSource
 * @date 2020/06/05
 **/
@Getter
@Setter
@ToString
public class TmgWeekPatternCheckDTO {

    /**
     * 週勤務社員ID
     */
    private String custId;
    /**
     *
     */
    private String compCode;
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 更新者
     */
    private String twp_cmodifieruserid;

    /**
     * 就業区分
     */
    private String twp_cmodifierprogramid;

    /**
     * 　データ開始時間
     */
    private String twp_dstartdate;
    /**
     * 　データ終了時間
     */
    private String twp_denddate;

    /**
     * 更新フラグ
     * TRUEの場合は登録
     * FALSEの場合は削除
     */
    private boolean checkFlag;

    /**
     * 　時間期間
     */
    private String period;

    /**
     * 　出勤時間
     */
    private String twp_copen1;
    /**
     * 退勤時間
     */
    private String twp_cclose1;
    /**
     * 　休憩時間
     */
    private String twp_crestopen1;

    /**
     * 　休憩時間
     */
    private String twp_crestclose1;

    /**
     * 就業区分
     * 1: 法定休
     * 0:　出勤
     * 3:　所定休
     */
    private String twp_cholflg1;


    /**
     * 出勤時間
     */
    private String twp_copen2;
    /**
     * 退勤時間
     */
    private String twp_cclose2;

    /**
     * 　休憩時間
     */
    private String twp_crestopen2;

    /**
     * 　休憩時間
     */
    private String twp_crestclose2;


    /**
     * 就業区分
     */
    private String twp_cholflg2;


    /**
     * 出勤時間
     */
    private String twp_copen3;
    /**
     * 退勤時間
     */
    private String twp_cclose3;

    /**
     * 　休憩時間
     */
    private String twp_crestopen3;

    /**
     * 　休憩時間
     */
    private String twp_crestclose3;


    /**
     * 就業区分
     */
    private String twp_cholflg3;


    /**
     * 出勤時間
     */
    private String twp_copen4;
    /**
     * 退勤時間
     */
    private String twp_cclose4;
    /**
     * 　休憩時間
     */
    private String twp_crestopen4;

    /**
     * 　休憩時間
     */
    private String twp_crestclose4;

    /**
     * 就業区分
     */
    private String twp_cholflg4;

    /**
     * 出勤時間
     */
    private String twp_copen5;
    /**
     * 退勤時間
     */
    private String twp_cclose5;

    /**
     * 　休憩時間
     */
    private String twp_crestopen5;

    /**
     * 　休憩時間
     */
    private String twp_crestclose5;

    /**
     * 就業区分
     */
    private String twp_cholflg5;

    /**
     * 出勤時間
     */
    private String twp_copen6;
    /**
     * 退勤時間
     */
    private String twp_cclose6;

    /**
     * 　休憩時間
     */
    private String twp_crestopen6;

    /**
     * 　休憩時間
     */
    private String twp_crestclose6;


    /**
     * 就業区分
     */
    private String twp_cholflg6;


    /**
     * 出勤時間
     */
    private String twp_copen7;
    /**
     * 退勤時間
     */
    private String twp_cclose7;

    /**
     * 　休憩時間
     */
    private String twp_crestopen7;

    /**
     * 　休憩時間
     */
    private String twp_crestclose7;

    /**
     * 就業区分
     */
    private String twp_cholflg7;


}
