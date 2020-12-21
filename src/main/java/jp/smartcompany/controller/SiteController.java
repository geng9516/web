package jp.smartcompany.controller;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteController {

    private final IMastApptreeService appTreeService;
    private final TimedCache<String,Object> timedCache;

    @GetMapping
    public String index(@RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                        @RequestAttribute("isAndroid") Boolean isAndroid) {
        if (isIPhoneOrIPod || isAndroid) {
            return "mobile/index";
        }
        String homeUrl = (String)timedCache.get(Constant.KEY_HOME_URL,true);
        if (StrUtil.isBlank(homeUrl)){
            homeUrl = appTreeService.getHomeUrl();
            timedCache.put(Constant.KEY_HOME_URL,homeUrl);
        }
        if (StrUtil.isNotBlank(homeUrl)) {
            return "forward:" + homeUrl;
        }
        return "index";
    }

}
