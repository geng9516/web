package jp.smartcompany.job.modules.tmg.tmgresults.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * パラメータDTO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DailyCheckDto {
    /**
     *顧客コード
     */
    private String custID;
    /**
     * 法人コード
     */
    private String compCode;
    /**
     * 対象者
     */
    private String targetUser;
    /**
     * 基準日
     */
    private String day;
    /**
     *ユーザID
     */
    private String userCode;
    /**
     *出張区分
     */
    private String mgdCbusinessTrip;
    /**
     *就業開始時刻
     */
    private String tdaNopenR;
    /**
     *就業時間終了時刻
     */
    private String tdaNcloseR;
    /**
     *アクション
     */
    private String action;
    /**
     *休暇
     */
    private String holiday;
    /**
     *就業区分
     */
    private String tdaCworkingidR;
    /**
     *サイトID
     */
    private String siteId;
    /**
     *承認者コメント
     */
    private String tdaCbosscommentR;
    /**
     *本人コメント
     */
    private String tdaCowncommentR;


}
