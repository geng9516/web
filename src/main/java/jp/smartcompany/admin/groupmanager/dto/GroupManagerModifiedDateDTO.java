package jp.smartcompany.admin.groupmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class GroupManagerModifiedDateDTO {

    private Long mgId;
    private String mgCcustomerid;
    private String mgCsystemidCkFk;
    private String mgCgroupidPk;
    private String mgClanguage;
    private Date mgDstartdate;
    private Date mgDenddate;
    private String mgCgroupdescription;
    private String mgCgroupdescriptionja;
    private String mgCgroupdescriptionen;
    private String mgCgroupdescriptionch;
    private String mgCgroupdescription01;
    private String mgCgroupdescription02;
    private String mgCcompanyid;
    private Long mgNpartinentnumber;
    private Long mgNweightage;
    private String mgCtext;
    private String mgCmodifieruserid;
    private Date mgDmodifieddate;
    private Integer versionNo;

    private List<GroupManagerModifiedDateDTO> glModifiedDateList;
    private String gsApplyDate;
    private String gsModifiedDate;
    private String validdate;
    private String previousdate;
    private String nextdate;
    private boolean gsMessage;
    private Map goJsVariables;
    private String gsAddParmURL;
    private String mindate;

}
