package jp.smartcompany.admin.groupmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class GroupManagerSortDTO {

    private String groupId;
    private String custId;
    private String systemId;
    private Integer sort;
    private String searchDate;

}
