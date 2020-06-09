package jp.smartcompany.controller;

import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.service.ITGroupMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页控制器
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.SITE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteController {

    private final HttpServletRequest request;
    private final ITGroupMenuService itGroupMenuService;

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

    @GetMapping("sys/dashboard")
    public String toDashboard() {
        return "sys/dashboard/index";
    }

    /**
     * 跳转到就业入力界面
     */
    @GetMapping("sys/input")
    public String toInput(@RequestParam("siteIndex") Integer siteIndex,
                          @RequestParam("menuId") Long menuId,
                          @SessionAttribute(Constant.SYSTEM_CODE) String systemCode,
                          @SessionAttribute(Constant.CUSTOMER_ID) String customerId,
                          ModelMap modelMap) {
       request.setAttribute(Constant.SITE_INDEX, siteIndex);
       modelMap.addAttribute(Constant.SECOND_NAVS,itGroupMenuService.listMenuByGroupCodeAndParentId(menuId,systemCode,customerId));
       return "sys/input/index";
    }

    /**
     * 跳转到システム管理メニュー
     */
    @GetMapping("sys/settings")
    public String toSettings(@RequestParam("siteIndex") Integer siteIndex,
                             @RequestParam("menuId") Long menuId,
                             @SessionAttribute(Constant.SYSTEM_CODE) String systemCode,
                             @SessionAttribute(Constant.CUSTOMER_ID) String customerId,
                             ModelMap modelMap) {
        request.setAttribute(Constant.SITE_INDEX, siteIndex);
        modelMap.addAttribute(Constant.SECOND_NAVS,itGroupMenuService.listMenuByGroupCodeAndParentId(menuId,systemCode,customerId));
        return "sys/settings/index";
    }

    /**
     * 跳转到就業承認・管理
     */
    @GetMapping("sys/wmanage")
    public String toWManage(@RequestParam("siteIndex") Integer siteIndex,@RequestParam("menuId") Long menuId,
                            @SessionAttribute(Constant.SYSTEM_CODE) String systemCode,
                            @SessionAttribute(Constant.CUSTOMER_ID) String customerId,
                            ModelMap modelMap) {
        request.setAttribute(Constant.SITE_INDEX, siteIndex);
        modelMap.addAttribute(Constant.SECOND_NAVS,itGroupMenuService.listMenuByGroupCodeAndParentId(menuId,systemCode,customerId));
        return "sys/wmanage/index";
    }

    /**
     * 跳转到就業管理
     */
    @GetMapping("sys/manage")
    public String toManage(@RequestParam("siteIndex") Integer siteIndex,@RequestParam("menuId") Long menuId,
                           @SessionAttribute(Constant.SYSTEM_CODE) String systemCode,
                           @SessionAttribute(Constant.CUSTOMER_ID) String customerId,
                           ModelMap modelMap) {
        request.setAttribute(Constant.SITE_INDEX, siteIndex);
        modelMap.addAttribute(Constant.SECOND_NAVS,itGroupMenuService.listMenuByGroupCodeAndParentId(menuId,systemCode,customerId));
        return "sys/manage/index";
    }

}
