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
public class IsWorkHealthChkVO {
    /**
     * 勤務状況一覧の使用可否
     */
    private int workChk;
    /**
     * 健康状態一覧の使用可否
     */
    private int healthChk;
}
