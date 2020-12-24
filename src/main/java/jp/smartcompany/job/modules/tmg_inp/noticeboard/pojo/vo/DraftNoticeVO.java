package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempFileDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DraftNoticeVO implements Serializable {

    private static final long serialVersionUID = 602903808978542990L;

    private Long hbtId;
    // 掲示開始日
    private Date hbtDdateofannouncement;
    // 掲示終了日
    private Date hbtDdateofexpire;
    // タイトル
    private String hbtCtitle;
    // 掲示内容
    private String hbtCcontents;
    // 先頭表示フラグ
    private String hbtCheaddisp;
    // 確定フラグ
    private String hbtCfix;
    // 附件
    private List<HistBulletinBoardTempFileDO> attachments;
    // 可查看此公告的用户id
    private List<Map<String,String>> empRangeList;
    // 所选发送范围type
    private List<NoticeRangeDTO> typeRangeList;

}
