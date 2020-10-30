package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import jp.smartcompany.job.modules.core.enums.MailType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mast_mailinfo")
@KeySequence("MAST_MAILINFO_SEQ")
public class MastMailInfoDO implements Serializable  {

    private static final long serialVersionUID = -6710280964534728437L;

    @TableId
    private Long mmId;
    @TableField(value="mm_cid")
    private MailType sendType;
    private String mmCmailname;
    private String mmCaddress;
    private String mmCname;
    private String mmCtitle;
    private String mmCcontent;
    private String mmClanguage;
    private String mmCdesc;
    private String mmCmodifieruserid;
    private Date mmDmodifieddate;
    @Version
    private Long versionno;
    private String mmCmoduletype;

//    MM_ID, MM_CID, MM_CMAILNAME, MM_CADDRESS, MM_CNAME, MM_CTITLE,
//    MM_CCONTENT, MM_CLANGUAGE, MM_CDESC, MM_CMODIFIERUSERID, MM_DMODIFIEDDATE,
//    VERSIONNO, MM_CMODULETYPE

}
