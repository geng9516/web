package jp.smartcompany.boot.configuration.security.access.impl;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.NumberHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.configuration.security.access.HasUrlPermission;
import jp.smartcompany.boot.configuration.security.dto.SmartUserDetails;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.business.GroupBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("hasUrlPermission")
@RequiredArgsConstructor
@Slf4j
public class HasUrlPermissionImpl implements HasUrlPermission {

    private final DataSource dataSource;
    private final SecurityProperties securityProperties;
    private final TimedCache<String,Object> timedCache;
    private final LRUCache<Object,Object> lruCache;
    private final AuthBusiness authBusiness;
    private final GroupBusiness groupBusiness;
    private final BaseSectionBusiness baseSectionBusiness;
    private final AntPathMatcher matcher = new AntPathMatcher();
    private final IMastGroupapppermissionService mastGroupapppermissionService;

    private String[] urlList;

    @PostConstruct
    public void init() {
        urlList = securityProperties.getOnlyAuthenticationList();
        matcher.setCachePatterns(true);
    }

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String siteId = request.getParameter("psSite");
        String appId = request.getParameter("psApp");
        HttpSession httpSession = request.getSession(false);
        // ??????psSite???psApp??????????????????????????????????????????????????????url
        boolean hasPermission = false;

        String requestUrl = request.getRequestURI();

        if (SecurityUtil.isAuthenticated()) {

            loadUserBasicInfo(httpSession);

            // ?????????????????????????????????????????????????????????????????????????????????????????????
            if (StrUtil.isNotBlank(appId)) {
                List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
                List<String> groupIds = authorities.stream().map(SimpleGrantedAuthority::toString).collect(Collectors.toList());
                // ??????????????????????????????group??????????????????
                if (CollUtil.isEmpty(authorities)) {
                    return hasPermission;
                }
                List<String> grantFlagList = mastGroupapppermissionService.getUrlPermFlags(groupIds, siteId + "_" + appId);
                if (CollUtil.contains(grantFlagList, "2")) {
                    return false;
                } else {
                    for (String grantFlag : grantFlagList) {
                        if (StrUtil.equals("1", grantFlag)) {
                            hasPermission = true;
                            break;
                        }
                    }
                    return hasPermission;
                }
            }

            for (String urlPattern : urlList) {
                boolean matchResult = matcher.match(urlPattern,requestUrl);
                log.info("??????request url:{},??????url:{},????????????:{}",requestUrl,urlPattern,matcher.match(urlPattern,requestUrl));
                if (matchResult) {
                    hasPermission = true;
                    break;
                // ???????????????site?????????????????????????????????
                } else if (StrUtil.equals(siteId, TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                    boolean isApprover = (Boolean)httpSession.getAttribute(Constant.IS_APPROVER);
                    if (isApprover) {
                        hasPermission = true;
                        break;
                    }
                } else if (StrUtil.isBlank(siteId)) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }

    @Override
    public List<MastSystemDO> getSystemList() {
        List<MastSystemDO> systemList = (List<MastSystemDO>)lruCache.get(Constant.SYSTEM_LIST);
        if (CollUtil.isEmpty(systemList)) {
            // ????????????????????????????????????????????????
            MastSystemDO system = new MastSystemDO();
            system.setMsId(1000000000L);
            system.setMsCsystemidPk("01");
            system.setMsCsystemname("SmartPublic-WM");
            system.setMsCsystemnameja("SmartPublic-WM");
            system.setMsCsystemname01("SmartPublic-WM");
            system.setMsCsystemname02("SmartPublic-WM");
            system.setMsClanguage("ja");
            system.setMsNtype(1L);
            systemList = CollUtil.newArrayList(system);
            lruCache.put(Constant.SYSTEM_LIST,systemList);
        }
        return systemList;
    }

    private void loadUserBasicInfo(HttpSession httpSession) {
        List<MastSystemDO> systemList = getSystemList();

        // ?????????PsSession??????
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        if (session==null) {
            session = new PsSession();
            httpSession.setAttribute(Constant.PS_SESSION, session);
            session.setLoginCustomer("01");
        }

        MastSystemDO systemDO = systemList.get(0);
        session.setLanguage(systemDO.getMsClanguage());
        session.setLoginCompany(systemDO.getMsCsystemidPk());
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

        executeLoginSequence(systemList, httpSession);

    }

    // ??????????????????
    @Override
    public List<MenuGroupBO> loadSystemMenu(HttpSession httpSession) {
        String systemCode = "01";
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        Map<String,List<LoginGroupBO>> loginGroupList = session.getLoginGroups();
        if (CollUtil.isEmpty(loginGroupList)) {
            groupBusiness.getGroupList("ja",getSystemList(),httpSession);
            session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
            loginGroupList = session.getLoginGroups();
        }
        List<LoginGroupBO> groupList = CollUtil.newArrayList();
        for (MastSystemDO system : getSystemList()) {
            loginGroupList.forEach((key, value) -> {
                if (StrUtil.equals(system.getMsCsystemidPk(), key)) {
                    CollUtil.addAllIfNotContains(groupList, value);
                }
            });
        }
        // ?????????????????????????????????????????????????????????????????????
        List<String> groupCodes = groupList.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        List<MenuGroupBO> menuGroupList = authBusiness.getUserPerms(systemCode,session.getLanguage(),groupCodes,(Boolean)httpSession.getAttribute(Constant.IS_APPROVER));
        timedCache.put(Constant.getSessionMenuId(httpSession.getId()),menuGroupList);
        return menuGroupList;
    }

    private void executeLoginSequence(List<MastSystemDO> systemList, HttpSession httpSession) {
        // ????????????????????????
        String empId = SecurityUtil.getLoginUser().getHdCemployeeidCk();
        String now = SysUtil.transDateToString(DateUtil.date());
        Boolean isApprover = (Boolean)httpSession.getAttribute(Constant.IS_APPROVER);
        if (isApprover==null) {
            int countValue;
            try (Connection connection = dataSource.getConnection()) {
                String countEvaluator = "SELECT COUNT(TEV_CEMPLOYEEID) as count FROM TMG_EVALUATER WHERE TEV_CEMPLOYEEID = '" + empId + "' AND TEV_DSTARTDATE <= '" + now + "' AND TEV_DENDDATE >= '" + now + "'";
                Number count = SqlExecutor.query(connection, countEvaluator, new NumberHandler());
                countValue = count.intValue();
                httpSession.setAttribute(Constant.IS_APPROVER, countValue > 0);
            } catch(SQLException e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
        groupBusiness.getGroupList("ja",systemList,httpSession);
        baseSectionBusiness.getBaseSectionList(httpSession);
    }

    private List<Designation> getDesignationList(List<SmartUserDetails> lAccountInfoList) {
        /*
         * ???????????????????????????List??????????????? ???PsSession????????????ArrayList????????????????????????????????????
         */
        List<Designation> lDesignationList = CollUtil.newArrayList();
        for (SmartUserDetails accountInfo : lAccountInfoList) {
            Designation designation = new Designation();

            // ???????????????
            designation.setCustomerCode(accountInfo.getHdCcustomeridCk());

            // ???????????????
            designation.setCompanyCode(accountInfo.getHdCcompanyidCk());

            // ???????????????????????????
            designation.setCompanyHierarchy(accountInfo.getMacClayeredcompanyid());

            // ???????????????
            designation.setCompanyOrder(accountInfo.getMacNseq().toString());

            // ????????????
            designation.setCompanyName(accountInfo.getMacCcompanyname());

            // ????????????
            designation.setEmployee(accountInfo.getHdCemployeeidCk());

            // ?????????ID
            designation.setUserid(accountInfo.getHdCuserid());

            // ??????
            designation.setName(accountInfo.getMeCemployeename());

            // ????????????
            designation.setNameKana(accountInfo.getMeCkananame());

            // ??????(??????)?????????
            designation.setSection(accountInfo.getHdCsectionidFk());

            // ???????????????????????????
            designation.setSectionHierarchy(accountInfo.getMoClayeredsectionid());

            // ???????????????
            designation.setSectionOrder(accountInfo.getMoNseq().toString());

            // ????????????
            designation.setSectionName(accountInfo.getMoCsectionname());

            // ???????????????
            designation.setPostCode(accountInfo.getHdCpostidFk());

            // ????????????
            designation.setPostRank(accountInfo.getMapNweightage());

            // ????????????
            designation.setPostName(accountInfo.getMapCpostname());

            // ??????????????????
            designation.setAttachRole(accountInfo.getHdCifkeyoradditionalrole());

            // ??????????????????
            designation.setPersonnelChangesBigin(accountInfo.getHdDstartdateCk());

            // ??????????????????
            designation.setBossOrNot(accountInfo.getHdCbossornot());

            // ???????????????????????????
            lDesignationList.add(designation);

        }
        return lDesignationList;
    }

}
