package example.spring.webflux.with.thymeleaf.springthymeleaf.controller;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserType;
import example.spring.webflux.with.thymeleaf.springthymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerGet(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping
    public Mono<RedirectView> registerPost(@ModelAttribute("user") User user){
        user.setUserType(UserType.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService
                .add(user)
                .map(u -> new RedirectView("/login"));
    }
}
