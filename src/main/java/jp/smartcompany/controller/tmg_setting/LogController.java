package jp.smartcompany.controller.tmg_setting;

import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.business.LogBusiness;
import jp.smartcompany.boot.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 日志控制器
 * @author Xiao Wenpeng
 */
@RestController(CoreBean.Controller.LOG)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("sys/log")
public class LogController {

  private final LogBusiness logBusiness;

  @GetMapping("access")
  public PageUtil accessAuditList(@RequestParam Map<String, Object> params) {
    return logBusiness.listAccessLog(params);
  }

  @GetMapping("login")
  public PageUtil loginAuditList(@RequestParam Map<String, Object> params) {
    return logBusiness.listLoginLog(params);
  }

  @GetMapping("error")
  public PageUtil errorAuditList(@RequestParam Map<String, Object> params) {
    return logBusiness.listErrorLog(params);
  }

}
