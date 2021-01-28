package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class EditNoticeDTO {

    /**
     * 公告id
     */
    @NotNull
    private Long hbId;
    /**
     * 公告有效开始日
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date hbDdateofannouncement;
    /**
     * 公告有效终了日
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date hbDdateofexpire;
    /**
     * 公告标题
     */
    private String hbCtitle;
    /**
     * 是否置顶
     */
    private String hbCheaddisp;
    /**
     * 公告具体内容
     */
    private String hbCcontents;
    /**
     * 可查看此公告的用户id，支持多个，中间用逗号隔开
     */
    @NotBlank
    private String empRangeIds;
    /**
     * 所选发送范围type，用逗号隔开
     */
    @NotBlank
    private String rangeTypes;

}
