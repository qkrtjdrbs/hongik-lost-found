package study.hlf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HlfApplication {

	public static void main(String[] args) {
		SpringApplication.run(HlfApplication.class, args);
	}

}
