package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardFileDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.List;

/**
 * 正式公告vo
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeVO implements Serializable {

    private static final long serialVersionUID = 602903808978542990L;

    private Long hbId;
    private String hbCcustomerid;
    private String hbCcompanyid;
    private Date hbDdateofannouncement;
    private Date hbDdateofexpire;
    private String hbCtitle;
    private String hbCmnusername;
    private String hbCheaddisp;
    private String hbCfix;
    private String hbCcontents;
    private Date updateDate;
    private Boolean hasRead = false;
    private Boolean enableEdit;
    private String hbCmnuser;
    private String author;
    private Integer status;

    private List<Map<String,String>> userRangeList;
    private List<HistBulletinBoardFileDO> attachmentList;
    private List<NoticeRangeDTO> typeRangeList;

}
