package jp.smartcompany.admin.groupappmanager.vo;

import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionTableDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class GroupAppManagerTableLayout {

    /** 今回改定日 */
    private String changeDate;
    /** 前回改定日 */
    private String beforeDate;
    /** 次回改定日 */
    private String afterDate;
    /** 未来改定日 */
    private String futureDate;
    /** 最新改定日 */
    private String latestDate;
    private List<GroupAppManagerGroupDTO> tableHeader;
    private List<GroupAppManagerPermissionTableDTO> tableBody;
}
