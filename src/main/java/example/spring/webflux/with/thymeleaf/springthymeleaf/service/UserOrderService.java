package example.spring.webflux.with.thymeleaf.springthymeleaf.service;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserOrder;
import example.spring.webflux.with.thymeleaf.springthymeleaf.repository.UserOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserOrderService {

    @Autowired
    private UserOrderRepository userOrderRepository;

    public Mono<UserOrder> add(UserOrder userOrder){
        return userOrderRepository.save(userOrder);
    }

    public Flux<UserOrder> getAllByUserId(String userId){
        return userOrderRepository.findAllByUserId(userId);
    }
}