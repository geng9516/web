package jp.smartcompany.job.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.business.GroupBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.pojo.entity.TMenuDO;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import jp.smartcompany.job.modules.core.service.ITGroupMenuService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.util.ShiroUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Xiao Wenpeng
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSessionInterceptor implements HandlerInterceptor {

    private final HttpSession httpSession;
    private final IMastSystemService iMastSystemService;
    private final GroupBusiness groupBusiness;
    private final BaseSectionBusiness baseSectionBusiness;
    private final ITGroupMenuService itGroupMenuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        // todo 根据选择的系统编号和客户编号来获取对应system下的对应信息，现在先默认为01
        String systemCode = "01";
        String customerId = "01";
        httpSession.setAttribute(Constant.SYSTEM_CODE,systemCode);
        httpSession.setAttribute(Constant.CUSTOMER_ID,customerId);
        // 默认为日本语
        String language = Constant.DEFAULT_LANGUAGE;
        List<MastSystemDO> systemList = iMastSystemService.getByLang(language);

        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        if (session==null) {
            httpSession.setAttribute(Constant.PS_SESSION, new PsSession());
        }
        if (ShiroUtil.isAuthenticated()) {
            executeLoginSequence(systemList,language);
            if (request.getAttribute(Constant.TOP_NAVS) == null) {
                loadMenus(request, systemCode, customerId, systemList);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    private void executeLoginSequence(List<MastSystemDO> systemList, String language) {
        if (CollUtil.isEmpty(systemList)){
            throw new GlobalException("Master not found");
        }
        // 获取系统角色组组
        groupBusiness.getGroupList(language,systemList);
        // 获取基点组织
        baseSectionBusiness.getBaseSectionList();
    }

    private void loadMenus(HttpServletRequest request, String systemCode, String customerId, List<MastSystemDO> systemList) {
        // 获取用户拥有的用户组（测试时注释）
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        Map<String,List<LoginGroupBO>> loginGroupList =  session.getLoginGroups();
        List<LoginGroupBO> groupList = CollUtil.newArrayList();
        systemList.forEach(system -> {
            loginGroupList.forEach((key,value)-> {
                 if (StrUtil.equals(system.getMsCsystemidPk(),key)) {
                     CollUtil.addAllIfNotContains(groupList,value);
                 }
            });
        });
        // 根据用户拥有的用户组获取对应菜单（测试时注释）
        List<String> groupCodes = groupList.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        // 测试时打开
//        List<String> groupCodes = CollUtil.newArrayList("4","7");
        List<TMenuDO> topMenus = itGroupMenuService.listTopMenuByGroupCode(groupCodes, systemCode,customerId);
        List<TMenuDO> topNavs = CollUtil.addAllIfNotContains(topMenus,CollUtil.newArrayList());
        // 第一种菜单展现方式，每一个主菜单都对应有一个主页
//        List<TMenuDO> secondMenus = itGroupMenuService.listSecondMenuByGroupCode(groupCodes, systemCode,customerId);
//        List<TMenuDO> secondNavs = CollUtil.addAllIfNotContains(secondMenus,CollUtil.newArrayList());
//        request.setAttribute(Constant.TOP_NAVS,topNavs);
//        request.setAttribute(Constant.SECOND_NAVS,secondNavs);

        // 第二种菜单展现方式，只有一级菜单存在一个主页
        List<MenuGroupBO> menuGroupList = CollUtil.newArrayList();
        for (TMenuDO topNav : topNavs) {
            MenuGroupBO menuGroupBO = new MenuGroupBO();
            menuGroupBO.setMenu(topNav);
            List<TMenuDO> secondMenuList = itGroupMenuService.listMenuByGroupCodeAndParentId(
                    topNav.getMenuId(),systemCode,customerId);
            menuGroupBO.setSecondMenuList(secondMenuList);
            menuGroupList.add(menuGroupBO);
        }
        request.setAttribute(Constant.TOP_NAVS,menuGroupList);
    }

}
