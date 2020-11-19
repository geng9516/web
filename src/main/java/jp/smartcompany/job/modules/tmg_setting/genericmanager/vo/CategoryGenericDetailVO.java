package jp.smartcompany.job.modules.tmg_setting.genericmanager.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryGenericDetailVO {

    private Long mgId;
    private String groupId;
    private String description;
    private String editAble;
    private String categoryId;
    private String categoryName;
    private String historyType;
    private String companyType;

}
