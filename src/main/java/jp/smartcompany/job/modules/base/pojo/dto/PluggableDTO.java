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
     *処理フェーズ
     */
    private String cphase;
    /**
     *顧客コード
     */
    private String customerId;
    /**
     *法人コード
     */
    private String companyId;
    /**
     *社員番号
     */
    private String employeeId;
    /**
     *基準日
     */
    private Date yyyymmdd;
    /**
     *勤務開始日
     */
    private Date beginDateWork;
    /**
     *分単位の数値
     */
    private Double pnTime;

}
