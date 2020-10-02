package jp.smartcompany.admin.searchrangemanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SearchRangeManagerChangeDateDTO {

    private Date nowDate;
    private Date beforeDate;
    private Date afterDate;
    private Date latestDate;

}
