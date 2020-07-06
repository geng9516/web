package jp.smartcompany.job.modules.tmg.monthlyoutput.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NotAppSectionListVo {

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<NotFixedDeptListVo> list;
}
