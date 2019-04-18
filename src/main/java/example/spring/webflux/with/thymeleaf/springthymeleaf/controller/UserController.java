package example.spring.webflux.with.thymeleaf.springthymeleaf.controller;

import example.spring.webflux.with.thymeleaf.springthymeleaf.config.CurrentUser;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserType;
import example.spring.webflux.with.thymeleaf.springthymeleaf.service.UserService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

import java.io.File;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String user(Model model, @AuthenticationPrincipal CurrentUser currentUser){
        User admin = currentUser.getUser();
        model.addAttribute("currentUser",admin);
        model.addAttribute("userList",new ReactiveDataDriverContextVariable(userService
                .getAll()
                .filter(user -> user.getUserType() != UserType.ADMIN),1));
        return "user";
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    Mono<ResponseEntity> delete(@PathVariable("id")String userId){
        return userService.deleteById(userId)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}