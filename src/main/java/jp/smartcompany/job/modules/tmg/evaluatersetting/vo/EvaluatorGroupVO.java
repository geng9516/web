package jp.smartcompany.job.modules.tmg.evaluatersetting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"id"})
public class EvaluatorGroupVO {

    private Integer id;
    private String group;
    private String groupId;
    private Boolean main;
    private Boolean enableEditGroup;
    private Boolean enableEditGroupName;

    private List<EvaluatorMemberVO> memberList;

}

