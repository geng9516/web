package jp.smartcompany.job.modules.tmg.empattrsetting.vo;

import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotApprovalVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmpDispVo {

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<TmgEmpVo> list;
}
