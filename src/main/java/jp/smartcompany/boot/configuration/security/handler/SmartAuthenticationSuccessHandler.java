package jp.smartcompany.boot.configuration.security.handler;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.business.GroupBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final SecurityProperties securityProperties;
  private final BaseSectionBusiness baseSectionBusiness;
  private final TimedCache<String,Object> timedCache;
  private final AuthBusiness authBusiness;
  private final GroupBusiness groupBusiness;
  private final LRUCache<Object,Object> lruCache;
  private final IMastSystemService systemService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
                                      Authentication auth) throws IOException, ServletException {
    HttpSession httpSession = req.getSession();
    String systemCode = (String)httpSession.getAttribute(Constant.SYSTEM_CODE);
    List<MastSystemDO> systemList = (List<MastSystemDO>)lruCache.get(Constant.SYSTEM_LIST);
    if (systemList==null) {
      systemList = systemService.getByLang("ja");
      lruCache.put(Constant.SYSTEM_LIST,systemList);
    }
    authBusiness.saveLoginInfo(true,auth.getName());
    groupBusiness.getGroupList("ja",systemList,httpSession);
    baseSectionBusiness.getBaseSectionList(httpSession);
    loadMenus(systemCode,  systemList,req);

    lruCache.remove(auth.getPrincipal()+"password");

    if (SysUtil.isAjaxRequest(req)) {
      resp.setCharacterEncoding("UTF-8");
      resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
      GlobalResponse r = GlobalResponse.ok("ログイン成功");
      resp.getWriter().write(JSONUtil.toJsonStr(r));
    } else {
//      System.out.println(req.getRequestURI());
//      System.out.println(req.getRequestURL());
//      System.out.println(req.getQueryString());
//      if (StrUtil.equalsAny(req.getRequestURI(),"/",securityProperties.getLoginSuccessUrl(),"/login")) {
//        resp.sendRedirect(securityProperties.getLoginSuccessUrl());
//      } else {
//        RequestDispatcher dispatcher = req.getRequestDispatcher(req.getRequestURI()+"?"+req.getQueryString());
//        dispatcher.forward(req, resp);
//      }
      resp.sendRedirect(securityProperties.getLoginSuccessUrl());
    }
  }

  // 加载系统菜单
  private void loadMenus(String systemCode,  List<MastSystemDO> systemList,HttpServletRequest req) {
    HttpSession httpSession = req.getSession();
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
    List<MenuGroupBO> menuGroupList = authBusiness.getUserPerms(systemCode,session.getLanguage(),groupCodes, SecurityUtil.getLoginUser().getHdCemployeeidCk());

    timedCache.put(Constant.TOP_NAVS+"_"+httpSession.getId(),menuGroupList);
  }


}
