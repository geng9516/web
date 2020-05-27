package jp.smartcompany.job.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.business.GroupBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.pojo.entity.TMenuDO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import jp.smartcompany.job.modules.core.service.ITGroupMenuService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.util.ShiroUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Xiao Wenpeng
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginInterceptor implements HandlerInterceptor {

    private final HttpSession httpSession;
    private final IMastSystemService iMastSystemService;
    private final GroupBusiness groupBusiness;
    private final BaseSectionBusiness baseSectionBusiness;
    private final ITGroupMenuService itGroupMenuService;
    private final IMastEmployeesService iMastEmployeesService;
    private final PsDBBean psDBBean;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        // 默认为日本语
        String language = Constant.DEFAULT_LANGUAGE;
        String systemCode = (String)httpSession.getAttribute(Constant.SYSTEM_CODE);
        // customerId固定为01
        String customerId = "01";
        List<MastSystemDO> systemList = iMastSystemService.getByLang(language);
        if (StrUtil.isBlank(systemCode)) {
            systemCode = systemList.get(0).getMsCsystemidPk();
            // 默认customerId都为01
            httpSession.setAttribute(Constant.SYSTEM_CODE, systemCode);
            httpSession.setAttribute(Constant.CUSTOMER_ID, customerId);
        }
        // 初始化PsSession对象
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        if (session==null) {
            session = new PsSession();
            httpSession.setAttribute(Constant.PS_SESSION, session);
            session.setLoginCustomer(customerId);
        }
        // 如果是登录用户，则执行登录后的一系列逻辑
        if (ShiroUtil.isAuthenticated()) {
            executeLoginSequence(systemList,language);
            if (request.getAttribute(Constant.TOP_NAVS) == null) {
                loadMenus(request, systemCode, customerId, systemList);
            }
        }
        configGlobalParameters(request,systemList.get(0));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    // 设置PsDBBean和PsSession
    public void configGlobalParameters(HttpServletRequest request,MastSystemDO mastSystemDO) {
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        session.setLanguage(mastSystemDO.getMsClanguage());
        session.setLoginCompany(mastSystemDO.getMsCsystemidPk());
        // 登录后且还未设置PsSession里的值则需要进行设置
        Hashtable<String,Object> hashtable = new Hashtable<>();
        if (ShiroUtil.isAuthenticated()){
            MastAccountDO account = ShiroUtil.getLoginUser();
            if (StrUtil.isNotBlank(account.getMaCaccount())) {
                session.setLoginAccount(account.getMaCaccount());
                session.setLoginCompany(mastSystemDO.getMsCsystemidPk());
                session.setLoginUser(account.getMaCuserid());
                session.setLoginCustomer(account.getMaCcustomerid());
                List<MastEmployeesDO> employeesDOList = iMastEmployeesService.selectEmployByLoginUserId(account.getMaCcustomerid(),mastSystemDO.getMsCsystemidPk(),account.getMaCuserid(), DateUtil.date());
                if (CollUtil.isNotEmpty(employeesDOList)){
                    MastEmployeesDO employ = employeesDOList.get(0);
                    session.setLoginKanjiName(employ.getMeCkanjiname());
                    session.setLoginEmployee(employ.getMeCemployeeidCk());
                }
            }
        }

        // 设置PsDBBean公共参数
        String compCode = request.getParameter("CompCode");
        String groupCode = request.getParameter("GroupCode");
        String userCode = request.getParameter("UserCode");
        String custId = request.getParameter("CustID");
        String replacedCompCode = request.getParameter("ReplacedCompCode");
        String replacedUserCode = request.getParameter("ReplacedUserCode");
        String employeeCode = request.getParameter("EmployeeCode");

        if (StrUtil.isNotBlank(replacedCompCode)){
            hashtable.put("ReplacedCompCode",replacedCompCode);
        }
        if (StrUtil.isNotBlank(replacedUserCode)){
            hashtable.put("ReplacedUserCode",replacedUserCode);
        }

        String strEmployeeCode ="";
        if (StrUtil.isNotBlank(employeeCode)){
            strEmployeeCode = employeeCode;
        } else if (StrUtil.isNotBlank(session.getLoginEmployee())){
            strEmployeeCode=session.getLoginEmployee();
        }

        hashtable.put("EmployeeCode",strEmployeeCode);

        String strCompCode="";
        if (StrUtil.isNotBlank(compCode)) {
            strCompCode = compCode;
        } else if ( StrUtil.isNotBlank(session.getLoginCompany())){
            strCompCode = session.getLoginCompany();
        }
        hashtable.put("CompCode",strCompCode);


        if (StrUtil.isNotBlank(groupCode)) {
            hashtable.put("GroupCode", groupCode);
        }

        String strUserCode = "";
        if (StrUtil.isNotBlank(userCode)){
            strUserCode = userCode;
        } else if (StrUtil.isNotBlank(session.getLoginUser())) {
            strUserCode = session.getLoginUser();
        }
        hashtable.put("UserCode",strUserCode);

        String strCustId = "";
        if (StrUtil.isNotBlank(custId)){
            strCustId = custId;
        } else if (StrUtil.isNotBlank(session.getLoginCustomer())) {
            strCustId = session.getLoginCustomer();
        }
        hashtable.put("CustID",strCustId);

        hashtable.put("Language",mastSystemDO.getMsClanguage());
        hashtable.put("SystemCode",mastSystemDO.getMsCsystemidPk());

        String targetComp = request.getParameter("targetComp");
        if (StrUtil.isNotBlank(targetComp )) {
            hashtable.put("targetComp",targetComp);
        }
        String sectionid = request.getParameter("sectionid");
        if (StrUtil.isNotBlank(sectionid)) {
            hashtable.put("sectionid",sectionid);
        }
        String compid = request.getParameter("compid");
        if (StrUtil.isNotBlank(compid)) {
            hashtable.put("compid",compid);
        } else if (StrUtil.isNotBlank(session.getLoginCompany())){
            hashtable.put("compid",session.getLoginCompany());
        }

        String custid = request.getParameter("custid");
        if (StrUtil.isNotBlank(custid)){
            hashtable.put("custid",custId);
        } else if (StrUtil.isNotBlank(session.getLoginCustomer())){
            hashtable.put("custid",session.getLoginCustomer());
        }

        String targetSection = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
        String targetEmp = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP);
        String targetGroup = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);

        if (StrUtil.isNotBlank(targetSection)){
            hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION,targetSection);
        }
        if (StrUtil.isNotBlank(targetEmp)){
            hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP,targetEmp);
        }
        if (StrUtil.isNotBlank(targetGroup)){
            hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP,targetGroup);
        }

        psDBBean.setSysControl(hashtable);

    }

    // 登录后加载角色组和基点组织
    private void executeLoginSequence(List<MastSystemDO> systemList, String language) {
        if (CollUtil.isEmpty(systemList)){
            throw new GlobalException("Master not found");
        }
        // 获取系统角色组
        groupBusiness.getGroupList(language,systemList);
        // 获取基点组织
        baseSectionBusiness.getBaseSectionList();
    }

    // 加载系统菜单
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
