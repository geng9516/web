package jp.smartcompany.job.common;

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

    public final static String ANON_USER = "anon user";

    /**
     * 环境映射，对应application.xml中的spring.profiles.active
     */
    public interface Env {
        String DEV = "dev";
        String PROD = "prod";
        String TEST = "test";
    }

}
