package jp.smartcompany.admin.groupappmanager.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupAppManagerUpdatePermsForm {

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date changeDate;

    private List<PermChangeItem> permList;

}
