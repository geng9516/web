package jp.smartcompany.job.controller;

import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页控制器
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.SITE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteController {

    private final HttpServletRequest request;

    @GetMapping
    public String redirectIndex() {
        return "redirect:/sys";
    }

    /**
     * 控制台首页
     * @return String
     */
    @GetMapping("sys")
    public String toIndex() {
        request.setAttribute(Constant.SITE_INDEX, 1);
        return "index";
    }

    /**
     * 跳转到就业入力界面
     */
    @GetMapping("sys/input")
    public String toInput(@RequestParam("tabIndex") Integer tabIndex) {
       request.setAttribute(Constant.SITE_INDEX, tabIndex);
       return "sys/input/index";
    }

    /**
     * 跳转到システム管理メニュー
     * @return
     */
    @GetMapping("sys/settings")
    public String toSettings(@RequestParam("tabIndex") Integer tabIndex) {
        request.setAttribute(Constant.SITE_INDEX, tabIndex);
        return "sys/settings/index";
    }

    /**
     * 跳转到就業承認・管理
     * @return
     */
    @GetMapping("sys/wmanage")
    public String toWManage(@RequestParam("tabIndex") Integer tabIndex) {
        request.setAttribute(Constant.SITE_INDEX, tabIndex);
        return "sys/wmanage/index";
    }

    /**
     * 跳转到就業管理
     * @param tabIndex
     * @return
     */
    @GetMapping("sys/manage")
    public String toManage(@RequestParam("tabIndex") Integer tabIndex) {
        request.setAttribute(Constant.SITE_INDEX, tabIndex);
        return "sys/manage/index";
    }

}
