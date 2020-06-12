package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TypeChildrenVo {

    /**2 申請区分*/
    private String ntfId;
    /**3 申請区分名称*/
    private String ntfName;
    /**4 表示項目タイプ*/
    private String viewType;
    /**5 申請事由必須有無*/
    private String confirmComment;
    /**6 注釈*/
    private String biko;
    /**7 添付ファイル必須有無*/
    private String confirmFile;




    //振替
    private boolean transfer = false;
    //時間帯
    private boolean timeZone = false;
    // 開始時刻・終了時刻
    private boolean workTime = false;
    // 傷病名
    private boolean sickName = false;
    // 労災申請有無
    private boolean sickApply = false;
    // 起算日
    private boolean period = false;
    // 加算日数
    private boolean addDate = false;
    //勤務時間ラベル
    private boolean label = false;
    // 休憩時間
    private boolean restTime = false;
    // 氏名
    private boolean name = false;
    // 続柄
    private boolean relation = false;
    // 生年月日
    private boolean birthday = false;
    // 曜日
    private boolean daysOfWeek = false;
    // 対象の人数
    private boolean targetNumber = false;
}
