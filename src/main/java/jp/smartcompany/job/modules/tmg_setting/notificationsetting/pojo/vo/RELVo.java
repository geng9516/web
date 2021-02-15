package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RELVo {
    private String workType;//職種

    private String limit;//上限値
    private String limitType1;//単位
    private String limitNum;//回数
    private String limitRange;//期間
    private String limitType2;//単位

}
