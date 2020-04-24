package jp.smartcompany.job.modules.core.business;


import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.entity.TMenuDO;
import jp.smartcompany.job.util.LevelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiao Wenpeng
 * 系统菜单Logic层
 */
@Service(CoreBean.Business.MENU)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuBusiness {

    /**
     * 获取系统顶部导航栏
      * @param allMenus
     * @return
     */
   public List<TMenuDO> listTopNavs(List<TMenuDO> allMenus) {
        return allMenus.stream().filter(menu -> menu.getParentMenuId() == Long.parseLong(LevelUtil.ROOT)).collect(Collectors.toList());
   }

}
