package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * Vo for  buildSQLforNenjikyukazannissu
 */
@Getter
@Setter
@ToString
public class restYearPaidHolidayVo {

    /**剩余天*/
    private Double nrestDays;
    /**剩余时间*/
    private int nrestHours;

    /**种类*/
    private String ctype;
    /**排序*/
    private String cdesc;
    /**开始日*/
    private String dbegin;
    /**结束日*/
    private String dend;
}
