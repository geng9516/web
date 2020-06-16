package jp.smartcompany.job.modules.tmg.patternsetting.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 */
@Getter
@Setter
@ToString
public class TmgPersonalPatternRow {

    /**
     * 顧客コード
     */
    @TableField("CUSTOMERID")
    private String cCustomerId;
    /**
     * 法人コード
     */
    @TableField("COMPANYID")
    private String ccCmpanyId;
    /**
     * 職員番号
     */
    @TableField("EMPLOYEEID")
    private String cEmployeeId;
    /**
     * 該当年月
     */
    @TableField("YYYYMM")
    private Date dYyyyMm;
    /**
     * 対象年月日
     */
    @TableField("YYYYMMDD")
    private Date dYyyyMmDd;
    /**
     * 休日フラグ
     */
    @TableField("CHOLFLG")
    private String cHolFlg;
    /**
     * 始業時刻
     */
    @TableField("NOPEN")
    private int nOpen;
    /**
     * 終業時刻
     */
    @TableField("NCLOSE")
    private int nClose;

    /**
     * 休憩開始時刻
     */
    @TableField("NRESTOPEN")
    private int nRestOpen;

    /**
     * 休憩終了時刻
     */
    @TableField("NRESTCLOSE")
    private int nRestClose;

}
