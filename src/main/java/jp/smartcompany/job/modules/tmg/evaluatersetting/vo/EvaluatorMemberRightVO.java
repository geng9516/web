package jp.smartcompany.job.modules.tmg.evaluatersetting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EvaluatorMemberRightVO {

    private Boolean workConfirm;
    private Boolean monthConfirm;
    private Boolean overWorkConfirm;
    private Boolean scheduleCreate;
    private Boolean permissionGive;
    private Boolean restConfirm;
    private Integer level;
    private String validStartDate;
    private String validEndDate;

}
