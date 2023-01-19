package tim24.projekat.uberapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import tim24.projekat.uberapp.config.JacksonConfig;
import tim24.projekat.uberapp.config.WebSocketConfig;


@SpringBootApplication
@Import({JacksonConfig.class, WebSocketConfig.class}) //verovatno nije potrebno
@EnableScheduling
public class UberappApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberappApplication.class, args);
	}
}
