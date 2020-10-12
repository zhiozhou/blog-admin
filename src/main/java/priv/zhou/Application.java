package priv.zhou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

//@EnableScheduling
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		new SpringApplication(Application.class).run(args);
	}
}
