package example.spring.webflux.with.thymeleaf.springthymeleaf.config;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class CurrentUser extends org.springframework.security.core.userdetails.User{

    @Getter
    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserType().name()));
        this.user = user;
    }
}
