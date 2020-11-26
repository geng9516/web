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
public class HomeWorkVO {
    /**
     * 0日
     */
    private String tdaDd;

    /**
     * 1曜日
     */
    private String tdaDy;

    /**
     *　2勤務年月日
     */
    private String hwApplicationdate;

    /**
     * 3休日区分
     */
    private String mgdCsparechar;

    /**
     * 4申請状態
     */
    private String hwStatus;

    /**
     * 在宅勤務状態
     */
    private String hwHomework;

    /**
     * 開始時間
     */
    private String hwStart;

    /**
     * 終了時間
     */
    private String hwEnd;

    /**
     * 通勤FLG
     */
    private String hwCommute;

    /**
     * 申請コメント
     */
    private String hwApplicationcomment;

    /**
     * 承認コメント
     */
    private String hwApprovalcomment;


}
