package jp.smartcompany.job.modules.tmg.tmgnotification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * Vo for   employeeList
 */
@Getter
@Setter
@ToString
public class EmployeeListVo {
    //0 職員番号
    private  String empid;
    //1 職員氏名
    private String empname;
}
