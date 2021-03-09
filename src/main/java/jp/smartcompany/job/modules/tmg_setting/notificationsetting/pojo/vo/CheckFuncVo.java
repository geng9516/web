package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckFuncVo {

    private String funcname;
    private String funcid;
    private String comment;
    private String groupingcode;
    private String limit;
    private String limitunit;

}
