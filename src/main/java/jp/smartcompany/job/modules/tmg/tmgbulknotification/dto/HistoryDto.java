package jp.smartcompany.job.modules.tmg.tmgbulknotification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HistoryDto {

    private String tbnNtbnid; // 0 一括申請番号

    private String tbnCbulkntftype; // 1 一括申請区分名

    private String moCsectionname;  // 2 部署名

    private String tbnCstatus; // 3 一括申請状態名

    private String tbnDbegin; // 4 一括申請期間

    private String tbndCount; // 5 対象部署数カウント
}
