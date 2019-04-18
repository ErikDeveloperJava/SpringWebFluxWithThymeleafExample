package example.spring.webflux.with.thymeleaf.springthymeleaf.service;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.Product;
import example.spring.webflux.with.thymeleaf.springthymeleaf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<Product> getAll(){
        return productRepository.findAll();
    }

    public Mono<Product> add(Product product){
        return productRepository.save(product);
    }

    public Mono<Void> deleteById(String id){
        return productRepository.deleteById(id);
    }

    public Mono<Product> getById(String id){
        return productRepository.findById(id);
    }
}