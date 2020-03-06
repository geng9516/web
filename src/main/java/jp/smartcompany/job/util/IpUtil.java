package jp.smartcompany.job.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xiao wenpeng
 */
public class IpUtil {

    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip != null && isNotValidAddress(ip)) {
            ip = null;
        }

        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (ip != null && isNotValidAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip != null && isNotValidAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip != null && isNotValidAddress(ip)) {
                ip = null;
            }
        }

        return ip;
    }

    private static boolean isNotValidAddress(String ip) {
        if (ip == null) {
            return true;
        } else {
            for(int i = 0; i < ip.length(); ++i) {
                char ch = ip.charAt(i);
                boolean isValid = (ch < '0' || ch > '9') && (ch < 'A' || ch > 'F') && (ch < 'a' || ch > 'f') && ch != '.' && ch != ':';
                if (isValid) {
                    return true;
                }
            }

            return false;
        }
    }

//    public static IpAddressInfo getAddressByIp(String ip){
//        String ipStackKey = "fca8c94c5d3485340cb5097a5bd0113f";
//        return JSONUtil.toBean(HttpUtil.get(StrUtil.format("http://api.ipstack.com/{}?access_key={}&language=ja", ip, ipStackKey)), IpAddressInfo.class);
//    }

}
