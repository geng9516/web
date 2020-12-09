package jp.smartcompany.controller.tmg_setting;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.logic.MailManagerLogic;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.MailConfigDTO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.UpdateMailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("sys/mailmanager")
public class MailManagerController {

   private final ScCacheUtil cacheUtil;
   private final IConfSyscontrolService confSyscontrolService;
   private final MailManagerLogic mailManagerLogic;

   private static final String MAIL_USERNAME = "MAIL_USERNAME";
   private static final String MAIL_PASSWORD = "MAIL_PASSWORD";
   private static final String MAIL_PORT = "MAIL_PORT";
   private static final String MAIL_HOST = "MAIL_HOST";
   private static final String MAIL_ENABLE = "MAIL_ENABLE";

   @GetMapping("server")
   public MailConfigDTO mailConfig() {
      String username = cacheUtil.getSystemProperty(MAIL_USERNAME);
      String password = cacheUtil.getSystemProperty(MAIL_PASSWORD);
      String port = cacheUtil.getSystemProperty(MAIL_PORT);
      String host = cacheUtil.getSystemProperty(MAIL_HOST);
      Boolean status = Boolean.parseBoolean(cacheUtil.getSystemProperty(MAIL_ENABLE));

      MailConfigDTO mailConfigDTO = new MailConfigDTO();
      mailConfigDTO.setUsername(username);
      mailConfigDTO.setPassword(password);
      mailConfigDTO.setPort(port);
      mailConfigDTO.setHost(host);
      mailConfigDTO.setStatus(status);
      return mailConfigDTO;
   }

   @PostMapping("config")
   public String updateMailConfig(@RequestBody MailConfigDTO dto) {
      confSyscontrolService.updateMailConfig(dto);
      return "メール設定変更成功";
   }

   @PostMapping("upload")
   public String uploadMailList(MultipartFile file) {
      mailManagerLogic.uploadMailList(file);
      return "導入成功";
   }

   @GetMapping("template")
   public void exportUploadTemplate(HttpServletResponse response,@RequestParam(defaultValue = "xls") String type){
      mailManagerLogic.exportXlsTemplate(response,type);
   }

   @GetMapping("invalid")
   public PageUtil invalidEmailEmpList(@RequestParam Map<String,Object> params) {
      return mailManagerLogic.getInvalidEmailEmpList(params);
   }

   @PostMapping("update")
   public String updateMailList(@Valid @NotEmpty @RequestBody List<UpdateMailDTO> list) {
      mailManagerLogic.updateMailList(list);
      return "メール変更成功";
   }

   // http://localhost:6879/sys/mailmanager/search?keyword=4640
   @GetMapping("search")
   public PageUtil searchForUpdateEmail(@RequestParam Map<String,Object> params) {
       String keyword = (String)params.get("keyword");
       if (StrUtil.isBlank(keyword)){
          throw new GlobalException("キーワードを入力してください");
       }
       return mailManagerLogic.searchForUpdateEmail(params,keyword);
   }

}
