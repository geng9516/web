package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Getter
@Setter
@ToString
public class TypeGroupVo {
    private String typeGroupName;
    private String typeGroupId;


    private String begin;
    private String end;

    private String avgSection; //平均勤務時間・年休残時間の換算区分
    private String digestionSection; //消化時間の換算区分
    private String workDay; //営業日or暦日区分
    private String contiCheck; //連続チェック有無（0：無、1：有）
    private String yearBeginMonth; //年度開始月
    private String startDateRange; //同一とみなす起算日の範囲
    private String hasuuSection; //消化時間に端数がある場合の換算区分
}
