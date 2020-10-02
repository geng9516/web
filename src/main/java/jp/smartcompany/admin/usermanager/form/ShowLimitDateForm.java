package jp.smartcompany.admin.usermanager.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class ShowLimitDateForm {

    @NotEmpty
    private List<String> userIds;
    @NotNull
    private Integer searchType;

}
