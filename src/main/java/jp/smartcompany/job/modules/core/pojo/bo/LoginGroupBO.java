package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class LoginGroupBO {

    private String systemCode;
    private String systemName;
    private String groupCode;
    private String groupName;

}
