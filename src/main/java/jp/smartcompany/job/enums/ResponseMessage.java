package jp.smartcompany.job.enums;

/**
 * @author xiao wenpeng
 */
public interface ResponseMessage {
    /**
     * 返回状态码
     * @return int
     */
    int code();

    /**
     * 返回提示信息
     * @return String
     */
    String msg();
}
