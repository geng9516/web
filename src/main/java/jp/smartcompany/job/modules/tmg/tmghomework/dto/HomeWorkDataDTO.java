package jp.smartcompany.job.modules.tmg.tmghomework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @objectSource
 * @date 2020/11/25
 **/
@Getter
@Setter
@ToString
public class HomeWorkDataDTO {
    /**
     * 顧客ｺｰﾄﾞ
     */
    private String hw_ccustomerid;

    /**
     * 法人ｺｰﾄﾞ
     */
    private String hw_ccompanyid;

    /**
     *　職員番号
     */
    private String hw_cemployeeid;

    /**
     *　申請日
     */
    private String hw_applicationdate;

    /**
     * 申請状態
     */
    private String hw_status;

    /**
     * 在宅勤務状態
     */
    private String hw_homework;

    /**
     * 開始時間
     */
    private String hw_start;

    /**
     * 終了時間
     */
    private String hw_end;

    /**
     * 通勤FLG
     */
    private String hw_commute;

    /**
     * 申請コメント
     */
    private String hw_applicationcomment;

    /**
     * 承認コメント
     */
    private String hw_approvalcomment;

    /**
     * 承認者番号
     */
    private String hw_approvalempid;

    /**
     * 承認日
     */
    private String hw_approvaledate;

}
