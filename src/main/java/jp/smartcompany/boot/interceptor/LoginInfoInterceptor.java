package jp.smartcompany.boot.interceptor;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.logic.impl.GroupAppManagerMainLogicImpl;
import jp.smartcompany.admin.searchrangemanager.logic.impl.SearchRangeMangerLogicImpl;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupDO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.modules.core.service.ITmgGroupService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsDBBeanUtil;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginInfoInterceptor implements HandlerInterceptor {


//    private final IMastSystemService iMastSystemService;
    private final LRUCache<Object,Object> lruCache;
    private final TimedCache<String,Object> timedCache;

    private final IMastOrganisationService organisationService;
    private final ITmgGroupService groupService;
    private final IMastEmployeesService employeesService;

    private final PsDBBeanUtil psDBBeanUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (SecurityUtil.isAuthenticated()) {
            Boolean passwordExpired = (Boolean) timedCache.get(SecurityUtil.getUsername() + "passwordExpired", true);
            // ????????????????????????????????????????????????????????????????????????????????????
            if (passwordExpired != null && passwordExpired) {
                if (!StrUtil.containsAny(requestUri, "changeExpirePassword", "/expirePassword")) {
                    request.setAttribute("username", SecurityUtil.getUsername());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/expirePassword");
                    dispatcher.forward(request, response);
                    return false;
                }
            }
        }
        HttpSession httpSession = request.getSession(false);
        ContextUtil.add(new PsDBBean());
        // ??????????????????
        if (httpSession == null) {
            return true;
        }
        String systemCode = (String)httpSession.getAttribute(Constant.SYSTEM_CODE);
        // customerId?????????01
        String customerId = "01";

        List<MastSystemDO> systemList = (List<MastSystemDO>)lruCache.get(Constant.SYSTEM_LIST);
        if (StrUtil.isBlank(systemCode)) {
            systemCode = systemList.get(0).getMsCsystemidPk();
            // ??????customerId??????01
            httpSession.setAttribute(Constant.SYSTEM_CODE, systemCode);
            httpSession.setAttribute(Constant.CUSTOMER_ID, customerId);
        }

        // ?????????PsSession??????
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        if (session==null) {
            session = new PsSession();
            httpSession.setAttribute(Constant.PS_SESSION, session);
            session.setLoginCustomer(customerId);
        }
        configGlobalParameters(request,systemList.get(0));
        // ????????????????????????????????????????????????????????????
        if (SecurityUtil.isAuthenticated()) {
            String sessionId = httpSession.getId();
            // ?????????????????????????????????????????????????????????????????????????????????
            if (!requestUri.contains("groupappmanager")) {
                timedCache.remove(GroupAppManagerMainLogicImpl.REQ_SCOPE_NAME+"_"+sessionId);
            }
            if (!requestUri.contains("searchrangemanager")) {
                timedCache.remove(SearchRangeMangerLogicImpl.REQ_SCOPE_NAME+"_"+sessionId);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ContextUtil.removeDbBean();
    }

    // ??????PsDBBean???PsSession
    public void configGlobalParameters(HttpServletRequest request,MastSystemDO mastSystemDO) {
        HttpSession httpSession = request.getSession(false);
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        // ????????????????????????PsSession??????????????????????????????
        Hashtable<String,Object> hashtable = new Hashtable<>();

        // ??????PsDBBean????????????
        String compCode = request.getParameter("CompCode");
        String groupCode = request.getParameter("GroupCode");
        String userCode = request.getParameter("UserCode");
        String custId = request.getParameter("CustID");
        String replacedCompCode = request.getParameter("ReplacedCompCode");
        String replacedUserCode = request.getParameter("ReplacedUserCode");
        String employeeCode = request.getParameter("EmployeeCode");
        // ???????????????
        String standardDate = request.getParameter("txtDYYYYMM");
        // ???????????????
        String standardDay = request.getParameter("txtDYYYYMMDD");
        // ??????action
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
            httpSession.setAttribute("txtDYYYYMMDD",standardDay);;
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

        // ??????Site????????????????????????
        String targetAdminSection = request.getParameter(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        String targetAdminEmp = request.getParameter(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP);

        // ??????Site????????????????????????
        String targetPermSection = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
        String targetPermEmp = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP);
        String targetPermGroup = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        String selectedView = request.getParameter(TmgReferList.TREEVIEW_KEY_PERM_SELECTED_VIEW);

        // ????????????????????????
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

        // ???????????????
        String recordDate = request.getParameter(TmgReferList.TREEVIEW_KEY_RECORD_DATE);
        if (StrUtil.isNotBlank(recordDate)) {
            hashtable.put(TmgReferList.TREEVIEW_KEY_RECORD_DATE,recordDate);
        }

        // --- start --- ?????????session??????site??????
        // psDBBean??????targetDept???targetUser?????????????????????site?????????id?????????id
        if (StrUtil.isNotBlank(targetAdminSection)){
            hashtable.put(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION,targetAdminSection);
            psDBBean.setTargetDept(targetAdminSection);
            // ????????????site???????????????id???session???
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
            // ????????????site???????????????id???session???
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

            // ????????????site???????????????id???session???
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
            // ????????????site???????????????id???session???
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
        // --- end --- ?????????session??????site??????


        String siteId = request.getParameter(PsConst.PARAM_KEY_SITEID);
        if (StrUtil.isNotBlank(siteId)) {
            hashtable.put("SiteId", siteId);
        }
        String appId = request.getParameter(PsConst.PARAM_KEY_APPID);
        if (StrUtil.isNotBlank(appId)) {
            hashtable.put("AppId",appId);
        }
        psDBBeanUtil.setSysControl(hashtable,psDBBean);
        request.setAttribute("BeanName",psDBBean);

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
