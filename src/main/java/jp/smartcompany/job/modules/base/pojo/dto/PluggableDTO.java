package jp.smartcompany.job.modules.base.pojo.dto;

import jp.smartcompany.job.modules.base.pojo.bo.PluggableBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PluggableDTO {

    private String className;
    private String methodName;
    private PluggableBO pluggableBO;

}
