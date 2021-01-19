package jp.smartcompany.job.asynctask;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.pojo.entity.TmgHistMaildataDO;
import jp.smartcompany.job.modules.core.service.ITmgHistMaildataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * 异步任务
 * 发送邮件
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SendMailTask {

    private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final ITmgHistMaildataService tmgHistMaildataService;
    private final ScCacheUtil cacheUtil;
    private static final String MAIL_ENABLE = "MAIL_ENABLE";

    public void init() {
        if (Objects.isNull(javaMailSender)) {
            JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
            ScCacheUtil cacheUtil = SpringUtil.getBean(ScCacheUtil.class);
            javaMailSenderImpl.setUsername(cacheUtil.getSystemProperty("MAIL_USERNAME"));
            javaMailSenderImpl.setPassword(cacheUtil.getSystemProperty("MAIL_PASSWORD"));
            javaMailSenderImpl.setPort(Integer.parseInt(cacheUtil.getSystemProperty("MAIL_PORT")));
            javaMailSenderImpl.setHost(cacheUtil.getSystemProperty("MAIL_HOST"));
            Properties props = new Properties();
            props.setProperty("mail.smtp.timeout", "0");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.starttls.required", "true");
            javaMailSenderImpl.setJavaMailProperties(props);
            javaMailSender = javaMailSenderImpl;
        }
    }

    @Async
    public void sendMail(MailType mailType, String sender,String empId, Date standardDate, String toAddress, String title, String content) {
        boolean status = Boolean.parseBoolean(cacheUtil.getSystemProperty(MAIL_ENABLE));
        // 不启用发送邮件功能直接返回
        if (!status) {
            return;
        }
        init();
        sendText(sender,toAddress,title,content);
        saveSendMailHistory(sender,mailType.getDesc(),empId,standardDate,toAddress,title,content,1);
    }

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param title 标题
     * @param content 内容
     */
    private void sendText(String from,String to,String title,String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(title);
        message.setText(content);
        log.info("to:{},from:{},title:{},content:{}",to,from,title,content);
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Async
    public void sendTestMail(String to,String title,String content,String from) {
        boolean status = Boolean.parseBoolean(cacheUtil.getSystemProperty(MAIL_ENABLE));
        if (!status) {
            throw new GlobalException("メール機能はまだ起用されていません");
        }
        init();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(title);
        message.setText(content);
        log.info("to:{},from:{},title:{},content:{}",to,from,title,content);
        try {
          javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * 发送html邮件
     * @param to 收件人
     * @param title 邮件内容
     */
    private void sendHtml(String from,List<String> to, String title, String templateName, List<Map<String,Object>> vars) {
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
     * @param to　送信先アドレス
     * @param title　送信メール件名
     * @param content　送信メールメッセージ
     * @param status　送信ステータス ０：未送信　１：送信済　２：送信エラー
     */
    public void saveSendMailHistory(String from,String cid,String empId,Date standardDate,String to,String title,String content,int status) {
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
        tmgHistMaildataDO.setThmdCtoaddress(to);
        tmgHistMaildataDO.setThmdCtitle(title);
        tmgHistMaildataDO.setThmdCcontent(content);
        tmgHistMaildataDO.setThmdDsend(now);
        tmgHistMaildataDO.setThmdNsendStatus(status);
        tmgHistMaildataDO.setVersionno(1L);
        tmgHistMaildataService.save(tmgHistMaildataDO);
    }

}
