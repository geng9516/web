package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class DraftNoticeDTO {

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
    private List<MultipartFile> attachments;


    // 可查看此公告的用户id，支持多个，中间用逗号隔开
    private String empRangeIds;
    // 所选发送范围type，用逗号隔开
    private String rangeTypes;
    // 要删除的文件idlist
    private List<Long> deleteAttachmentIdList;
    // 要保留的文件idlist
    private List<Long> keepAttachmentIdList;

}
