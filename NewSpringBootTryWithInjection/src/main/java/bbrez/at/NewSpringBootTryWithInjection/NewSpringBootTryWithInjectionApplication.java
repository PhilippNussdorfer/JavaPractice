package bbrez.at.NewSpringBootTryWithInjection;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class NewSpringBootTryWithInjectionApplication implements CommandLineRunner {

	@Autowired
	private School school;

	public static void main(String[] args) {
		SpringApplication.run(NewSpringBootTryWithInjectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Started!");
		school.startSchool();
	}
}
