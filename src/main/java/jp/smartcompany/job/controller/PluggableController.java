package jp.smartcompany.job.controller;

import jp.smartcompany.job.common.GlobalResponse;
import jp.smartcompany.job.modules.base.BaseBean;
import jp.smartcompany.job.modules.base.service.PluggableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(BaseBean.Controller.PLUGGABLE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("exe")
public class PluggableController {

    private final PluggableService pluggableService;

    @PostMapping("param")
    @ResponseBody
    public GlobalResponse testPluggable() {
        pluggableService.execute();
        return GlobalResponse.ok();
    }

}
