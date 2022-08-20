package cl.beto;

import java.util.TimeZone;

import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndCrudApplication.class, args);
	}
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
