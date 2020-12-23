package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class DraftNoticeDTO {

    private Long hbtId;
    // 掲示開始日
    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date hbtDdateofannouncement;
    // 掲示終了日
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date hbtDdateofexpire;
    @NotBlank
    // タイトル
    private String hbtCtitle;
    // 掲示内容
    @NotBlank
    private String hbtCcontents;
    // 先頭表示フラグ
    private String hbtCheaddisp;
    // 確定フラグ
    private String hbtCfix;

}
