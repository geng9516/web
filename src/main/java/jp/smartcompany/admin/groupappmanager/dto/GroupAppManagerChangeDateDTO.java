package jp.smartcompany.admin.groupappmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class GroupAppManagerChangeDateDTO implements Serializable {
    private Date nowdate;
    private Date beforedate;
    private Date afterdate;
    private Date latestdate;
}
