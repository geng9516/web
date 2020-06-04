package jp.smartcompany.job.modules.tmg.overtimeInstruct.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class DispOverTimeItemsDto {
    private  String  mgdCheader;
    private  String  mgdCsql;
    private  int     mgdNwidth;
    private  String  mgdCcolumnid;
}
