package jp.smartcompany.job.modules.tmg.evaluatersetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class EditAuthorityItemDTO {

    @NotNull
    private Boolean delete;
    @NotNull
    private Boolean newLine;

    private String approvalLevel;
    @NotNull
    private Boolean dailyResult;
    @NotNull
    private Boolean monthlyResult;
    @NotNull
    private Boolean overTime;
    @NotNull
    private Boolean schedule;
    @NotNull
    private Boolean authority;
    @NotNull
    private Boolean notification;
    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;
    private String beforeStartDate;
    private String beforeEndDate;

}
