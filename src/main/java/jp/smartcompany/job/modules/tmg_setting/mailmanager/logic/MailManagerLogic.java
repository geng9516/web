package jp.smartcompany.job.modules.tmg_setting.mailmanager.logic;

import jp.smartcompany.boot.util.PageUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface MailManagerLogic {

    void uploadMailList(MultipartFile file);

    void exportXlsTemplate(HttpServletResponse response, String type);

    PageUtil getInvalidEmailEmpList(Map<String,Object> params);

}
