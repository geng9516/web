package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("hist_bulletinboard_read_status")
@KeySequence("HIST_BULLETINBOARD_READ_STATUS_SEQ")
public class HistBulletinBoardReadStatusDO {

    @TableId
    private Long hbrsId;

    private Long hbIdFk;

    private String hbrsEmpIdFk;

    private Boolean hbrsStatus;

}
