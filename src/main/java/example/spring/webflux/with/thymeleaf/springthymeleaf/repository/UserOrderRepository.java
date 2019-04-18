package example.spring.webflux.with.thymeleaf.springthymeleaf.repository;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserOrderRepository extends ReactiveMongoRepository<UserOrder,String> {

    Flux<UserOrder> findAllByUserId(String userId);
}