package jp.smartcompany.job.modules.tmg.evaluatersetting.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"name","empId"})
public class EvaluatorMemberVO {

    private String empId;
    private String name;
    private String groupName;
    private String groupId;
    private String postName;
    private Boolean nameBold;
    private Boolean enableEmpEdit;
    private List<EvaluatorMemberRightVO> validList;

}
