package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mast_mailinfo")
@KeySequence("MAST_MAILINFO_SEQ")
public class MastMailInfoDO implements Serializable  {

    private static final long serialVersionUID = -6710280964534728437L;

    private Long mmId;

//    MM_ID, MM_CID, MM_CMAILNAME, MM_CADDRESS, MM_CNAME, MM_CTITLE,
//    MM_CCONTENT, MM_CLANGUAGE, MM_CDESC, MM_CMODIFIERUSERID, MM_DMODIFIEDDATE,
//    VERSIONNO, MM_CMODULETYPE

}
