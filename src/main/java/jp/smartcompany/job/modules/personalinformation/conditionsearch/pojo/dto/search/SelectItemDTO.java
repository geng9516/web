package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SelectItemDTO {

  private String label;
  private Integer value;
  private Boolean disabled;

}
