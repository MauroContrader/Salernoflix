package Mauro.Salernoflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalernoflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalernoflixApplication.class, args);
	}

}
