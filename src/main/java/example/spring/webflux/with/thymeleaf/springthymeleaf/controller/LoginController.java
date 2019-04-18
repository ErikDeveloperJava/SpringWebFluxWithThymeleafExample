package example.spring.webflux.with.thymeleaf.springthymeleaf.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(ServerHttpRequest serverHttpRequest, Model model,
                        @AuthenticationPrincipal UsernamePasswordAuthenticationToken token){
        //here check Does error parameter exist (error = true/false)
        model.addAttribute("error",serverHttpRequest.getQueryParams().containsKey("error"));
        return "login";
    }
}
