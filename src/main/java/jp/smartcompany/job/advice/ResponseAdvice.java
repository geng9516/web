package jp.smartcompany.job.advice;

import jp.smartcompany.job.annotation.IgnoreResponseSerializable;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.common.GlobalResponse;
import jp.smartcompany.job.enums.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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
        } else if (body instanceof ResponseMessage) {
            return GlobalResponse.ok((ResponseMessage)body);
        } else {
            return GlobalResponse.ok().put(Constant.DATA, body);
        }
    }

}
