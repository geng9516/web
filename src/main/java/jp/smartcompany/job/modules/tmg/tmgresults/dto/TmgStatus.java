package jp.smartcompany.job.modules.tmg.tmgresults.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 個人タイプかつ編集機能を持つ各種コンテンツにおいて、編集可否を判定する際に使用できます。
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TmgStatus {
    /**
     * 月次勤怠締め判定（０：締め処理確定前  １：締め処理確定後）
     */
    private String fixedMonthly;
    /**
     * 月次給与締め判定（０：締め処理確定前  １：締め処理確定後）
     */
    private String fixedSalary;
    /**
     * 日次ステータス
     */
    private String tdaCstatusflg;
    /**
     * 月次ステータス
     */
    private String tmoCstatusflg;
    /**
     * 未来日判断
     */
    private String isFuture;
}
