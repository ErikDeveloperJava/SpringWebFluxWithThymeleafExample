package example.spring.webflux.with.thymeleaf.springthymeleaf.repository;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product,String> {
}