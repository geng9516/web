package jp.smartcompany.job.modules.tmg.evaluatersetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class EditMemberDTO {

  @NotBlank
  private String sectionId;
  @NotBlank
  private String groupId;
  @NotBlank
  private String empId;

}
