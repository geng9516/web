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
public class EditGroupDTO {

  @NotBlank(message = "sectionIdは空白するのを許可されていません")
  private String sectionId;

  private Boolean autoStart;
  private String dailyOverTime;
  private Double avgOverTime;
  private Double countOverTime;

  private String monthlyOverTimeYellow;
  private String monthlyOverTimeOrange;
  private String monthlyOverTimePink;
  private String monthlyOverTimeRed;
  private String monthlyOverTimeBackUp;

  private String yearlyOverTimeYellow;
  private String yearlyOverTimeOrange;
  private String yearlyOverTimePink;
  private String yearlyOverTimeRed;
  private String yearlyOverTimeBackUp;

  private String monthlyHolidayTimeLevel1;
  private String monthlyHolidayTimeLevel2;
  private String monthlyHolidayTimeLevel3;
  private String monthlyHolidayTimeLevel4;
  private String monthlyHolidayTimeLevel5;


}
