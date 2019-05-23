package br.com.alurapic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.alurapic.api.enums.Profile;
import br.com.alurapic.api.model.User;
import br.com.alurapic.api.repository.UserRepository;

@SpringBootApplication
public class AluraPicApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluraPicApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, passwordEncoder);
		};
	}

	private void initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User admin = new User();
		admin.setUserName("gilberto");
		admin.setPassword(passwordEncoder.encode("123"));
		admin.setProfile(Profile.ROLE_CUSTOMER);

		User find = userRepository.findByUserName("gilberto");
		if (find == null) {
			userRepository.save(admin);
		}
	}
}
