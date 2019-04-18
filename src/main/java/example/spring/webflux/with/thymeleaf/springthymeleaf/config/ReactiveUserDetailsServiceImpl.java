package example.spring.webflux.with.thymeleaf.springthymeleaf.config;

import example.spring.webflux.with.thymeleaf.springthymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository
                .findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("user not found")))
                .map(CurrentUser::new);
    }
}
