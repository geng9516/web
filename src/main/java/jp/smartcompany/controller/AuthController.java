package jp.smartcompany.controller;

import cn.hutool.cache.impl.LRUCache;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.dto.ChangePasswordDTO;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.boot.util.ShiroUtil;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockInfoVO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockResultVO;
import jp.smartcompany.job.modules.core.business.AuthBusiness;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String toLogin(@RequestAttribute("isMobile")Boolean isMobile) {
        if (ShiroUtil.isAuthenticated()) {
            return "redirect:/sys";
        }
        if (!isMobile) {
            return "login";
        }
        return "mobile/login";
    }

    /**
     * 登录API
     */
    @PostMapping("login")
    public String login(@RequestParam LoginDTO loginDTO) {
        authBusiness.login(loginDTO);
        return "redirect:/sys";
    }


    /**
     * 跳转到登ホームページ
     *
     * @return String
     */
    @GetMapping("index")
    public String toIndex() {
        if (ShiroUtil.isAuthenticated()) {
            return "redirect:/sys/input/sign?menuId=1640&moduleIndex=1&psSite=TMG_INP&psApp=TmgTimePunch";
        }
        return "login";
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
        ClockResultVO clockResultVO = new ClockResultVO();
        //1.decode　又は　md5+salt
        //2.チェック
        //3.打刻
        if (null != username && null != password) {
            //チェック
            try {
                loginAccountBo = authBusiness.basicStamping(username, password);
            } catch (Exception e) {
                clockResultVO.setResultCode("10");
                clockResultVO.setResultMsg("アカウント又はパスワードをチェック失敗しました、もう一度入力し直してください");
                return clockResultVO;
            }
            if (null != loginAccountBo) {
                //打刻したかしなかった
                ClockInfoVO clockInfoVO = tmgTimePunchBean.selectClockInfo(loginAccountBo.getHdCcustomeridCk(), loginAccountBo.getHdCcompanyidCk(), loginAccountBo.getHdCemployeeidCk());
                if (null == clockInfoVO.getTda_nopen_p() || "".equals(clockInfoVO.getTda_nopen_p())) {
                    //予定データがない場合、打刻しない
                    clockResultVO.setResultCode("20");
                    clockResultVO.setResultMsg("今日は出勤しない日です");
                }
                if ("ACT_EXEC_OPEN".equals(pAction) && null != clockInfoVO.getNopen() && !"".equals(clockInfoVO.getNopen())) {
                    //出勤打刻データがある場合、画面へ返却する
                    clockResultVO.setResultCode("0");
                    clockResultVO.setClockTime(clockInfoVO.getNopen());
                    clockResultVO.setResultMsg("今日はもう出勤打刻しました");
                    return clockResultVO;
                }
                //打刻
                clockResultVO = tmgTimePunchBean.execTimePunch(loginAccountBo.getHdCemployeeidCk(), loginAccountBo.getHdCcustomeridCk(), loginAccountBo.getHdCcompanyidCk(), pAction);
                if ("ACT_EXEC_OPEN".equals(pAction) && "".equals(clockResultVO.getResultMsg())) {
                    clockResultVO.setResultMsg("今日も一日頑張りましょう");
                }
                if ("ACT_EXEC_CLOSE".equals(pAction) && "".equals(clockResultVO.getResultMsg())) {
                    clockResultVO.setResultMsg("今日も一日お疲れ様でした");
                }
            } else {
                clockResultVO.setEmployeeId(username);
                clockResultVO.setResultCode("10");
                clockResultVO.setResultMsg("アカウント又はパスワードをチェック失敗しました、もう一度入力し直してください");
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
    public String logout() {
        authBusiness.logout();
        scCache.clear();
        return "redirect:/login";
    }

    @GetMapping("expirePassword")
    public String expirePassword() {
        return "expirePassword";
    }

    @PostMapping("changeExpirePassword")
    @ResponseBody
    public String changeExpirePassword(@RequestBody @Valid ChangePasswordDTO dto) {
        authBusiness.changePassword(dto,true);
        return "パスワード変更成功";
    }

    @PostMapping("changePassword")
    @ResponseBody
    public String changePassword(@RequestBody @Valid ChangePasswordDTO dto) {
        authBusiness.changePassword(dto,false);
        authBusiness.logout();
        return "パスワード変更成功";
    }

}
