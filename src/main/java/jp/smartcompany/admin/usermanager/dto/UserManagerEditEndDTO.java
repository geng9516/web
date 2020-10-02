package jp.smartcompany.admin.usermanager.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserManagerEditEndDTO {

    @NotNull
    private Date endDate;

    private Boolean useRetireDate;

    @NotEmpty
    private List<UserManagerListDTO> list;

}
