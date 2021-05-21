package priv.zhou.common.tools;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import priv.zhou.common.properties.AppProperties;

/**
 * 邮箱工具类
 *
 * @author zhou
 * @since 0.0.1
 */
@DependsOn(value = {"javaMailSender", "mailProperties", "appProperties"})
public class EmailUtil {

    private static final JavaMailSender SENDER = SpringUtils.getBean(JavaMailSender.class);

    private static final MailProperties PROPERTIES = SpringUtils.getBean(MailProperties.class);

    private static final AppProperties APP_PROPERTIES = SpringUtils.getBean(AppProperties.class);

    /**
     * 给某人发送异常邮件
     *
     * @param topic   发送主题
     * @param content 发送内容
     * @param address 要发送到的邮箱
     */
    public static void send(String topic, String content, String... address) {
        if (APP_PROPERTIES.isEnableEmail()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(PROPERTIES.getUsername());
            message.setTo(address);
            message.setSubject(topic);
            message.setText(content);
            SENDER.send(message);
        }
    }
}
