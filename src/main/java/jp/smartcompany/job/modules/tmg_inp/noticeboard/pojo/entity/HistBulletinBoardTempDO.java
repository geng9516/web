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
@TableName("hist_bulletinboard_temp")
@KeySequence("HIST_BULLETINBOARD_TEMP_SEQ")
public class HistBulletinBoardTempDO implements Serializable {

  @TableId
  private Long hbtId;
  private String hbtCcustomerid;
  private String hbtCcompanyid;
  private Date hbtDdateofannouncement;
  private Date hbtDdateofexpire;
  private String hbtCtitle;
  private String hbtCmnuser;
  private String hbtCmnusername;
  private String hbtCempRange;
  private String hbtCheaddisp;
  private String hbtCmodifieruserid;
  private Date hbtDmodifieddate;
  private String hbtCcontents;
  private String hbtCrangeTypes;
  @Version
  private Long versionno;

}
