package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * Vo for  employee detail
 */
@Getter
@Setter
@ToString
public class EmployeeDetailVo {
    private String name;// 0 漢字氏名

    private String section; // 1 所属名称

    private String worktype;// 2 種別名称

    private String post;// 3 役職
}
