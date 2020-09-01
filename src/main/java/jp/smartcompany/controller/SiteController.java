package jp.smartcompany.controller;

import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

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
    public String toIndex(@RequestAttribute("isMobile")Boolean isMobile) {
        request.setAttribute(Constant.SITE_INDEX, 1);
        if (isMobile) {
            return "mobile/index";
        }
        return "index";
    }

    @GetMapping("sys/dashboard")
    public String toDashboard() {
        return "sys/dashboard/index";
    }

}
