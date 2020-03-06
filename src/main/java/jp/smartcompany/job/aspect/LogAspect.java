package jp.smartcompany.job.aspect;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import jp.smartcompany.job.annotation.OperationLog;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.modules.core.manager.ErrorAuditManager;
import jp.smartcompany.job.modules.core.manager.OperationAuditManager;
import jp.smartcompany.job.modules.core.pojo.entity.ErrorAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.OperationAuditDO;
import jp.smartcompany.job.util.ContextUtil;
import jp.smartcompany.job.util.IpUtil;
import jp.smartcompany.job.util.ShiroUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogAspect {

  private final ErrorAuditManager errorAuditManager;
  private final OperationAuditManager operationAuditManager;

  @Around(value="@annotation(jp.smartcompany.job.annotation.OperationLog)")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    HttpServletRequest request = ContextUtil.getHttpRequest();
    long beginTime = System.currentTimeMillis();
    String className = point.getTarget().getClass().getName();
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    OperationLog operationLog = method.getAnnotation(OperationLog.class);
    OperationAuditDO operationAuditDO = new OperationAuditDO();
    if(operationLog != null){
      // 注解上的描述
      operationAuditDO.setOperation(operationLog.value());
    }
    operationAuditDO.setMethod(className + "." + method.getName() + "()");
    //请求的参数
    Object[] args = point.getArgs();
    String[] argNames = signature.getParameterNames();
    boolean canOriginalInsert = true;
    Map<String,Object> paramMaps = MapUtil.newHashMap();
    for (int i = 0;i<args.length;i++) {
      Object arg = args[i];
      if (arg != null) {
        boolean isOriginalInsert = arg instanceof MultipartFile || (arg instanceof String && ((String) arg).length() > 5000);
        if (isOriginalInsert) {
          canOriginalInsert = false;
          break;
        }
      }
      assert request != null;
      if (StrUtil.equalsIgnoreCase(request.getMethod(),"get")) {
        String argName = argNames[i];
        paramMaps.put(argName,arg);
      }
    }
    String params;
    if (canOriginalInsert) {
      if (args.length>1 || MapUtil.isEmpty(paramMaps)){
         params = JSONUtil.toJsonStr(paramMaps);
      } else {
        if (args.length>0) {
          Object arg = args[0];
          params = JSONUtil.toJsonStr(arg);
        } else {
          params = "Params can not serializable";
        }
      }
    } else {
      params = "Params can not serializable";
    }
    // 3000为数据库定义的可存储varchar长度
    operationAuditDO.setParams(params.length()>3000?"Params is too long":params);
    //设置IP地址
    if (request!=null) {
      operationAuditDO
              .setIp(IpUtil.getRemoteAddr(request))
              .setUrl(request.getRequestURI());
    }

//    EmpBO empBO = ShiroUtil.getLoginEmp();

    String username = Constant.ANON_USER;
//    if (empBO!=null){
//      username = empBO.getUsername();
//    }
    operationAuditDO
            .setUsername(username);
    //执行方法
    Object result = point.proceed();
    //执行时长(毫秒)
    long time = System.currentTimeMillis() - beginTime;
    operationAuditDO.setTime(time);
    operationAuditManager.save(operationAuditDO);
    return result;
  }

  @AfterThrowing(throwing = "e",value="execution(* jp.smartcompany.job.controller.*.*(..)) || execution(* jp.smartcompany.job.modules..*(..))")
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
    errorAuditManager.save(errorAuditDO);
  }


}
