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
public class NoticeBoardDTO {

    // 掲示開始日
    private String hbDdateofannouncement;
    // 掲示終了日
    private String hbDdateofexpire;
    // タイトル
    private String hbCtitle;
    // 掲示内容
    private String hbCcontents;
    // 掲示者ユーザID
    private String hbCmnuser;
    // 先頭表示フラグ
    private String hbCheaddisp;
    // 確定フラグ
    private String hbCfix;
    // 最終更新者
    private String hbCmodifieruserid;
    // 最終更新日
    private Date hbDmodifieddate;
    // 上传文件列表
    private List<MultipartFile> files;

}
