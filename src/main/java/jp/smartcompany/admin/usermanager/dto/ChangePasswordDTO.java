package jp.smartcompany.admin.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class ChangePasswordDTO {

    private Integer searchType;
    private List<String> userIds;

}
