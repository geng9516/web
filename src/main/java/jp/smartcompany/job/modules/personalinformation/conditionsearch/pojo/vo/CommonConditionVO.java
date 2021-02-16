package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonConditionVO {

   private Long hseId;
   private Long hseNsettingid;
   private String hseCsettingName;
   private String hseCcompanyselect;
   private String hseCpublic;
   private String hseCcomment;

}
