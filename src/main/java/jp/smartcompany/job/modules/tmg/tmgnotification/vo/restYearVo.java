package jp.smartcompany.job.modules.tmg.tmgnotification.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "typeId")
public class restYearVo {

    private String typeId;
    private String typeName;
    /**显示期间*/
    private List<String> timeRange = new ArrayList<String>();
    /**剩余天*/
    private List<String> dayList = new ArrayList<String>();
    /**剩余时间*/
    private List<String> timeList = new ArrayList<String>();
}
