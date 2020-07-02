package jp.smartcompany.admin.groupappmanager.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupAppManagerUpdatePermsForm {

    private Date changeDate;

    private List<PermChangeItem> permList;

}
