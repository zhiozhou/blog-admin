package priv.zhou.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统属性
 *
 * @author zhou
 * @since 0.1.0
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

	private Integer accessLimit;

	private String aesSeed;

	private String fileService;

	private String fileUploadPrefix;


	private Integer cacheSecond = 60 * 60 * 24 * 30 * 12; //缓存一年

}

