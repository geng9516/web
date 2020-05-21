package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 表示月遷移リスト情報
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DispMonthlyVO {

    /**
     * コード（年月日）
     */
    private String code;
    /**
     * 値（表示値）
     */
    private String val;

}
