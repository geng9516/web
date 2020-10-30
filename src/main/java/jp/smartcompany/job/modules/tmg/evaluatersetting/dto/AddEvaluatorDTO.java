package jp.smartcompany.job.modules.tmg.evaluatersetting.dto;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
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
public class AddEvaluatorDTO {

    @NotBlank
    private String empId;
    @NotBlank
    private String sectionId;
    @NotBlank
    private String groupId;
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
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;

    public void validate() {
        if (!notification && StrUtil.isNotBlank(approvalLevel)) {
            throw new GlobalException("休暇認定許可をオンにしないとレベルを設定できません。");
        }
    }

}
