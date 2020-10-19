package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo;

import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationPeriodListDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.WorkTypeGroupDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class LiquidationDispVo {

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<LiquidationPeriodListDto> liquidationPeriodList;

}
