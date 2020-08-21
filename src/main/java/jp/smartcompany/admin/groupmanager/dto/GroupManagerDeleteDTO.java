package jp.smartcompany.admin.groupmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupManagerDeleteDTO {

    private String systemId;
    @NotEmpty
    private List<String> groupIds;

}
