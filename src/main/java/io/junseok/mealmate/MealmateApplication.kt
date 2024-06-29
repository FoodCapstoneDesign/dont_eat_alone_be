package io.junseok.mealmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MealmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealmateApplication.class, args);
	}

}
