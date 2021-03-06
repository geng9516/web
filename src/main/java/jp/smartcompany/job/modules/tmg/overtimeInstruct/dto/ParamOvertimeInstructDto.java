package jp.smartcompany.job.modules.tmg.overtimeInstruct.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class ParamOvertimeInstructDto {

    /**顧客コード*/
    private String custId;
    /**法人コード*/
    private String compId;
    /**職員番号*/
    private String userCode;
    /**today
     * yyyy/mm/dd
     * */
    private String today;
    /**today*/
    private Date todayD;
    /**言語*/
    private String lang;
    /**  アクション識別子*/
    private String action;
    /**対象職員*/
    private String targetUser;

    private String siteId;

    /**基準日(YYYYMMDD)*/
    private String baseDate;
    /**基準日(YYYYMM)*/
    private String baseDateMM;
    /**基準日(YYYY)*/
    private String baseDateYYYY;
    /**基準日(YYYYMM)*/
    private Date baseDateD;
    /**グループコード*/
    private String targetGroup;
    /**組織コード*/
    private String targetSec;
    /**組織コード*/
    private String userSec;
    /** 対象年月の前月 */
    private String preMonth       = null;
    /** 対象年月の翌月 */
    private String nextMonth      = null;

    private String employeeListSql;

}
