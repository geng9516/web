package jp.smartcompany.boot.util;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.pojo.entity.TmgHistMaildataDO;
import jp.smartcompany.job.modules.core.service.ITmgHistMaildataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.List;
import java.util.Map;

/**
 * 发送邮件工具类
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MailUtil {

    private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final ITmgHistMaildataService tmgHistMaildataService;

    private String from;

    @PostConstruct
    public void getSender() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        from = "sp_wm_dev";
        javaMailSenderImpl.setUsername("sp_wm_dev");
        javaMailSenderImpl.setPassword("password0");
        javaMailSenderImpl.setPort(587);
        javaMailSenderImpl.setHost("smtp.nisshin-sci.co.jp");
        Properties props = new Properties();
        props.setProperty("mail.smtp.timeout","0");
        props.setProperty("mail.smtp.auth","true");
        props.setProperty("mail.smtp.starttls.enable","true");
        props.setProperty("mail.smtp.starttls.required","true");
        javaMailSenderImpl.setJavaMailProperties(props);
        javaMailSender = javaMailSenderImpl;
    }

    @Async
    public void sendMail(MailType mailType, String empId, Date standardDate, String toAddress, String title, String content) {
        sendText(toAddress,title,content);
        saveSendMailHistory(mailType.getDesc(),empId,standardDate,toAddress,title,content,1);
    }

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param title 标题
     * @param content 内容
     */
    private void sendText(String to,String title,String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(title);
        message.setText(content);
        log.info("to:{},from:{},title:{},content:{}",to,from,title,content);
        javaMailSender.send(message);
    }

    /**
     * 发送html邮件
     * @param to 收件人
     * @param title 邮件内容
     */
    private void sendHtml(List<String> to, String title, String templateName, List<Map<String,Object>> vars) {
            for (int i = 0; i < to.size(); i++) {
                Context context = new Context();
                Map<String,Object> map = vars.get(i);
                context.setVariables(map);
                String emailContent = templateEngine.process(templateName, context);
                String toItem = to.get(i);
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper;
                try {
                    helper = new MimeMessageHelper(message, true,"UTF-8");
                    helper.setTo(toItem);
                    helper.setSubject(title);
                    helper.setFrom(from);
                    helper.setText(emailContent, true);
                } catch (MessagingException e) {
                    throw new GlobalException(e.getMessage());
                }
                javaMailSender.send(message);
            }
    }

    /**
     *
     * @param cid メール定義ID
     * @param empId　メールを受け取る職員番号
     * @param standardDate　基準日
     * @param toAddress　送信先アドレス
     * @param title　送信メール件名
     * @param content　送信メールメッセージ
     * @param status　送信ステータス ０：未送信　１：送信済　２：送信エラー
     */
    public void saveSendMailHistory(String cid,String empId,Date standardDate,String toAddress,String title,String content,int status) {
        Date now =DateUtil.date();
        TmgHistMaildataDO tmgHistMaildataDO = new TmgHistMaildataDO();
        tmgHistMaildataDO.setThmdCcompanyid("01");
        tmgHistMaildataDO.setThmdCcustomerid("01");
        tmgHistMaildataDO.setThmdDmodifieddate(now);
        tmgHistMaildataDO.setThmdCeventid("TMG_MAIL");
        tmgHistMaildataDO.setThmdCid(cid);
        tmgHistMaildataDO.setThmdCemployeeidReceive(empId);
        tmgHistMaildataDO.setThmdDyyyymmdd(standardDate);
        tmgHistMaildataDO.setThmdDcreate(now);
        tmgHistMaildataDO.setThmdCfromaddress(from);
        tmgHistMaildataDO.setThmdCtoaddress(toAddress);
        tmgHistMaildataDO.setThmdCtitle(title);
        tmgHistMaildataDO.setThmdCcontent(content);
        tmgHistMaildataDO.setThmdDsend(now);
        tmgHistMaildataDO.setThmdNsendStatus(status);
        tmgHistMaildataDO.setVersionno(1L);
        tmgHistMaildataService.save(tmgHistMaildataDO);
    }

}
