package jp.smartcompany.job.modules.tmg.tmgnotification.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "groupId")
public class TypeGroupVo {

    /**0 グループの区分*/
    private String groupId;
    /**1 グループの名称*/
    private String groupName;

    List<TypeChildrenVo> ntfTypeValue= new ArrayList<TypeChildrenVo>();;
}
