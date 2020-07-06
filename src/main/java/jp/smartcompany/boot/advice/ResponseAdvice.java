package jp.smartcompany.boot.advice;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.annotation.IgnoreResponseSerializable;
import jp.smartcompany.boot.common.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
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
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return !(methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseSerializable.class
        ) || (methodParameter.getMethod() !=null && methodParameter.getMethod().isAnnotationPresent(IgnoreResponseSerializable.class)));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest req, ServerHttpResponse res) {
        if (body == null) {
            return GlobalResponse.ok();
        } else if (body instanceof GlobalResponse) {
            return body;
        } else {
            GlobalResponse r= GlobalResponse.ok().put("data", body);
            // 如果返回值为string则要特殊处理
            if(body instanceof String) {
                res.getHeaders().set("Content-Type",MediaType.APPLICATION_JSON_VALUE);
                return JSONUtil.toJsonStr(r);
            } else if (body instanceof LinkedHashMap){
                // 如果返回值是500或者404
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
                        msg = "内部エラー";
                    }
                    return GlobalResponse.error(status,msg);
                }
            }
            return r;
        }
    }

}
