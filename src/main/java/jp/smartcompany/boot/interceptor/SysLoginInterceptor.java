package jp.smartcompany.boot.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.framework.auth.business.AppAuthJudgmentBusiness;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.business.GroupBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.pojo.entity.TMenuDO;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import jp.smartcompany.job.modules.core.service.ITGroupMenuService;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.boot.util.ShiroUtil;
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
    private final PsDBBean psDBBean;
    private final AppAuthJudgmentBusiness appAuthJudgmentBusiness;

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
        configGlobalParameters(request,systemList.get(0));
        // 如果是登录用户，则执行登录后的一系列逻辑
        if (ShiroUtil.isAuthenticated()) {
            executeLoginSequence(systemList,language,session);
            if (request.getAttribute(Constant.TOP_NAVS) == null) {
                loadMenus(request, systemCode, customerId, systemList);
            }
        }

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
            LoginAccountBO account = ShiroUtil.getLoginUser();
            String username = account.getHdCuserid();
            if (StrUtil.isNotBlank(username)) {
                session.setLoginAccount(username);
                session.setLoginCompany(account.getHdCcompanyidCk());
                session.setLoginUser(username);
                session.setLoginCustomer(account.getHdCcustomeridCk());
                session.setLoginKanjiName(account.getMeCemployeename());
                session.setLoginEmployee(account.getHdCemployeeidCk());
                List<Designation> designationList = getDesignationList(CollUtil.newArrayList(account));
                session.setLoginDesignation(designationList);
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
        // 表示対象月
        String standardDate = request.getParameter("txtDYYYYMM");
        // 表示対象日
        String standardDay = request.getParameter("txtDYYYYMMDD");
        // 请求action
        String requestAction = request.getParameter("txtAction");

        String redirectBean = request.getParameter("txtRedirectBean");
        String redirectAction = request.getParameter("txtRedirectAction");
        String callBeanAction = request.getParameter("txtCallBeanAction");
        String executeEmpId = request.getParameter("txtExecuteEmpId");

        if (StrUtil.isNotBlank(redirectBean)) {
            hashtable.put("txtRedirectBean",redirectBean);
        }
        if (StrUtil.isNotBlank(redirectAction)) {
            hashtable.put("txtRedirectAction",redirectAction);
        }
        if (StrUtil.isNotBlank(callBeanAction)) {
            hashtable.put("txtCallBeanAction",callBeanAction);
        }
        if (StrUtil.isNotBlank(executeEmpId)){
            hashtable.put("txtExecuteEmpId",executeEmpId);
        }

        if (StrUtil.isNotBlank(requestAction)) {
            hashtable.put("txtAction",requestAction);
        }

        if (StrUtil.isNotBlank(standardDate)){
            hashtable.put("txtDYYYYMM",standardDate);
        }
        if (StrUtil.isNotBlank((standardDay))) {
            hashtable.put("txtDYYYYMMDD",standardDay);
        }

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

        psDBBean.setTargetComp(session.getLoginCompany());
        psDBBean.setTargetCust(session.getLoginCustomer());

        // 管理Site的选中员工和部门
        String targetAdminSection = request.getParameter(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        String targetAdminEmp = request.getParameter(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP);

        // 承认Site的选中员工和部门
        String targetPermSection = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
        String targetPermEmp = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP);

        // 部门基准日
        String recordDate = request.getParameter(TmgReferList.TREEVIEW_KEY_RECORD_DATE);
        if (StrUtil.isNotBlank(recordDate)) {
            hashtable.put(TmgReferList.TREEVIEW_KEY_RECORD_DATE,recordDate);
        }

        String targetGroup = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);

        if (StrUtil.isNotBlank(targetAdminSection)){
            hashtable.put(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION,targetAdminSection);
            psDBBean.setTargetDept(targetAdminSection);
        }
        if (StrUtil.isNotBlank(targetAdminEmp)){
            hashtable.put(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP,targetAdminEmp);
            psDBBean.setTargetUser(targetAdminEmp);
        }

        if (StrUtil.isNotBlank(targetPermSection)) {
            hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION,targetPermSection);
            psDBBean.setTargetDept(targetPermSection);
        }
        if (StrUtil.isNotBlank(targetPermEmp)) {
            hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP,targetPermEmp);
            psDBBean.setTargetDept(targetPermEmp);
        }

        if (StrUtil.isNotBlank(targetGroup)){
            hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP,targetGroup);
        }
        String siteId = request.getParameter("psSite");
        if (StrUtil.isNotBlank(siteId)) {
            hashtable.put("SiteId", siteId);
        }

        psDBBean.setSysControl(hashtable);
        request.setAttribute("BeanName",psDBBean);

    }

    public List<Designation> getDesignationList(List<LoginAccountBO> lAccountInfoList) {
        /*
         * 検索結果を異動歴のListに設定する ※PsSession側の型がArrayListのため、そちらに合わせる
         */
        List<Designation> lDesignationList = CollUtil.newArrayList();
        for (LoginAccountBO accountInfo : lAccountInfoList) {
            Designation designation = new Designation();

            // 顧客コード
            designation.setCustomerCode(accountInfo.getHdCcustomeridCk());

            // 法人コード
            designation.setCompanyCode(accountInfo.getHdCcompanyidCk());

            // 法人内部階層コード
            designation.setCompanyHierarchy(accountInfo.getMacClayeredcompanyid());

            // 法人並び順
            designation.setCompanyOrder(accountInfo.getMacNseq().toString());

            // 法人名称
            designation.setCompanyName(accountInfo.getMacCcompanyname());

            // 社員番号
            designation.setEmployee(accountInfo.getHdCemployeeidCk());

            // ユーザID
            designation.setUserid(accountInfo.getHdCuserid());

            // 氏名
            designation.setName(accountInfo.getMeCemployeename());

            // 氏名カナ
            designation.setNameKana(accountInfo.getMeCkananame());

            // 組織(所属)コード
            designation.setSection(accountInfo.getHdCsectionidFk());

            // 組織内部階層コード
            designation.setSectionHierarchy(accountInfo.getMoClayeredsectionid());

            // 組織並び順
            designation.setSectionOrder(accountInfo.getMoNseq().toString());

            // 組織名称
            designation.setSectionName(accountInfo.getMoCsectionname());

            // 役職コード
            designation.setPostCode(accountInfo.getHdCpostidFk());

            // 役職順位
            designation.setPostRank(accountInfo.getMapNweightage());

            // 役職名称
            designation.setPostName(accountInfo.getMapCpostname());

            // 本務兼務区分
            designation.setAttachRole(accountInfo.getHdCifkeyoradditionalrole());

            // 異動歴開始日
            designation.setPersonnelChangesBigin(accountInfo.getHdDstartdateCk());

            // 所属長フラグ
            designation.setBossOrNot(accountInfo.getHdCbossornot());

            // 異動歴リストに追加
            lDesignationList.add(designation);

        }
        return lDesignationList;
    }


    // 登录后加载角色组和基点组织
    private void executeLoginSequence(List<MastSystemDO> systemList, String language,PsSession session) {
        if (CollUtil.isEmpty(systemList)){
            throw new GlobalException("Master not found");
        }
        // 获取系统角色组
        groupBusiness.getGroupList(language,systemList);
        // アプリケーション起動判定処理実施(戻り値をセッションに設定)
        session.setLoginAppPermission(appAuthJudgmentBusiness.getAppAuthJudgmentInfo());

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
        log.info("【topNavs:{}】",topNavs);
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
