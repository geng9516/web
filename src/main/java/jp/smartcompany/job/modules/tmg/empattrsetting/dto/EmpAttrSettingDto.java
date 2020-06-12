package jp.smartcompany.job.modules.tmg.empattrsetting.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * parameter for  EmpAttrSettingBean
 */
@Getter
@Setter
@ToString
public class EmpAttrSettingDto {

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
    /**部局(組織)*/
    private String targetSectionId;
    /**部局(組織)*/
    private String targetUser;
    /**
     * 一覧検索用基準日を取得します
     */
    private String baseDate;
    /**
     * 社員の一覧
     */
    private String empListsql;





    // 平均勤務時間 時間
    private String sAvgWorkingHours = "";
    // 平均勤務時間 分
    private String sAvgWorkingMinutes = "";
    /** 週勤務日数 */
    private String sWorkingdaysWeek = "";



    // 適用開始日
    private String psStartDate;

    // 適用終了日
    private String psEndDate;

    // 勤務開始日
    private String psBeginDate;

    // 適用開始日(旧設定)
    private String psOldStartDate;

    // 適用終了日(旧設定)
    private String psOldEndDate;

    // 勤務開始日(旧設定)
    private String psOldBeginDate;

    // 勤務開始日(一番古い)
    private String psMaxOldStartDate;

}
