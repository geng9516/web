package jp.smartcompany.boot.configuration.security.filter;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.configuration.security.dto.SmartUserDetails;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class LoginInfoFilter extends OncePerRequestFilter {

  private final LRUCache<Object,Object> lruCache;
  private final IMastSystemService systemService;
  private final TimedCache<String,Object> timedCache;
  private final AuthBusiness authBusiness;
  private final ITmgGroupService groupService;
  private final IMastEmployeesService employeesService;
  private final IMastOrganisationService organisationService;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
    if (StrUtil.endWithAny(req.getRequestURI(),".js",".css",".ico",".json",".moc","mtn")) {
      chain.doFilter(req, resp);
      return;
    }
    ContextUtil.add(new PsDBBean());
    HttpSession httpSession = req.getSession();
    String systemCode = (String)httpSession.getAttribute(Constant.SYSTEM_CODE);
    // customerId固定为01
    String customerId = "01";
    // 默认为日本语
    String language = Constant.DEFAULT_LANGUAGE;

    List<MastSystemDO> systemList = (List<MastSystemDO>)lruCache.get(Constant.SYSTEM_LIST);
    if (systemList==null) {
      systemList =  systemService.getByLang(language);
      lruCache.put(Constant.SYSTEM_LIST,systemList);
    }
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
    configGlobalParameters(req,systemList.get(0));
    // 如果是登录用户，则执行登录后的一系列逻辑
    if (SecurityUtil.isAuthenticated()) {
      if (timedCache.get(Constant.TOP_NAVS+"_"+httpSession.getId(),true) == null) {
        loadMenus(systemCode, systemList,httpSession);
      }

      Boolean passwordExpired = (Boolean)timedCache.get(SecurityUtil.getUsername()+"passwordExpired",true);
      // 如果密码过期且不是访问修改密码的接口则跳转到密码过期页面
      if (passwordExpired!=null && passwordExpired && !req.getRequestURI().contains("changeExpirePassword")) {
          req.setAttribute("username",SecurityUtil.getUsername());
          RequestDispatcher dispatcher = req.getRequestDispatcher("/expirePassword");
          dispatcher.forward(req, resp);
          return;
      }
    }
    chain.doFilter(req, resp);
  }

  // 设置PsDBBean和PsSession
  public void configGlobalParameters(HttpServletRequest request, MastSystemDO mastSystemDO) {
    HttpSession httpSession = request.getSession();
    PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
    session.setLanguage(mastSystemDO.getMsClanguage());
    session.setLoginCompany(mastSystemDO.getMsCsystemidPk());
    // 登录后且还未设置PsSession里的值则需要进行设置
    Hashtable<String,Object> hashtable = new Hashtable<>();
    if (SecurityUtil.isAuthenticated()){
      SmartUserDetails account = SecurityUtil.getLoginUser();
      String username = account.getUsername();
      if (StrUtil.isNotBlank(username)) {
        session.setLoginAccount(username);
        session.setLoginCompany(account.getHdCcompanyidCk());
        session.setLoginUser(username);
        session.setLoginCustomer(account.getHdCcustomeridCk());
        session.setLoginKanjiName(account.getMeCemployeename());
        session.setLoginEmployee(account.getHdCemployeeidCk());
        session.setWorkTypeName(account.getWorkTypeName());
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
      httpSession.setAttribute("txtDYYYYMM",standardDate);
    }
    if (StrUtil.isNotBlank((standardDay))) {
      hashtable.put("txtDYYYYMMDD",standardDay);
      httpSession.setAttribute("txtDYYYYMMDD",standardDay);
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

    PsDBBean psDBBean = ContextUtil.getDbBean();
    psDBBean.setTargetComp(session.getLoginCompany());
    psDBBean.setTargetCust(session.getLoginCustomer());

    String selectedTab = request.getParameter(TmgReferList.TREEVIEW_OBJ_HIDSELECT);

    // 管理Site的选中员工和部门
    String targetAdminSection = request.getParameter(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
    String targetAdminEmp = request.getParameter(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP);

    // 承认Site的选中员工和部门
    String targetPermSection = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
    String targetPermEmp = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP);
    String targetPermGroup = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
    String selectedView = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_SELECTED_VIEW);

    // 树状图搜索用参数
    String searchItems = request.getParameter(TmgReferList.TREEVIEW_OBJ_HIDSEARCHITEMES);
    String searchCondition = request.getParameter(TmgReferList.TREEVIEW_OBJ_HIDSEARCHCONDITION);
    String searchData = request.getParameter(TmgReferList.TREEVIEW_OBJ_HIDSEARCHDATA);

    if (StrUtil.isNotBlank(searchItems)) {
      hashtable.put(TmgReferList.TREEVIEW_OBJ_HIDSEARCHITEMES,searchItems);
    }
    if (StrUtil.isNotBlank(searchCondition)) {
      hashtable.put(TmgReferList.TREEVIEW_OBJ_HIDSEARCHCONDITION,searchCondition);
    }
    if (StrUtil.isNotBlank(searchData)) {
      hashtable.put(TmgReferList.TREEVIEW_OBJ_HIDSEARCHDATA,searchData);
    }
    if (StrUtil.isNotBlank(selectedView)) {
      hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_SELECTED_VIEW,selectedView);
    }
    if (StrUtil.isNotBlank(selectedTab)) {
      hashtable.put(TmgReferList.TREEVIEW_OBJ_HIDSELECT,selectedTab);
    }

    // 部门基准日
    String recordDate = request.getParameter(TmgReferList.TREEVIEW_KEY_RECORD_DATE);
    if (StrUtil.isNotBlank(recordDate)) {
      hashtable.put(TmgReferList.TREEVIEW_KEY_RECORD_DATE,recordDate);
    }

    // --- start --- 保存在session中的site数据
    // psDBBean中的targetDept和targetUser是跨承认和管理site的部门id和用户id
    if (StrUtil.isNotBlank(targetAdminSection)){
      hashtable.put(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION,targetAdminSection);
      psDBBean.setTargetDept(targetAdminSection);
      // 保存管理site选中的部门id到session中
      String sessionAdminTargetSection = (String)httpSession.getAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
      if (StrUtil.isBlank(sessionAdminTargetSection)) {
        saveOrgName(targetAdminSection, TmgUtil.Cs_SITE_ID_TMG_ADMIN,httpSession);
      } else {
        if (!StrUtil.equals(sessionAdminTargetSection, targetAdminSection)){
          saveOrgName(targetAdminSection,TmgUtil.Cs_SITE_ID_TMG_ADMIN,httpSession);
        }
      }
    }
    if (StrUtil.isNotBlank(targetAdminEmp)){
      hashtable.put(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP,targetAdminEmp);
      psDBBean.setTargetUser(targetAdminEmp);
      // 保存管理site选中的员工id到session中
      String sessionAdminTargetEmp = (String)httpSession.getAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP);
      if (StrUtil.isBlank(sessionAdminTargetEmp)) {
        saveEmpName(targetAdminEmp, TmgUtil.Cs_SITE_ID_TMG_ADMIN,httpSession);
      } else {
        if (!StrUtil.equals(sessionAdminTargetEmp, targetAdminEmp)) {
          saveEmpName(targetAdminEmp, TmgUtil.Cs_SITE_ID_TMG_ADMIN,httpSession);
        }
      }
    }


    if (StrUtil.isNotBlank(targetPermSection)) {
      hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION,targetPermSection);
      psDBBean.setTargetDept(targetPermSection);

      // 保存承认site选中的部门id到session中
      String sessionPermTargetSection = (String)httpSession.getAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
      if (StrUtil.isBlank(sessionPermTargetSection)) {
        saveOrgName(targetPermSection,TmgUtil.Cs_SITE_ID_TMG_PERM,httpSession);
      } else {
        if (!StrUtil.equals(sessionPermTargetSection, targetAdminSection)){
          saveOrgName(targetPermSection,TmgUtil.Cs_SITE_ID_TMG_PERM,httpSession);
        }
      }
    }
    if (StrUtil.isNotBlank(targetPermEmp)) {
      hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP,targetPermEmp);
      psDBBean.setTargetUser(targetPermEmp);
      // 保存承认site选中的员工id到session中
      String sessionPermTargetEmp = (String)httpSession.getAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP);
      if (StrUtil.isBlank(sessionPermTargetEmp)) {
        saveEmpName(targetPermEmp, TmgUtil.Cs_SITE_ID_TMG_PERM,httpSession);
      } else {
        if (!StrUtil.equals(sessionPermTargetEmp, targetPermEmp)) {
          saveEmpName(targetPermEmp, TmgUtil.Cs_SITE_ID_TMG_PERM,httpSession);
        }
      }
    }

    if (StrUtil.isNotBlank(targetPermGroup)) {
      hashtable.put(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP,targetPermGroup);
      String sessionPermTargetGroup = (String)httpSession.getAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
      if (StrUtil.isBlank(sessionPermTargetGroup)) {
        saveGroupName(targetPermGroup, TmgUtil.Cs_SITE_ID_TMG_PERM,httpSession);
      } else {
        if (!StrUtil.equals(sessionPermTargetGroup, sessionPermTargetGroup)) {
          saveGroupName(targetPermGroup, TmgUtil.Cs_SITE_ID_TMG_PERM,httpSession);
        }
      }
    }
    // --- end --- 保存在session中的site数据


    String siteId = request.getParameter(PsConst.PARAM_KEY_SITEID);
    if (StrUtil.isNotBlank(siteId)) {
      hashtable.put("SiteId", siteId);
    }
    String appId = request.getParameter(PsConst.PARAM_KEY_APPID);
    if (StrUtil.isNotBlank(appId)) {
      hashtable.put("AppId",appId);
    }
    psDBBean.setSysControl(hashtable);
    request.setAttribute("BeanName",psDBBean);

  }

  public List<Designation> getDesignationList(List<SmartUserDetails> lAccountInfoList) {
    /*
     * 検索結果を異動歴のListに設定する ※PsSession側の型がArrayListのため、そちらに合わせる
     */
    List<Designation> lDesignationList = CollUtil.newArrayList();
    for (SmartUserDetails accountInfo : lAccountInfoList) {
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

  // 加载系统菜单
  private void loadMenus(String systemCode,  List<MastSystemDO> systemList,HttpSession httpSession) {
    PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
    Map<String,List<LoginGroupBO>> loginGroupList = session.getLoginGroups();
    List<LoginGroupBO> groupList = CollUtil.newArrayList();
    systemList.forEach(system ->
            loginGroupList.forEach((key,value)-> {
              if (StrUtil.equals(system.getMsCsystemidPk(),key)) {
                CollUtil.addAllIfNotContains(groupList,value);
              }
            })
    );
    // 根据用户拥有的用户组获取对应菜单（测试时注释）
    List<String> groupCodes = groupList.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
    List<MenuGroupBO> menuGroupList = authBusiness.getUserPerms(systemCode,session.getLanguage(),groupCodes,session.getLoginEmployee());
    timedCache.put(Constant.TOP_NAVS+"_"+httpSession.getId(),menuGroupList);
  }

  private void saveOrgName(String sectionId,String siteId,HttpSession httpSession) {
    List<MastOrganisationDO> orgList = organisationService.list(SysUtil.<MastOrganisationDO>query().eq("MO_CSECTIONID_CK", sectionId).select("MO_CSECTIONNAME"));
    if (CollUtil.isNotEmpty(orgList)) {
      if (StrUtil.equals(siteId,TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION, sectionId);
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION_NAME, orgList.get(0).getMoCsectionname());
      }
      if (StrUtil.equals(siteId,TmgUtil.Cs_SITE_ID_TMG_PERM)) {
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION, sectionId);
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION_NAME, orgList.get(0).getMoCsectionname());
      }
    }
  }

  private void saveGroupName(String groupId,String siteId,HttpSession httpSession) {
    if (StrUtil.equals(siteId,TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
      List<TmgGroupDO> groupList = groupService.list(SysUtil.<TmgGroupDO>query().eq("TGR_GROUPID", groupId).select("TGR_CGROUPNAME"));
      if (CollUtil.isNotEmpty(groupList)) {
        if (StrUtil.equals(siteId,TmgUtil.Cs_SITE_ID_TMG_PERM)) {
          httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP, groupId);
          httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP_NAME, groupList.get(0).getTgrCgroupname());
        }
      }
    }
  }

  private void saveEmpName(String empId,String siteId,HttpSession httpSession) {
    List<MastEmployeesDO> orgList = employeesService.list(SysUtil.<MastEmployeesDO>query().eq("ME_CEMPLOYEEID_CK",empId).select("ME_CKANJINAME"));
    if (CollUtil.isNotEmpty(orgList)) {
      if (StrUtil.equals(siteId,TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP, empId);
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP_NAME, orgList.get(0).getMeCkanjiname());
      }
      if (StrUtil.equals(siteId,TmgUtil.Cs_SITE_ID_TMG_PERM)) {
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP, empId);
        httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP_NAME, orgList.get(0).getMeCkanjiname());
      }
    }
  }

}
