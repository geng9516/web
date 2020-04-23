package jp.smartcompany.job.modules.base.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
public class PluggableDTO {

    /**
     * 処理フェーズ
     */
    private String cphase;
    /**
     * 顧客コード
     */
    private String customerId;
    /**
     * 法人コード
     */
    private String companyId;
    /**
     * 社員番号
     */
    private String employeeId;
    /**
     * 基準日
     */
    private Date yyyymmdd;
    /**
     * 更新者
     */
    private String userId;
    /**
     * 更新プログラムID
     */
    private String programId;


    /**
     * 勤務開始日
     */
    private Date beginDateWork;
    /**
     * 分単位の数値
     */
    private int pnTime;


    //region 年次有給休暇消化処理

    /**
     * 開始日
     */
    private Date startDate;
    /**
     * 終了日
     */
    private Date endDate;
    /**
     * 消化前残日数
     */
    private int restDays;
    /**
     * 消化前残時間数
     */
    private int restHours;
    /**
     * 申請番号
     */
    private String ntfNo;
    /**
     * 0:指定された申請以外も含む計算(通常) 1:指定された申請番号のみでの計算
     */
    private int single;
    //endregion


}
