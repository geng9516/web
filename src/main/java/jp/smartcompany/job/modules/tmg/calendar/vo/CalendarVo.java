package jp.smartcompany.job.modules.tmg.calendar.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CalendarVo {

    /** 更新判別用フラグ*/
    private boolean gUpdateFlag = false;
    /** エラー判別用フラグ*/
    private boolean gErrorFlag = false;
    /** 全学判別用フラグ */
    private boolean gTopLevelOrganization = true;

    List<CalendarDispVo> calendarDispVoList=new ArrayList<CalendarDispVo>();
}
