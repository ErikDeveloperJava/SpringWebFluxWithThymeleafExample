package example.spring.webflux.with.thymeleaf.springthymeleaf.repository;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User,String> {

    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByUserType(UserType userType);
}
