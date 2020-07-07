package jp.smartcompany.job.modules.tmg.empattrsetting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BeginDateEditDispVo {

    /**
     * 勤務開始日設定状況
     */
    private List<EmploymentWithMgdVo>  list;

    /**
     * [参考]発令上の勤務開始日
     */
    private EmployMentWithMEVo beginDate;

    /**
     * 更新履歴
     */
    private List<TmgDateOfEmpLogVo> historyList;

}
