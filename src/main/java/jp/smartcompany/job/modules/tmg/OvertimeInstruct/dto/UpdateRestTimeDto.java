package jp.smartcompany.job.modules.tmg.overtimeInstruct.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class UpdateRestTimeDto {

    //超過勤務命令・休憩時間
    private String sNRestOpen;
    private String sNRestClose;
}
