package example.spring.webflux.with.thymeleaf.springthymeleaf;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserType;
import example.spring.webflux.with.thymeleaf.springthymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
public class SpringThymeleafApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringThymeleafApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.existsByUserType(UserType.ADMIN)
                .subscribe(result -> {
                    if (!result) {
                        User admin = User
                                .builder()
                                .name("Admin")
                                .surname("Admin")
                                .email("admin@gmail.com")
                                .password(passwordEncoder.encode("admin"))
                                .userType(UserType.ADMIN)
                                .build();
                        userRepository.save(admin)
                                .subscribe();
                    }
                });
    }
}