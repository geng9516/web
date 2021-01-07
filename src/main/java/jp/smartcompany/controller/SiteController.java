package jp.smartcompany.controller;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.dto.GenericDetailItemDTO;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 首页控制器
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.SITE)
@RequiredArgsConstructor
public class SiteController {

    private final IMastApptreeService appTreeService;
    private final TimedCache<String,Object> timedCache;
    private final IMastGenericDetailService genericDetailService;

    @GetMapping
    public String index(@RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                        @RequestAttribute("isAndroid") Boolean isAndroid,
                        @RequestAttribute("isAndroidPad") Boolean isAndroidPad) {
        if (isIPhoneOrIPod || isAndroid) {
            if (isAndroidPad) {
                return indexPageDispatcher();
            }
            return "mobile/index";
        }
        return indexPageDispatcher();
    }

    private String indexPageDispatcher() {
        String homeUrl = (String) timedCache.get(Constant.KEY_HOME_URL, true);
        if (StrUtil.isBlank(homeUrl)) {
            homeUrl = appTreeService.getHomeUrl();
            timedCache.put(Constant.KEY_HOME_URL, homeUrl);
        }
        if (StrUtil.isNotBlank(homeUrl)) {
            return "forward:" + homeUrl;
        }
        return "index";
    }

    @GetMapping("header/flags")
    @ResponseBody
    public List<GenericDetailItemDTO> getHeaderDisplayFlags() {
        return genericDetailService.listItemsByDetailGroupId("TMG_HEADER_NOTICE");
    }

}
