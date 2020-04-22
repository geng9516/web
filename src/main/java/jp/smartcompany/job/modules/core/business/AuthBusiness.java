package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.enums.ErrorMessage;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import jp.smartcompany.job.modules.core.service.LoginAuditService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.util.ContextUtil;
import jp.smartcompany.job.util.IpUtil;
import jp.smartcompany.job.util.ShiroUtil;
import jp.smartcompany.job.util.SysDateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.AUTH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthBusiness {

    private final IMastPasswordService iMastPasswordService;
    private final IMastAccountService iMastAccountService;
    private final LoginAuditService loginAuditService;
    private final IMastSystemService iMastSystemService;

    private final GroupBusiness groupBusiness;
    private final BaseSectionBusiness baseSectionBusiness;

    private final HttpSession httpSession;

    public boolean checkPassword(MastAccountDO account, String password) {
        Date passwordSetDate = iMastPasswordService.getUpdateDateByUsernamePassword(account.getMaCuserid(),password);
        // 密码错误
        if (passwordSetDate == null) {
// todo: 判断尝试重新登录次数
//            int retryCount = account.getMaNretrycounter();
//            if () {
//                account.setMaNpasswordlock(1).setMaDmodifieddate(DateUtil.date());
//                iMastAccountService.updateById(account);
//            }
//            account.setMaNretrycounter(retryCount + 1)
//                    .setMaNpasswordlock(0)
//                    .setMaDmodifieddate(DateUtil.date());
//            iMastAccountService.updateById(account);
            throw new IncorrectCredentialsException(ErrorMessage.PASSWORD_INVALID.msg());
        // 判断密码是否已经过了使用期限
        } else {
            if (SysDateUtil.isLess(passwordSetDate, DateUtil.date())) {
               // 抛出密码过期异常
            }
        }
        if (account.getMaNretrycounter() > 0) {
            account.setMaNretrycounter(0).setMaCmodifieruserid(account.getMaCuserid());
            iMastAccountService.updateById(account);
        }
        return true;
    }

    public void login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, loginDTO.getPassword());
        subject.login(token);
        // 初始化session
        httpSession.setAttribute(Constant.LOGIN_INFO,new PsSession());
        executeLoginSequence();
        saveLoginInfo(true, username);
    }

    public void logout() {
        String username = ShiroUtil.getUsername();
        saveLoginInfo(true, username);
        ShiroUtil.getSubject().logout();
    }

    private void saveLoginInfo(boolean status,String username) {
        LoginAuditDO loginAuditDO = new LoginAuditDO();
        HttpServletRequest request = ContextUtil.getHttpRequest();
        Date now = DateUtil.date();
        loginAuditDO
                .setOperation(status? Constant.LOG_LOGIN:Constant.LOG_LOGOUT)
                .setStatus(status)
                .setUsername(username);
        loginAuditDO.setCreateTime(now);
        loginAuditDO.setUpdateTime(now);
        if (request != null){
            loginAuditDO.setUserAgent(request.getHeader(Constant.KEY_USER_AGENT));
            loginAuditDO.setIp(IpUtil.getRemoteAddr(request));
        }
        loginAuditService.save(loginAuditDO);
    }

    private void executeLoginSequence() {
           // 默认为日本语
           String language = Constant.DEFAULT_LANGUAGE;
           List<MastSystemDO> systemList = iMastSystemService.getByLang(language);
           if (CollUtil.isEmpty(systemList)){
               throw new GlobalException("Master not found");
           }
           // 获取系统组
           groupBusiness.getGroupList(language,systemList);
           // 获取基点组织
           baseSectionBusiness.getBaseSectionList();
    }


}
