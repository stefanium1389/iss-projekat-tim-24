package tim24.projekat.uberapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import tim24.projekat.uberapp.config.JacksonConfig;

@SpringBootApplication
@Import(JacksonConfig.class)
public class UberappApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberappApplication.class, args);
	}
}
