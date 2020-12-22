package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("hist_bulletinboard_user")
@KeySequence("HIST_BULLETINBOARD_USER_SEQ")
public class HistBulletinBoardUserDO implements Serializable {

    @TableId
    private Long hbgId;
    private Long hbgCarticleid;
    private String hbgCuserids;
    private String hbgCmodifieruserid;
    private Date hbgDmodifieddate;
    @Version
    private Long versionno;

}
