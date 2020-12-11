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
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

    private String[] urlList;

    @PostConstruct
    public void init() {
        urlList = securityProperties.getOnlyAuthenticationList();
        matcher.setCachePatterns(true);
    }

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) throws SQLException {
        String siteId = request.getParameter("psSite");
//        String appId = request.getParameter("psAppId");
        HttpSession httpSession = request.getSession(false);
        // 根据psSite和psApp查询出当前登录用户在当前模块可访问的url
        boolean hasPermission = false;

        String requestUrl = request.getRequestURI();


        if (SecurityUtil.isAuthenticated()) {

            loadUserBasicInfo(request, httpSession);

            for (String urlPattern : urlList) {
                boolean matchResult = matcher.match(urlPattern,requestUrl);
                log.info("当前request url:{},比对url:{},匹配结果:{}",requestUrl,urlPattern,matcher.match(urlPattern,requestUrl));
                if (matchResult) {
                    hasPermission = true;
                    break;
                // 如果是承认site而且是承认者才有权访问
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

    private void loadUserBasicInfo(HttpServletRequest request, HttpSession httpSession) throws SQLException {
        List<MastSystemDO> systemList = (List<MastSystemDO>)lruCache.get(Constant.SYSTEM_LIST);

        // 初始化PsSession对象
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        if (session==null) {
            session = new PsSession();
            httpSession.setAttribute(Constant.PS_SESSION, session);
            session.setLoginCustomer("01");
        }

        if (systemList==null) {
            // 不再读取数据库，默认就只有本系统
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
//            systemList =  iMastSystemService.getByLang(language);
            lruCache.put(Constant.SYSTEM_LIST,systemList);
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

        if (timedCache.get(Constant.getSessionMenuId(httpSession.getId()),false) == null) {
            loadMenus(systemList, request.getSession(false));
        }
    }


    // 加载系统菜单
    private void loadMenus( List<MastSystemDO> systemList,HttpSession httpSession) {
        String systemCode = "01";
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
        List<MenuGroupBO> menuGroupList = authBusiness.getUserPerms(systemCode,session.getLanguage(),groupCodes,(Boolean)httpSession.getAttribute(Constant.IS_APPROVER));
        timedCache.put(Constant.getSessionMenuId(httpSession.getId()),menuGroupList);
    }

    private void executeLoginSequence(List<MastSystemDO> systemList, HttpSession httpSession) {
        // 判断是否是承认者
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

            // 職員番号
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

}
