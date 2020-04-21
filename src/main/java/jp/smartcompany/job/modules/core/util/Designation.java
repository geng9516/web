package jp.smartcompany.job.modules.core.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Designation {

    private String customerCode;
    private String companyCode;
    private String companyHierarchy;
    private String companyOrder;
    private String companyName;
    private String employee;
    private String userId;
    private String name;
    private String nameKana;
    private String section;
    private String sectionHierarchy;
    private String sectionOrder;
    private String sectionName;
    private String postCode;
    private Integer postRank;
    private String postName;
    private String attachRole;
    private Date personnelChangesBegin;
    private Date endDate;
    private String bossOrNot;

}
