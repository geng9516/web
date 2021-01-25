package jp.smartcompany.boot.advice;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.configuration.security.access.HasUrlPermission;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author xiao wenpeng
 */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BaseControllerAdvice {

    private final TimedCache<String,Object> timedCache;
    private final SecurityProperties securityProperties;
    private final HasUrlPermission hasUrlPermission;

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        HttpSession session =  ContextUtil.getSession();
        model.addAttribute("enableSSL",securityProperties.getSsl());
        if (session != null) {
            // 如果tmgreferlist里不存在系统时间，则设置一个默认时间
            String currentDate = (String) session.getAttribute(TmgReferList.SESSION_KEY_CURRENT_DATE);
            if (currentDate == null) {
                session.setAttribute(TmgReferList.SESSION_KEY_CURRENT_DATE, SysUtil.transDateToString(DateUtil.date()));
            }
            // 如果是登录用户则为其加载系统菜单
            if (SecurityUtil.isAuthenticated()) {
                List<MenuGroupBO> leftSidebarList = (List<MenuGroupBO>) timedCache.get(Constant.getSessionMenuId(session.getId()), false);
                if (CollUtil.isNotEmpty(leftSidebarList)) {
                    model.addAttribute(Constant.TOP_NAVS, leftSidebarList);
                    return;
                }
                leftSidebarList = hasUrlPermission.loadSystemMenu(session);
                model.addAttribute(Constant.TOP_NAVS, leftSidebarList);
            }
        }
    }

}
