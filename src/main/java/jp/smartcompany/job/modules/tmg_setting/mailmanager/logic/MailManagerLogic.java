package jp.smartcompany.job.modules.tmg_setting.mailmanager.logic;

import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.UpdateMailDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.List;

public interface MailManagerLogic {

    void uploadMailList(MultipartFile file);

    void exportXlsTemplate(HttpServletResponse response, String type);

    PageUtil getInvalidEmailEmpList(Map<String,Object> params);

    void updateMailList(List<UpdateMailDTO> list);

    PageUtil searchForUpdateEmail(Map<String,Object> params,String keyword);

}
