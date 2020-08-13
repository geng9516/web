package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Xiao Wenpeng
 *
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MenuGroupBO {

    private String url;


    private String type;

    /**
     * 菜单显示顺序
     */

    private Long orderNum;


    private String icon;


    private String perms;

    /**
     * 名称(日语)
     */
    private String jaName;

    /**
     * 名称(英语)
     */
    private String enName;

    /**
     * 名称(中文)
     */
    private String zhName;

    /**
     * 客户编号
     */
    private String customerId;

    /**
     * 公司编号
     */
    private String companyId;

    /**
     * 备注
     */
    private String remark;


    private String pageId;

    private Long menuId;

    private List<MenuBO> secondMenuList;

}
