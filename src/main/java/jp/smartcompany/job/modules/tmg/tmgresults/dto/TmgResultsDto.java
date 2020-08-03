package jp.smartcompany.job.modules.tmg.tmgresults.dto;


import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailNonDutyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailOverhoursVO;
import lombok.*;
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
@AllArgsConstructor
@NoArgsConstructor
public class TmgResultsDto {

    private String txtAction;
    private String txtDYYYYMMDD;
    private String psSite;
    private String hasAuthority;
    private String targetEmp;
    /**
     * 出張区分
     */
    private String selMgdCbusinessTrip;

    /**
     * 就業時間始業
     */
    private String txtTdaNopenR;

    /**
     * 就業時間終業
     */
    private String txtTdaNcloseR;

    /**
     * 休暇区分
     */
    private String holiday;

    /**
     * 就業区分
     */
    private String workingId;

    /**
     * 承認者コメント
     */
    private String txtTdaCbosscommentR;

    /**
     * 本人コメント
     */
    private String tdaCowncommentR;

    /**
     * 非勤務
     */
    private List<DetailNonDutyVO> nonDutyList;

    /**
     * 超過勤務
     */
    private List<DetailOverhoursVO> overHoursList;


}
