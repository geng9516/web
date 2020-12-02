package jp.smartcompany.job.modules.tmg.tmghomework.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author 顧成斌
 * @description 在宅勤務登録vo
 * @objectSource
 * @date 2020/11/25
 **/
@Getter
@Setter
@ToString
public class HomeWorkAdminVO {

    /**
     * 0 empname
     */
    private String empname;
    /**
     * 1 日
     */
    private String tdaday;

    /**
     * 2 申請状態
     */
    private String hwstatus;

    /**
     *　3 在宅勤務状態
     */
    private String hwhomework;

    /**
     * 4 開始時間
     */
    private String hwStart;

    /**
     * 5 終了時間
     */
    private String hwEnd;

    /**
     * 6 通勤FLG
     */
    private String hwCommute;

    /**
     * 7 申請コメント
     */
    private String hwApplicationcomment;

    /**
     * 8 承認コメント
     */
    private String hwApprovalcomment;

    /**
     * 9 時間
     */
    private String hwtime;

    /**
     * 10 empid
     */
    private String EMPID;

    /**
     * 11 就業区分名称
     */
    private String hwcworkingid;

}
