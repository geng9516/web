package jp.smartcompany.job.controller;

import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.service.LogService;
import jp.smartcompany.job.util.PageUtil;
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
@RequestMapping("log")
public class LogController {

  private final LogService logService;

  @GetMapping("access")
  public PageUtil accessAuditList(@RequestParam Map<String, Object> params) {
    return logService.listAccessLog(params);
  }

  @GetMapping("login")
  public PageUtil loginAuditList(@RequestParam Map<String, Object> params) {
    return logService.listLoginLog(params);
  }

  @GetMapping("error")
  public PageUtil errorAuditList(@RequestParam Map<String, Object> params) {
    return logService.listErrorLog(params);
  }

  @GetMapping("operation")
  public PageUtil operationAuditList(@RequestParam Map<String, Object> params) {
    return logService.listOperationLog(params);
  }

}
