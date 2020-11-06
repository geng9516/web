package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo;

import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyCheckDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationMonthDto;
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

    private List<String> errList;
}
