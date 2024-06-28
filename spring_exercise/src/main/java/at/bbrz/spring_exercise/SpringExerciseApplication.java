package at.bbrz.spring_exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@SpringBootApplication
public class SpringExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringExerciseApplication.class, args);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.headers(AbstractHttpConfigurer::disable)

				.authorizeHttpRequests(auth -> auth
						.requestMatchers(antMatcher("/h2-console/**")).permitAll()
						.requestMatchers(antMatcher("/player/**")).permitAll())

				.csrf(csrf -> csrf
						.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
						.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/player/**")));

		return httpSecurity.build();
	}
}