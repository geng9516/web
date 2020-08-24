package jp.smartcompany.job.modules.tmg.tmgbulknotification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SectionDetailVo {
    private String moNlevel;
    private String moNseq;
    private String moCsectionidCk;
    private String moCsectionname;
    private String moCparentid;
}
