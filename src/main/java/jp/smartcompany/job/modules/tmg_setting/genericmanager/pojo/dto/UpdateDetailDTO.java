package jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UpdateDetailDTO {


    private Boolean newHistory;

    @NotNull
    private MastGenericDetailDO info;

}
