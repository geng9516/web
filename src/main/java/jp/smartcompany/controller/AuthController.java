package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.boot.util.ShiroUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.AUTH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthBusiness authBusiness;

    /**
     * 跳转到登录页
     * @return String
     */
    @GetMapping("login")
    public String toLogin() {
        if (ShiroUtil.isAuthenticated()) {
            return "redirect:/sys";
        }
        return "login";
    }

    /**
     * 登录API
     */
    @PostMapping("login")
    public RedirectView login(@RequestParam LoginDTO loginDTO) {
        authBusiness.login(loginDTO);
        return new RedirectView("/sys");
    }

    /**
     * 退出登录API
     * @return RedirectView
     */
    @GetMapping("logout")
    public RedirectView logout() {
        authBusiness.logout();
        return new RedirectView("/login");
    }

}