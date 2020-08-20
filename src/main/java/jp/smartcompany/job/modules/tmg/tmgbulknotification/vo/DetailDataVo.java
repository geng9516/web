package jp.smartcompany.job.modules.tmg.tmgbulknotification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetailDataVo {

    private String tbnNtbnid;
    private String tbnCbulkntftype;
    private String tbnCsectionid;
    private String tbnCstatus;
    private String tbnDbegin;
    private String tbnNcount;
    private String cntFinish;
    private String cntCancel;
    private String cntErr;
    private String tbnCstatusid;
    private String tbndCount;
}
