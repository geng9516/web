package jp.smartcompany.boot.util;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiao wenpeng
 */
public class ContextUtil {

  public static HttpServletRequest getHttpRequest() {
    RequestAttributes servletRequestAttributes = RequestContextHolder.getRequestAttributes();
    if (servletRequestAttributes instanceof ServletRequestAttributes) {
      return ((ServletRequestAttributes) servletRequestAttributes).getRequest();
    } else if (servletRequestAttributes  instanceof NativeWebRequest) {
      return  (HttpServletRequest) ((NativeWebRequest) servletRequestAttributes).getNativeRequest();
    }
    return null;
  }

  public static String getDomain(){
    HttpServletRequest request = getHttpRequest();
    StringBuffer url = request.getRequestURL();
    return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
  }

  public static String getOrigin(){
    HttpServletRequest request = getHttpRequest();
    return request.getHeader("Origin");
  }

  public static String getParam(String key) {
    return getHttpRequest().getParameter(key);
  }

  public static void setAttr(String key, Object value) {
    getHttpRequest().setAttribute(key, value);
  }

  public static Object getAttr(String key) {
    return getHttpRequest().getAttribute(key);
  }

}