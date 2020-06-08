package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.enums.ErrorMessage;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.modules.core.service.LoginAuditService;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.IpUtil;
import jp.smartcompany.boot.util.ShiroUtil;
import jp.smartcompany.boot.util.SysDateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 权限验证Logic层
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.AUTH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthBusiness {

    private final IMastPasswordService iMastPasswordService;
    private final IMastAccountService iMastAccountService;
    private final LoginAuditService loginAuditService;

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


}
