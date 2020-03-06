package jp.smartcompany.job.common;

/**
 * @author xiao wenpeng
 */
public interface Regex {

    String USERNAME = "^[a-zA-Z\\d]{2,16}$";
    String DATE_YEAR = "^(1|2)\\d{3}$";
    /**
     * url不能以/或者/list结尾
     */
    String MENU_URL = "^sys(\\/([a-zA-Z])+)+(?<!(\\/*list))$";
    /**
     * 用于shiro验证权限，尾部的权限根据系统需求还可以继续增加，目前暂定为（list|save|delete|update|select）五种
     */
    String MENU_PERMS = "^[a-zA-Z,]+$";
    String DATE = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

    String BASE64 = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";

}
