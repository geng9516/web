package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 勤務状況一覧、健康状態一覧の使用可否設定の取得格納用VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class WorkHealthChkVO {
    /**
     * 勤務状況表示有無
     */
    private boolean bDispKinmujokyoKakunin;

    /**
     * 勤務状況
     */
    private boolean bWorkChkStatus;

    /**
     * 状況表示有無
     */
    private boolean bDispKenkojotaiKakunin;

    /**
     * 健康状況
     */
    private boolean bHealthChkStatus;
    /**
     * 勤務状況終了月
     */
    private int kinmujokyoEnd;
    /**
     *健康状態
     */
    private String selHealthStatus;
}
