package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class DBMastGroupBO {

    private String systemId;
    private Integer weight;
    private String groupId;
    private String systemName;
    private String groupName;
    private String pQuery;

}
