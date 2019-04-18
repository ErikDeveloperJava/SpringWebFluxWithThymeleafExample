package example.spring.webflux.with.thymeleaf.springthymeleaf.config;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{

    @Autowired
    private ReactiveUserDetailsService  reactiveUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private ReactiveAuthenticationManager authenticationManager(){
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/user/order")
                .hasAuthority(UserType.USER.name())
                .pathMatchers("/user")
                .hasAuthority(UserType.ADMIN.name())
                .pathMatchers(HttpMethod.POST,"/product")
                .hasAuthority(UserType.ADMIN.name())
                .anyExchange()
                .permitAll()
        .and()
                .formLogin()
                .loginPage("/login")
                .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/login?error"))
                .authenticationManager(authenticationManager())
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/"))
        .and()
                .logout()
                .logoutUrl("/logout")
        .and()
                .csrf()
                .disable()
                .build();
    }
}
