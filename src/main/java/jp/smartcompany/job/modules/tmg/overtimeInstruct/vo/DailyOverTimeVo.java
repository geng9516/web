package jp.smartcompany.job.modules.tmg.overtimeInstruct.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class DailyOverTimeVo {

    //ID
    private String empId;
    //氏名
    private String empName;
    //就業区分
    private String workingId;
    //始業(予定)
    private String openN;
    //終業(予定)
    private String closeN;
    //始業(打刻)
    private String openTp;
    //終業(打刻)
    private String closeTp;
    //超過勤務命令
    private List<DailyDetailOverHoursVo> overTimeList=new ArrayList<>();
    //休憩
    private List<ResultRest40tVo> restTimeList=new ArrayList<>();
    //備考
    private String message;
    // 超勤権限有無フラグ
    private boolean isOvertime;
    // 表示対象日が「確定済」かどうかの状態
    private boolean isStatus;
}
