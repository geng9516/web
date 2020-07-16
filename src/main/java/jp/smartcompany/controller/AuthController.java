package jp.smartcompany.controller;

import cn.hutool.cache.impl.LRUCache;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.enums.SuccessMessage;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.boot.util.ShiroUtil;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockResultVO;
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
    private final TmgTimePunchBean tmgTimePunchBean;
    private final LRUCache<Object, Object> scCache;

    /**
     * 跳转到登录页
     *
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
     * 打刻
     * 　実は、項目暗号化が必要です（encode  md5+salt　等）けど、
     * 　今のフランド（iView）フレームワーク中で、そのモジュールが含まないが、今は平文伝送だけです
     *
     * @return
     */
    @PostMapping("stamping")
    @ResponseBody
    public ClockResultVO stamping(String username, String password, String pAction) {
        LoginAccountBO loginAccountBo = null;
        ClockResultVO clockResultVO = null;
        //1.decode　又は　md5+salt
        //2.チェック
        //3.打刻
        if (null != username && null != password) {
            //チェック
            loginAccountBo = authBusiness.basicStamping(username, password);
            if (null != loginAccountBo) {
                //打刻
                clockResultVO = tmgTimePunchBean.execTimePunch(loginAccountBo.getHdCemployeeidCk(), loginAccountBo.getHdCcustomeridCk(), loginAccountBo.getHdCcompanyidCk(), pAction);
            } else {
                clockResultVO.setEmployeeId(username);
                clockResultVO.setResultCode("10");
                clockResultVO.setResultMsg("チェック失敗しました、もう一度ユーザー又はパスワードを入力し直してください");
            }
        } else {
            clockResultVO.setResultCode("10");
            clockResultVO.setResultMsg("チェック失敗しました、もう一度ユーザー又はパスワードを入力し直してください");
        }
        return clockResultVO;
    }

    /**
     * 退出登录API
     *
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
