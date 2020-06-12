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
@Accessors(chain = true)
public class PaidHolidayDispVO {
    /**
     * 付与日
     */
    private String dyyyymmdd;
    /**
     * 今回付与期間終了日
     */
    private String dyyyymmddEnd;
    /**
     * 今期付与日数(A)
     */
    private String ninvest;
    /**
     * 前期繰越日数(D)
     */
    private String nthroughout;
    /**
     * 前期繰越時間(G)
     */
    private String nthroughoutHours;
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
     * 合計付与日数(A)+(B)+(D)+(E)
     */
    private String ninvestDays;
    /**
     * 合計付与時間(C)+(F)+(G)
     */
    private String ninvestHours;
    /**
     * 認定出勤日数
     */
    private String nconfirm;
    /**
     * 予定勤務日数
     */
    private String nplanworkday;
    /**
     * 予定の8割
     */
    private String nplanworkdayLimit;
    /**
     * 今期取得日数
     */
    private String npaidUsedDays;
    /**
     * 今期取得時間数
     */
    private String npaidUsedHours;
    /**
     * 今期残日数
     */
    private String npaidRestDays;
    /**
     * 今期残時間数
     */
    private String npaidRestHours;
    /**
     * 勤務開始日
     */
    private String dbegindateWork;
    /**
     * 週勤務パターン(週n日)
     */
    private String cworkingdaysWeekName;
    /**
     * 平均勤務時間(計算用)
     */
    private String navgworktimeCalc;
    /**
     * 平均勤務時間(表示用)
     */
    private String navgworktimeDisp;
    /**
     * 付与の有効期限
     */
    private String dexpireInvest;
    /**
     * 調整付与の有効期限
     */
    private String dexpireAdjust;
    /**
     * 調整繰越の有効期限
     */
    private String dexpireAdjustTo;
    /**
     * 喪失日数
     */
    private String nloseDays;
    /**
     * 喪失時間数
     */
    private String nloseHours;
    /**
     * 変更者コメント
     */
    private String ccomment;


}
