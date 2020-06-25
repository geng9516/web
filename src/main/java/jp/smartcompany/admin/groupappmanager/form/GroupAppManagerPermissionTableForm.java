package jp.smartcompany.admin.groupappmanager.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupAppManagerPermissionTableForm {

    private Date date;
    private List<String> groupIds;

}
