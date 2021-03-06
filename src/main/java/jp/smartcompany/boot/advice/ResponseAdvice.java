package jp.smartcompany.boot.advice;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import jp.smartcompany.boot.annotation.IgnoreResponseSerializable;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiao wenpeng
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return !(
                methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseSerializable.class) ||
                        (methodParameter.getMethod() !=null && methodParameter.getMethod().isAnnotationPresent(IgnoreResponseSerializable.class))
                        || StrUtil.containsAny(methodParameter.getDeclaringClass().getName(),"WebMvcEndpointHandlerMapping")
        );
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest req, ServerHttpResponse res) {
        String now = TmgUtil.getSysdate();
        if (body == null) {
            return GlobalResponse.ok().put("sysDate", now);
        } else if (body instanceof GlobalResponse) {
            GlobalResponse r = (GlobalResponse)body;
            return r.put("sysDate", now);
        } else {
            GlobalResponse r= GlobalResponse.ok().put("data", body);
            // ??????????????????string??????????????????
            if (body instanceof LinkedHashMap){
                // ??????????????????500??????404
                Map<String,Object> errorResult = (Map<String,Object>)body;
                String statusKey = "status";
                String errorKey ="error";
                if (errorResult.containsKey(statusKey)&& errorResult.containsKey(errorKey)
                        && errorResult.containsKey("path")&&errorResult.containsKey("timestamp")){
                    int status = (int)errorResult.get(statusKey);
                    String msg = "";
                    if (status == HttpStatus.HTTP_NOT_FOUND) {
                        msg="404";
                    } else if (status == HttpStatus.HTTP_INTERNAL_ERROR) {
                        msg = "???????????????";
                    }
                    return GlobalResponse.error(status,msg);
                }
            }
            r.put("sysDate",now);
            return r;
        }
    }

}
