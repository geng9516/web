package jp.smartcompany.controller;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.event.StampEvent;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.dto.ChangePasswordDTO;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockInfoVO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockResultVO;
import jp.smartcompany.job.modules.core.business.AuthBusiness;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthBusiness authBusiness;
    private final TmgTimePunchBean tmgTimePunchBean;
    private final TimedCache<String,Object> timedCache;
    private final ApplicationContext ctx;

    /**
     * 跳转到登录页
     *
     * @return String
     */
    @GetMapping("login")
    public String toLogin(@RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                          @RequestAttribute("isAndroid") Boolean isAndroid) {
        if (SecurityUtil.isAuthenticated()) {
            return "redirect:/";
        }
        if (isIPhoneOrIPod || isAndroid) {
            return "mobile/login";
        }
        return "login";
    }

    /**
     * 登录API
     */
//    @PostMapping("login")
//    @ResponseBody
//    public String login(@RequestParam LoginDTO loginDTO) {
//        authBusiness.login(loginDTO);
//        return "ログイン成功";
//    }


    /**
     * 跳转到登ホームページ
     *
     * @return String
     */
    @GetMapping("index")
    public String toIndex() {
        if (SecurityUtil.isAuthenticated()) {
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

    private static final String START_WORK_FLAG = "ACT_EXEC_OPEN";
    private static final String END_WORK_FLAG = "ACT_EXEC_CLOSE";
    @PostMapping("stamping")
    @ResponseBody
    public ClockResultVO stamping(String username, String password, String pAction, HttpServletRequest request) {
        LoginAccountBO loginAccountBo;
        ClockResultVO clockResultVO = new ClockResultVO();
        //1.decode　又は　md5+salt
        //2.チェック
        //3.打刻
        if (StrUtil.isAllNotBlank(username,password)) {
            //チェック
            try {
                loginAccountBo = (LoginAccountBO)timedCache.get(getStampingKey(username),false);
                if (loginAccountBo == null) {
                    loginAccountBo = authBusiness.basicStamping(username, password);
                    if (loginAccountBo!=null){
                        // 60秒后过期
                        timedCache.put(getStampingKey(username),loginAccountBo,6000);
                    }
                }
            } catch (Exception e) {
                clockResultVO.setResultCode("10");
                clockResultVO.setResultMsg("アカウント又はパスワードをチェック失敗しました、もう一度入力し直してください");
                return clockResultVO;
            }
            if (null != loginAccountBo) {
                //打刻したかしなかった
                ClockInfoVO clockInfoVO = (ClockInfoVO)timedCache.get(getClockInfoKey(loginAccountBo.getHdCemployeeidCk()),false);
                if (clockInfoVO == null) {
                    clockInfoVO = tmgTimePunchBean.selectClockInfo(loginAccountBo.getHdCcustomeridCk(), loginAccountBo.getHdCcompanyidCk(), loginAccountBo.getHdCemployeeidCk());
                    // 60秒后过期
                    timedCache.put(getClockInfoKey(loginAccountBo.getHdCemployeeidCk()),clockInfoVO,6000);
                }
                if (StrUtil.isBlank(clockInfoVO.getTda_nopen_p())) {
                    //予定データがない場合、打刻しない
                    clockResultVO.setResultCode("20");
                    clockResultVO.setResultMsg("今日は出勤しない日です。");
                }
                if (StrUtil.equals(START_WORK_FLAG,pAction) && StrUtil.isNotBlank(clockInfoVO.getNopen())) {
                    //出勤打刻データがある場合、画面へ返却する
                    clockResultVO.setResultCode("0");
                    clockResultVO.setClockTime(clockInfoVO.getNopen());
                    clockResultVO.setResultMsg("今日はもう出勤打刻しました");
                    return clockResultVO;
                }
                StampEvent stampEvent = new StampEvent(loginAccountBo,pAction);
                //打刻
                ctx.publishEvent(stampEvent);
                if (StrUtil.equals(START_WORK_FLAG,pAction) && StrUtil.isBlank(clockResultVO.getResultMsg())) {
                    clockResultVO.setResultMsg("今日も一日頑張りましょう");
                }
                if (StrUtil.equals(END_WORK_FLAG,pAction) && StrUtil.isBlank(clockResultVO.getResultMsg())) {
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
        request.getSession().invalidate();
        return clockResultVO;
    }


    @GetMapping("expirePassword")
    public String expirePassword(@RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                                 @RequestAttribute("isAndroid") Boolean isAndroid) {
        if (isIPhoneOrIPod || isAndroid) {
            return "mobile/expirePassword";
        }
        return "expirePassword";
    }

    @PostMapping("changeExpirePassword")
    @ResponseBody
    public String changeExpirePassword(@RequestBody @Valid ChangePasswordDTO dto) {
        authBusiness.changePassword(dto);
        return "パスワードを変更しました";
    }

    @PostMapping("changePassword")
    @ResponseBody
    public String changePassword(@RequestBody @Valid ChangePasswordDTO dto) {
        authBusiness.changePassword(dto);
        return "パスワードを変更しました";
    }

    @GetMapping("403")
    public String error403() {
        return "403";
    }

    private String getStampingKey(String username) {
       return username+":stamping:"+ DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN);
    }

    private String getClockInfoKey(String username) {
        return username+":clock:"+ DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN);
    }

}
