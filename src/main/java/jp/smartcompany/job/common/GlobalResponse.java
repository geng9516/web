package jp.smartcompany.job.common;
import jp.smartcompany.job.enums.ErrorMessage;
import jp.smartcompany.job.enums.ResponseMessage;
import jp.smartcompany.job.enums.SuccessMessage;

import java.util.HashMap;

/**
 * @author xiao wenpeng
 */
public class GlobalResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -4482985626749941891L;

    private GlobalResponse() {}

    public static GlobalResponse ok() {
        return putData(SuccessMessage.SUCCESS);
    }

    public static GlobalResponse ok(String msg) {
        return putData(SuccessMessage.SUCCESS.code(), msg);
    }

    public static GlobalResponse data(String data) {
        GlobalResponse r = ok();
        r.put(Constant.DATA, data);
        return r;
    }

    public static GlobalResponse ok(ResponseMessage responseMessage) {
        return putData(responseMessage);
    }

    public static <T> GlobalResponse ok(String msg, T data) {
        GlobalResponse r = putData(SuccessMessage.SUCCESS.code(), msg);
        r.put(Constant.DATA, data);
        return r;
    }

    public static GlobalResponse error() {
        return error(ErrorMessage.SERVER_INTERNAL_ERROR);
    }

    public static GlobalResponse error(String msg) {
        return putData(ErrorMessage.SERVER_INTERNAL_ERROR.code(), msg);
    }

    public static GlobalResponse error(int code, String msg) {
        return putData(code, msg);
    }

    public static GlobalResponse error(ResponseMessage responseMessage) {
        return putData(responseMessage);
    }

    private static GlobalResponse putData(ResponseMessage responseMessage) {
        return putData(responseMessage.code(), responseMessage.msg());
    }

    private static GlobalResponse putData(int code, String msg) {
        GlobalResponse r = new GlobalResponse();
        r.put(Constant.CODE, code);
        r.put(Constant.MSG, msg);
        return r;
    }

    @Override
    public GlobalResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
