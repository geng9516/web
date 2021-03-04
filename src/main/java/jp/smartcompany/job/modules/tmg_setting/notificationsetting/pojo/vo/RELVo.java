package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RELVo {
    private String workType;//職種
    private String workTypeId;
    //private String limitCheck;//使用有無
    private String limitNum;//取得上限値
    private String limitCount;//取得可能回数
    private String limitRange; //取得可能期
}
