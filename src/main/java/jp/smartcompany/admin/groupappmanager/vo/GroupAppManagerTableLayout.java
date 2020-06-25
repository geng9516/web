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

    private List<GroupAppManagerGroupDTO> tableHeader;
    private List<GroupAppManagerPermissionTableDTO> tableBody;
}
