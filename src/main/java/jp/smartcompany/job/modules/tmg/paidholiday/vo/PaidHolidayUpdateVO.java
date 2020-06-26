package jp.smartcompany.job.modules.tmg.paidholiday.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
public class PaidHolidayUpdateVO {

    /**
     * 対象者
     */
    private String cemployeeid;
    /**
     * 付与日
     */
    private String dyyyymmdd;
    /**
     * 今期付与日数(A)
     */
    private String ninvest;

    /**
     * 調整付与日数(B)
     */
    private String nadjust;
    /**
     * 調整付与時間(C)
     */
    private String nadjustHours;
    /**
     * 調整繰越日数(E)
     */
    private String nadjustTo;
    /**
     * 調整繰越時間(F)
     */
    private String nadjustHoursTo;
    /**
     * 調整付与の有効期限
     */
    private String dexpireAdjust;
    /**
     * 調整繰越の有効期限
     */
    private String dexpireAdjustTo;
    /**
     * 変更者コメント
     */
    private String ccomment;
}
