package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("hist_bulletinboard_temp_file")
@KeySequence("HIST_BULLETINBOARD_TEMP_FILE_SEQ")
public class HistBulletinBoardTempFileDO implements Serializable {

    private static final long serialVersionUID = -2223133985388200355L;

    @TableId
    private Long hbtfId;
    private Long hbtIdFk;
    private String hbtfFilename;
    private String hbtfFileUrl;
    private String hbtfFileRealPath;
    private Date hbtfStartDate;
    private Date hbtfEndDate;
    private Boolean hbtfValid;

}
