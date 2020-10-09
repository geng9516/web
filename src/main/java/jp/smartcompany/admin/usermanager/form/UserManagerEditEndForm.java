package jp.smartcompany.admin.usermanager.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
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
public class UserManagerEditEndForm {

    @NotNull
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date endDate;

    private Boolean useRetireDate;

    @NotEmpty
    private List<UserManagerListDTO> list;

}
