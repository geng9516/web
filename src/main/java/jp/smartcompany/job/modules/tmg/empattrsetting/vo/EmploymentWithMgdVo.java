package jp.smartcompany.job.modules.tmg.empattrsetting.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmploymentWithMgdVo {

    private String dstart; // 適用開始日

    private String dend;// 適用終了日

    private String dendback;// 適用終了日(保持用)

    private String beginDate;// 勤務開始日

}
