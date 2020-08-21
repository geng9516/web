package jp.smartcompany.admin.groupmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GroupManagerDeleteDTO {

    private String systemId;
    private String searchDate;
    private List<String> groupIds;

}
