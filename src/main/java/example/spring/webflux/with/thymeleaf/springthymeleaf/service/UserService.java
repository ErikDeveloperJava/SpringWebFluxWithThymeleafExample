package example.spring.webflux.with.thymeleaf.springthymeleaf.service;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import example.spring.webflux.with.thymeleaf.springthymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> add(User user){
        return userRepository.save(user);
    }

    public Flux<User> getAll(){
        return userRepository.findAll();
    }

    public Mono<Void> deleteById(String id){
        return userRepository.deleteById(id);
    }
}