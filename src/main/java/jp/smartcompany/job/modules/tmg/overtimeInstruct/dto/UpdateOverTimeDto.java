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
public class UpdateOverTimeDto {

    //超過勤務命令・時間、コメント、ステータス
    private String sNOpen;
    private String sNClose;
    private String sCComment;
    private String sStatus;



}
