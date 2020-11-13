package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo;

import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyCheckDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationMonthDto;
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
    private List<LiquidationMonthDto> monthDtoList=new ArrayList<>();
    private List<MonthSumTimeDto> monthTimeList;
    private List<String> errList;
}
