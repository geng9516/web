package jp.smartcompany.controller;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.CoreBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;


/**
 * 首页控制器
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.SITE)
@RequiredArgsConstructor(ondConstructor = @__(@Autowired))
public class SiteController {

    private final ScCacheUtil cacheUtil;

    @GetMapping
    public String index(@RequestAttribute("isMobile")Boolean isMobile) {
        if (isMobile) {
            return "mobile/index";
        }
        String homeUrl = cacheUtil.getSystemProperty("DEFAULT_HOME_URL");
        if (StrUtil.isNotBlank(homeUrl)) {
            return "forward:" + homeUrl;
        }
        return "index";
    }

}
