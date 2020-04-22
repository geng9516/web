package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class BaseSectionBO {

    private Map<String, Map<String, String>> hmCompany;
    private Map<String, Map<String, String>> hmGroup;

}
