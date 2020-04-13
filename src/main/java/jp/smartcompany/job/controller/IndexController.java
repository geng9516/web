package jp.smartcompany.job.controller;

import jp.smartcompany.job.modules.core.CoreBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器
 * @author Xiao Wenpeng
 */
@Controller(CoreBean.Controller.INDEX)
public class IndexController {

    @GetMapping
    public String toIndex() {
        return "index";
    }

}
