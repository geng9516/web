package jp.smartcompany.boot.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.service.ErrorAuditService;
import jp.smartcompany.job.modules.core.pojo.entity.ErrorAuditDO;

import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogAspect {

  private final ErrorAuditService errorAuditService;

  @AfterThrowing(throwing = "e",value="execution(* jp.smartcompany.controller.*.*(..)) || execution(* jp.smartcompany.job.modules..*(..))")
  public void afterThrowing(JoinPoint point, Throwable e) {
    int maxParamLen = 5000;
//    EmpBO empBO = ShiroUtil.getLoginEmp();
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    ErrorAuditDO errorAuditDO = new ErrorAuditDO();
    String className = point.getTarget().getClass().getName();
    // 请求的方法名
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    String errorMessage = sw.toString();
    if (StrUtil.isNotBlank(errorMessage)) {
      // 错误信息太长则只取能存下的长度
      if (errorMessage.length() > maxParamLen) {
        errorAuditDO.setMessage(errorMessage.substring(0,maxParamLen));
      }else{
        errorAuditDO.setMessage(errorMessage);
      }
    } else {
      errorAuditDO.setMessage(e.toString());
    }
    errorAuditDO.setCalledMethod(className + "." + method.getName() + "()");
    //请求的参数
    Object[] args = point.getArgs();
    boolean canOriginalInsert = true;
    for (Object arg:args) {
      if (arg != null) {
        boolean isOriginalInsert = arg instanceof MultipartFile ||
                (arg instanceof String && ((String) arg).length() > maxParamLen);
        if (isOriginalInsert) {
          canOriginalInsert = false;
          break;
        }
      }
    }
    String params;
    if (canOriginalInsert) {
      if (args.length>1){
        params = JSONUtil.toJsonStr("[]");
      } else {
        if (args.length>0) {
          Object arg = args[0];
          params = JSONUtil.toJsonStr(arg);
        } else {
          params = "Params can not serializable";
        }
      }
    } else {
      params = "Params is not serializable";
    }
    errorAuditDO.setParams(params.length()>3000?"Params is too long":params);
    //获取request
    HttpServletRequest request = ContextUtil.getHttpRequest();
    if (request!=null) {
      errorAuditDO.setUserAgent(request.getHeader(Constant.KEY_USER_AGENT));
      errorAuditDO.setIp(IpUtil.getRemoteAddr(request));
      errorAuditDO.setUrl(request.getRequestURI());
      errorAuditDO.setMethod(request.getMethod());
    }
    String username = Constant.ANON_USER;
//    if (empBO!=null){
//      username = empBO.getUsername();
//    }
    errorAuditDO
            .setUsername(username);
    Date now = DateUtil.date();
    errorAuditDO.setCreateTime(now);
    errorAuditDO.setUpdateTime(now);
    errorAuditService.save(errorAuditDO);
  }


}
