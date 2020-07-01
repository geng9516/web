package jp.smartcompany.job.modules.tmg.overtimeInstruct.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class AvgMonthlyVo {

    private List<String> AvgTimes = new ArrayList<String>();
    private List<String> tableHeader;
    private List<Map<String,String>> date;

}
