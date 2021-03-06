package priv.zhou.common.properties;

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

	private String gate;

	private String name;

	private boolean enableEmail;

	private String adminEmail;

	private String fileService;

	private String fileUploadPrefix;

	private Integer accessLimit;

	private Integer cacheSecond = 60 * 60 * 24 * 30 * 12; //缓存一年

}

