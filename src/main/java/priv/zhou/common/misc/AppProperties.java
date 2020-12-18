package priv.zhou.common.misc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhou
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	public static String ENC;

	private String gate;

	private String name;

	private boolean email;

	private String adminEmail;

	private String fileService;

	private String fileUploadPrefix;

	private Integer accessLimit;

	private Integer cacheSecond = 60 * 60 * 24 * 30 * 12; //缓存一年

	public void setEnc(String enc) {
		ENC = enc;
	}
}

