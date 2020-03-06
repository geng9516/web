package jp.smartcompany.job.advice;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import jp.smartcompany.job.annotation.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author xiao wenpeng
 */
@RestControllerAdvice
@Slf4j
public class RequestAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        boolean isClassAnnotation = methodParameter.getDeclaringClass().isAnnotationPresent(Encrypt.class);
        boolean isMethodAnnotation = methodParameter.getMethod() != null && methodParameter.getMethod().isAnnotationPresent(Encrypt.class);
        boolean isEncrypt = isClassAnnotation || isMethodAnnotation;
        if (!isEncrypt) {
          return false;
        }
        Encrypt encrypt;
        if (isClassAnnotation) {
            encrypt = methodParameter.getDeclaringClass().getAnnotation(Encrypt.class);
        } else {
            encrypt = methodParameter.getMethod().getAnnotation(Encrypt.class);
        }
        return  encrypt.encode();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                String bodyStr = IoUtil.read(httpInputMessage.getBody(), CharsetUtil.UTF_8);
                return IoUtil.toUtf8Stream(bodyStr);
            }
            @Override
            public HttpHeaders getHeaders() {
                return httpInputMessage.getHeaders();
            }
        };
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
