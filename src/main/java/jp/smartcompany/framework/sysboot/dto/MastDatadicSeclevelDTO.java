package jp.smartcompany.framework.sysboot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class MastDatadicSeclevelDTO {

    private Long mdslId;
    private String mdslCsystemid;
    private String mdslCcolumnname;
    private String mdslClevelid;
    private Long versionno;

}
