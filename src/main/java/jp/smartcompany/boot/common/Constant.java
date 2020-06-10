package jp.smartcompany.boot.common;

/**
 * @author Xiao Wenpeng
 */
public class Constant {

    /**
     * 当前页码
     */
    public final static String KEY_PAGE = "page";
    /**
     *  每页显示记录数
     */
    public final static String KEY_LIMIT = "limit";
    /**
     * 排序字段
     */
    public final static String KEY_ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public final static String KEY_ORDER = "order";
    /**
     * 升序
     */
    public final static String KEY_ASC = "asc";
    /**
     * 是否需要返回数据总数
     */
    public final static String KEY_SUM = "sum";
    /**
     * 客户端浏览器useragent
     */
    public final static String KEY_USER_AGENT = "user-agent";
    public final static String DATA = "data";
    public final static String MSG = "msg";
    public final static String CODE = "code";

    public final static String PS_SESSION = "psSession";

    public final static String ANON_USER = "anon user";

    /**
     * 环境映射，对应application.xml中的spring.profiles.active
     */
    public interface Env {
        String DEV = "dev";
        String PROD = "prod";
        String TEST = "test";
    }

    public final static String LOG_LOGIN = "ログイン";
    public final static String LOG_LOGOUT = "ログアウト";

    public final static String DEFAULT_LANGUAGE = "ja";

    /**
     * 基点组织（是否包含组织下的所有自组织）
     */
    public final static String NO_SUB_LEVEL = "1";

    /**
     * 系统菜单相关常量
     */
    public final static String TOP_NAVS = "topNavs";
    public final static String SECOND_NAVS = "secondNavs";

    public final static String SYSTEM_CODE = "systemCode";
    public final static String CUSTOMER_ID = "customerId";

    public final static String SITE_INDEX = "siteIndex";

}
