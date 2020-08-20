package jp.smartcompany.job.modules.tmg.tmgbulknotification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadDto {

    private String seq;
    private String sectionId;
    private String bulkNtfId;
    private String beginDate;
    private String endDate;
    private String uploadDate;
    private String uploadUserId;
    private String cancelDate;
    private String cancelUserId;
    private String count;
}
