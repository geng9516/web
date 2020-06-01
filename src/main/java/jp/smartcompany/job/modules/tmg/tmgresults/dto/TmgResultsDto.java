package jp.smartcompany.job.modules.tmg.tmgresults.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * パラメータDTO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TmgResultsDto {

    /**
     * 出張区分
     */
    private String selMGD_CBUSINESS_TRIP;

    /**
     * 就業時間始業
     */
    private String txtTDA_NOPEN_R;

    /**
     * 就業時間終業
     */
    private String txtTDA_NCLOSE_R;

    /**
     * 休暇区分
     */
    private String holiday;

    /**
     * 就業区分
     */
    private String selTDA_CWORKINGID_R;

    /**
     * 承認者コメント
     */
    private String txtTDA_CBOSSCOMMENT_R;

    /**
     * 本人コメント
     */
    private String txtTDA_COWNCOMMENT_R;

    /**
     * 非勤務
     */
    private List<DetailDto> nonDutyList;

    /**
     * 超過勤務
     */
    private List<DetailDto> overHoursList;


}
