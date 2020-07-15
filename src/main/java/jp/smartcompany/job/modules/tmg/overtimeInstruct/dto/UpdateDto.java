package jp.smartcompany.job.modules.tmg.overtimeInstruct.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class UpdateDto {

    private String sEmpId;

    private String baseDay;
    private String baseMonth;

    private List<UpdateOverTimeDto> updateOverTimeDtoList;
    private List<UpdateRestTimeDto> updateRestTimeDtoList;
}
