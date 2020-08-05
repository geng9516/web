package jp.smartcompany.job.modules.tmg.tmgifsimulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/08/03
 **/
@Setter
@Getter
@ToString
public class SimulationInsertDTO {

    private String psCustId;
    private String psCompId;
    /**
     * グループコード
     */
    private String psGroupCode;
    /**
     * 明細データコード
     */
    private String psDetailId;
    /**
     * マスタコード
     */
    private String psMasterCode;
    private String psLanguage;
    /**
     * データ開始日
     */
    private String psStartDate;
    /**
     * データ終了日
     */
    private String psEndDate;
    /**
     * 更新者ID
     */
    private String psUpdateUser;
    /**
     * 絞込み項目区分
     */
    private String psExuludecondType;
    /**
     * 範囲(FROM)
     */
    private String psExuludecondFrom;
    /**
     * 範囲(TO)
     */
    private String psExuludecondTo;

}
