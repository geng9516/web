package jp.smartcompany.job.modules.tmg.empattrsetting.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BeginDateForUpdateDto {

    /**  アクション識別子*/
    private String action;

    /**  適用開始日*/
    private String psStartDate;

    /**  適用終了日*/
    private String psEndDate;

    /**  勤務開始日*/
    private String psBeginDate;

    /**  適用開始日(旧設定)*/
    private String psOldStartDate;

    /**  適用終了日(旧設定)*/
    private String psOldEndDate;

    /**  勤務開始日(旧設定)*/
    private String psOldBeginDate;

    /**  勤務開始日(一番古い)*/
    private String psMaxOldStartDate;
}
