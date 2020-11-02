package jp.smartcompany.job.modules.tmg.evaluatersetting.dto;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class EditAuthorityDTO {

    @NotBlank
    private String groupId;
    @NotBlank
    private String sectionId;
    @NotBlank
    private String empId;

    @NotEmpty
    @Valid
    private List<EditAuthorityItemDTO> list;

    public void validate() {
        for (EditAuthorityItemDTO editAuthorityItemDTO : list) {
            if (!editAuthorityItemDTO.getDelete() && !editAuthorityItemDTO.getNewLine()) {

                if (StrUtil.isBlank(editAuthorityItemDTO.getBeforeStartDate())||StrUtil.isBlank(editAuthorityItemDTO.getBeforeEndDate())) {
                     throw new GlobalException("beforeStartDateとbeforeEndDateを入社してください");
                }

            }
        }
    }

}
