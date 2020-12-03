package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo;

import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.MonthSumTimeDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class EditDispVo {
    private List<List<LiquidationDailyDto>> monthDtoList=new ArrayList<>();
    private List<MonthSumTimeDto> monthTimeList;
    private List<String> errList;
}
