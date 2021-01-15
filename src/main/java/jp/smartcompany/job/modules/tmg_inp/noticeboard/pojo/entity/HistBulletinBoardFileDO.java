package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("hist_bulletinboard_file")
@KeySequence("HIST_BULLETINBOARD_FILE_SEQ")
public class HistBulletinBoardFileDO implements Serializable {

    private static final long serialVersionUID = 7554200610592160013L;
    @TableId
    private Long hbfId;

    private Long hbIdFk;

    @TableField(value="hbf_filename")
    private String hbfFileName;

    private String hbfFileUrl;

    private String hbfFileRealPath;

    private Date hbfStartDate;

    private Date hbfEndDate;

    private Boolean hbfValid;

}
