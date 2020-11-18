package jp.smartcompany.job.modules.tmg_setting.genericmanager.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CategoryGenericDetailItemVO {

    private Long mgdId;
    private String groupId;
    private String detailId;
    private String masterCode;
    private String desc;
    private Date startDate;
    private Date endDate;

}
