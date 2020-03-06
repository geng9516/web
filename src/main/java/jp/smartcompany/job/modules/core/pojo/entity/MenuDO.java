package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.smartcompany.job.modules.base.pojo.entity.BaseBean;
import jp.smartcompany.job.modules.core.pojo.enums.MenuType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Xiao Wenpeng
 */
@TableName("t_menu")
@ToString
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "menuId",callSuper = true)
public class MenuDO extends BaseBean {

    @TableId
    private Long menuId;
    private Long parentId;
    private String name;
    private String url;
    @TableField(value="type")
    private MenuType type;
    private String level;
    private String icon;
    private Integer orderNum;
    private Integer componentName;
    private String toChildUrl;
    private String perms;
    private String remark;

}
