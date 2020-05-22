package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 予定出社・退社時間の基準値VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CompanyVO {
    /**
     *開始時刻
     */
    private String tcoNopen;
    /**
     *終了時刻
     */
    private String tcoNclose;
    /**
     *開始時刻(HH:MI60形式)
     */
    private String tcoNopenMin2hhmi;
    /**
     *終了時刻(HH:MI60形式)
     */
    private String tcoNcloseMin2hhmi;
}
