package jp.smartcompany.job.modules.tmg.deptstatlist.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
public class DispItemsDto {

    private String mgdCitemname;
    private String mgdCsql;
    private String mgdCtotalsql;
    private String mgdNwidth;
    private String tempColumnid;
}
