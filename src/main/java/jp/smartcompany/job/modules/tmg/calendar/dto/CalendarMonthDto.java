package jp.smartcompany.job.modules.tmg.calendar.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CalendarMonthDto {
    private String month;

    //private Boolean updateFlg= false;

    private List<String> holFlgList=new ArrayList<String>();
}