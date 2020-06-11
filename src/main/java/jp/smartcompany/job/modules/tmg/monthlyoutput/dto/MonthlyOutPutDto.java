package jp.smartcompany.job.modules.tmg.monthlyoutput.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * parameter for  MonthlyOutPutDto
 */
@Getter
@Setter
@ToString
public class MonthlyOutPutDto {


    /**顧客コード*/
    private String custId;
    /**法人コード*/
    private String compId;
    /**社員番号*/
    private String userCode;
    /**言語*/
    private String lang;
    /**  アクション識別子*/
    private String action;
    /** リクエストキー.検索対象部署 */
    private String targetSection;

    private String targetComp;
    /**検索対象年月日*/
    private String targetDate;
}
