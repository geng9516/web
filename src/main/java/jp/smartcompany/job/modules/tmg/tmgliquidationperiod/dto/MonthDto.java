package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Getter
@Setter
@ToString
public class MonthDto {
    private List<LqdDto> monthList;
    private String empId;
    private String startDate;
    private String endDate;
}
