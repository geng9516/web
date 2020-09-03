package jp.smartcompany.job.modules.tmg.paidholiday.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TmgPaidHolidayDto {
    /**
     * 更新者
     */
    @TableField("TPH_CMODIFIERUSERID")
    private String tphCmodifieruserid;
    /**
     * 更新プログラムID
     */
    @TableField("TPH_CMODIFIERPROGRAMID")
    private String tphCmodifierprogramid;
    /**
     * 今期付与日数(A)
     */
    @TableField("TPH_NINVEST")
    private Double tphNinvest;
    /**
     * 調整付与日数(B)
     */
    @TableField("TPH_NADJUST")
    private Double tphNadjust;
    /**
     * 調整付与時間(C)
     */
    @TableField("TPH_NADJUST_HOURS")
    private Double tphNadjustHours;
    /**
     * 調整繰越日数(E)
     */
    @TableField("TPH_NADJUST_TO")
    private Double tphNadjustTo;
    /**
     * 調整繰越時間(F)
     */
    @TableField("TPH_NADJUST_HOURS_TO")
    private Double tphNadjustHoursTo;
    /**
     * 調整付与の有効期限
     */
    @TableField("TPH_DEXPIRE_ADJUST")
    private Date tphDexpireAdjust;
    /**
     * 調整繰越の有効期限
     */
    @TableField("TPH_DEXPIRE_ADJUST_TO")
    private Date tphDexpireAdjustTo;
    /**
     * 変更者コメント
     */
    @TableField("TPH_CCOMMENT")
    private String tphCcomment;
    /**
     * 対象者
     */
    @TableField("TPH_CEMPLOYEEID")
    private String tphCemployeeid;
    /**
     * 付与日
     */
    @TableField("TPH_DYYYYMMDD")
    private Date tphDyyyymmdd;
    /**
     * 法人ｺｰﾄ
     */
    @TableField("TPH_CCOMPANYID")
    private String tphCcompanyid;
    /**
     * 顧客ｺｰﾄ
     */
    @TableField("TPH_CCUSTOMERID")
    private String tphCcustomerid;


}
