package jp.smartcompany.admin.groupappmanager.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PermChangeItem {

    private Integer rowIndex;
    private Integer colIndex;
    private String permission;

}
