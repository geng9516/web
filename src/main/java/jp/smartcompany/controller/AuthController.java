package jp.smartcompany.controller;

import cn.hutool.cache.impl.LRUCache;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.enums.SuccessMessage;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.boot.util.ShiroUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.AUTH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthBusiness authBusiness;
    private final LRUCache<Object,Object> scCache;

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
    @ResponseBody
    public GlobalResponse login(@RequestParam LoginDTO loginDTO) {
        authBusiness.login(loginDTO);
        return GlobalResponse.data(SuccessMessage.LOGIN.msg());
    }

    /**
     * 不登录打卡
     * @return
     */
    @PostMapping("stamping")
    public LoginAccountBO stamping(@RequestParam LoginDTO loginDTO) {
        LoginAccountBO loginAccountBo = null ;
        if(null!=loginDTO){
            loginAccountBo = authBusiness.basicStamping(loginDTO.getUsername(),loginDTO.getPassword());
        }
        return loginAccountBo;
    }

    /**
     * 退出登录API
     * @return RedirectView
     */
    @GetMapping("logout")
    @ResponseBody
    public Boolean logout() {
        authBusiness.logout();
        scCache.clear();
        return true;
    }

    @GetMapping("isAuth")
    @ResponseBody
    public Boolean isLogin() {
       return ShiroUtil.isAuthenticated();
    }

}
