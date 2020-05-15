package priv.zhou.common.tools;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import priv.zhou.common.param.AppProperties;

/**
 *
 * @author zhou
 * @since 2019.03.11
 */
public class EmailUtil {

	private static JavaMailSender SENDER = AppContextUtil.getBean(JavaMailSender.class);

	private static MailProperties PROPERTIES = AppContextUtil.getBean(MailProperties.class);

	private static AppProperties APP_PROPERTIES = AppContextUtil.getBean(AppProperties.class);


	/**
	 * 给某人发送异常邮件
	 *
	 * @param address 要发送道德邮箱
	 * @param topic   发送主题
	 * @param content 发送内容
	 */
	public static void send(String address, String topic, String content) {
		if(APP_PROPERTIES.isEmail()){
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(PROPERTIES.getUsername());
			message.setTo(address);
			message.setSubject(topic);
			message.setText(content);
			SENDER.send(message);
		}
	}
}
