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

    private MenuBO menu;
    private List<MenuBO> secondMenuList;

}
