package jp.smartcompany.boot.util;

import jp.smartcompany.boot.common.GlobalException;
import lombok.RequiredArgsConstructor;
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
import java.util.Properties;
import java.util.List;
import java.util.Map;

/**
 * 发送邮件工具类
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailUtil {

    private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private String from;

    @PostConstruct
    public void getSender() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        from = "sp_wm_dev@nisshin-sci.co.jp";
        javaMailSenderImpl.setUsername(from);
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


    /**
     * 发送文本邮件
     * @param to 收件人
     * @param title 标题
     * @param content 内容
     */
    @Async
    public void sendText(String to,String title,String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(title);
        message.setText(content);
        javaMailSender.send(message);
    }

    /**
     * 发送html邮件
     * @param to 收件人
     * @param title 邮件内容
     */
    @Async
    public void sendHtml(List<String> to, String title, String templateName, List<Map<String,Object>> vars) {
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

}
