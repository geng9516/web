package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConditionSettingTargetDTO {

    private String groupId;
    private String groupName;
    /**
     * 設定ID
     */
    private Long hstId;
    /**
     * 設定ID
     */
    private Long hstNsettingid;
    /**
     * 対象システム
     * */
    private String hstCtargetsystem;
    /**
     * 対象グループ
     */
    private String hstCtargetgroup;


}
