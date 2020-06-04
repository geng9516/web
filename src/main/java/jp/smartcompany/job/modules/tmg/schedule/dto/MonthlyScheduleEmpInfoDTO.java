package jp.smartcompany.job.modules.tmg.schedule.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 予定作成社員情報（更新）
 * @objectSource
 * @date 2020/06/03
 **/
@Getter
@Setter
@ToString
public class MonthlyScheduleEmpInfoDTO {
    /**
     * 日付
     */
    private String dyyyymmdd;

    /**
     * 区分
     */
    private String workId;

    /**
     * 出張
     */
    private String bussinessTripid;

    /**
     * 始業
     */
    private String nopen;

    /**
     * 終業
     */
    private String nclose;

    /**
     * 休憩(JSON フォーマット)
     * 例え：[{"restOpen":"12:00","restClose":"12:30"},{"restOpen":"12:30","restClose":"13:00"}]
     */
    private List<HashMap<String,String>> restList;

    /**
     * 　コメント
     */
    private String comment;





}
