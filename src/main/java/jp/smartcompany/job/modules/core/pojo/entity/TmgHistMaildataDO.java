package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@TableName("tmg_hist_maildata")
@KeySequence("TMG_HIST_MAILDATA_SEQ")
public class TmgHistMaildataDO {

    @TableId
    private Long thmdId;
    private String thmdCcustomerid;
    private String thmdCcompanyid;
    private String thmdCmodifieruserid;
    private Date thmdDmodifieddate;
    private String thmdCmodifierprogramid;
    private String thmdCeventid;
    private String thmdCid;
    private String thmdCemployeeidReceive;
    private String thmdCemployeeidTarget;
    private Date thmdDyyyymmdd;
    private Date thmdDcreate;
    private String thmdCfromaddress;
    private String thmdCtoaddress;
    private String thmdCtitle;
    private String thmdCcontent;
    private Date thmdDsend;
    private Integer thmdNsendStatus;
    @Version
    private Long versionno;

}
