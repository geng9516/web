package jp.smartcompany.controller.tmg_setting;

import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.MailConfigDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("sys/mailmanager")
public class MailManagerController {

   private final ScCacheUtil cacheUtil;
   private final IConfSyscontrolService confSyscontrolService;
   private final IMastEmployeesService employeesService;

   private static final String MAIL_USERNAME = "MAIL_USERNAME";
   private static final String MAIL_PASSWORD = "MAIL_PASSWORD";
   private static final String MAIL_PORT = "MAIL_PORT";
   private static final String MAIL_HOST = "MAIL_HOST";

   @GetMapping("server")
   public MailConfigDTO mailConfig() {
      String username = cacheUtil.getSystemProperty(MAIL_USERNAME);
      String password = cacheUtil.getSystemProperty(MAIL_PASSWORD);
      String port = cacheUtil.getSystemProperty(MAIL_PORT);
      String host = cacheUtil.getSystemProperty(MAIL_HOST);
      MailConfigDTO mailConfigDTO = new MailConfigDTO();
      mailConfigDTO.setUsername(username);
      mailConfigDTO.setPassword(password);
      mailConfigDTO.setPort(port);
      mailConfigDTO.setHost(host);
      return mailConfigDTO;
   }

   @PostMapping("config")
   public String updateMailConfig(@RequestBody MailConfigDTO dto) {
      confSyscontrolService.updateMailConfig(dto);
      return "メール設定変更成功";
   }

   @PostMapping("upload")
   public String uploadMailList(MultipartFile file) {
      employeesService.uploadMailList(file);
      return "導入成功";
   }

}
