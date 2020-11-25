package jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class UpdateDetailDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Long mgdId;

    private Boolean newHistory;

    @NotNull
    private MastGenericDetailDO info;

}
