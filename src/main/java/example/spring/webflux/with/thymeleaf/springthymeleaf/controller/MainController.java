package example.spring.webflux.with.thymeleaf.springthymeleaf.controller;

import example.spring.webflux.with.thymeleaf.springthymeleaf.config.CurrentUser;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.Product;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserType;
import example.spring.webflux.with.thymeleaf.springthymeleaf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import java.util.Comparator;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String main(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        User user;
        if (currentUser == null) {
            user = User
                    .builder()
                    .userType(UserType.ANONYMOUS)
                    .build();
        } else {
            user = currentUser.getUser();
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("products", new ReactiveDataDriverContextVariable(productService
                .getAll()
                .sort(Comparator.comparing(Product::getCreateDate)), 1));
        if (user.getUserType() == UserType.ADMIN) {
            model.addAttribute("product", new Product());
        }
        return "index";
    }
}
