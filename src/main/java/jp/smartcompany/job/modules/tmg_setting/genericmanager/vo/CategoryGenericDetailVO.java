package jp.smartcompany.job.modules.tmg_setting.genericmanager.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class CategoryGenericDetailVO {

    private Long mgId;
    private String groupId;
    private String desc;
    private String editAble;
    private String cateId;
    private String cateName;

    private List<CategoryGenericDetailItemVO> itemList;

}
