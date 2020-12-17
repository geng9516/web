package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadFileInfo {

    private String filename;
    private String fileUrl;
    private String realPath;

}
