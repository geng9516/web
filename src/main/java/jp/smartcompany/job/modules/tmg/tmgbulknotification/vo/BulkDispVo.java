package jp.smartcompany.job.modules.tmg.tmgbulknotification.vo;


import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.HistoryDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BulkDispVo {

    private List<HistoryDto> historyList;
    private  String  timeRange;

}
