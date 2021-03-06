package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="mgbsCsectionid")
public class GroupBaseSectionBO {

    private String mgbsCcompanyid;
    private String mgbsCsectionid;
    private String mgbsClayeredsectionid;
    private String mgbsCbeloworsingle;

}
