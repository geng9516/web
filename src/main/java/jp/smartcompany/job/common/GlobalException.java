package jp.smartcompany.job.common;

import jp.smartcompany.job.enums.ResponseMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xiao wenpeng
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = -398961163336124454L;

    private int code;

    public GlobalException(String msg) {
        super(msg);
    }

    public GlobalException(int code) {
        this.code = code;
    }

    public GlobalException(int code, String msg) {
       super(msg);
       this.code = code;
    }

    public GlobalException(ResponseMessage responseMessage) {
        this(responseMessage.code(),responseMessage.msg());
    }

}
