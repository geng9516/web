package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 日別詳細情報（非勤務）VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DailyDetailVO {

    /**
     * 非勤務区分
     */
    private String tdadCnotworkid;
    /**
     * 非勤務区分名称
     */
    private String tdadCnotworkName;
    /**
     * 開始時刻
     */
    private String tdadBopen;
    /**
     * 終了時刻
     */
    private String tdadNclose;
    /**
     * 削除フラグ
     */
    private String tdadNdeleteflg;
    /**
     * 行ID
     */
    private String rowId;
}
