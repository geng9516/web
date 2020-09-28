package jp.smartcompany.job.modules.tmg.evaluatersetting.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"groupId"})
public class EditMemberVO {

  private String groupId;
  private String empName;
  private String empId;
  private String groupName;
  private List<Map<String,String>> groupList;

}
