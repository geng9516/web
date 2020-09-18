package jp.smartcompany.controller;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.CoreBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 首页控制器
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.SITE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteController {

    private final ScCacheUtil cacheUtil;

    @GetMapping
    public String index(@RequestAttribute("isMobile")Boolean isMobile, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isMobile) {
            return "mobile/index";
        }
        String homeUrl = cacheUtil.getSystemProperty("DEFAULT_HOME_URL");
        if (StrUtil.isNotBlank(homeUrl)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(homeUrl);
            dispatcher.forward(req, resp);
        }
        return "index";
    }

    /**
     * 控制台首页
     * @return String
     */
//    @GetMapping("sys")
//    public String toIndex(@RequestAttribute("isMobile")Boolean isMobile) {
//        request.setAttribute(Constant.SITE_INDEX, 1);
//        if (isMobile) {
//            return "mobile/index";
//        }
//        return "index";
//    }

}
