package example.spring.webflux.with.thymeleaf.springthymeleaf.controller;

import example.spring.webflux.with.thymeleaf.springthymeleaf.config.CurrentUser;
import example.spring.webflux.with.thymeleaf.springthymeleaf.form.UserOrderForm;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.User;
import example.spring.webflux.with.thymeleaf.springthymeleaf.model.UserOrder;
import example.spring.webflux.with.thymeleaf.springthymeleaf.service.ProductService;
import example.spring.webflux.with.thymeleaf.springthymeleaf.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.RedirectView;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Controller
@RequestMapping("/user/order")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public Mono<RedirectView> order(@AuthenticationPrincipal CurrentUser currentUser, UserOrderForm userOrderForm){
        return productService
                .getById(userOrderForm.getProductId())
                .switchIfEmpty(Mono.error(ChangeSetPersister.NotFoundException::new))
                .flatMap(product -> userOrderService.add(UserOrder
                        .builder()
                        .count(userOrderForm.getCount())
                        .orderDate(new Date())
                        .price(userOrderForm.getCount() * product.getPrice())
                        .product(product)
                        .user(currentUser.getUser())
                        .build()))
                .flatMap(userOrder -> Mono.just(new RedirectView("/user/order")));
    }

    @GetMapping
    public String userOrderPage(Model model,@AuthenticationPrincipal CurrentUser currentUser){
        User user = currentUser.getUser();
        model.addAttribute("currentUser",user);
        model.addAttribute("userOrderList",new ReactiveDataDriverContextVariable(userOrderService
                .getAllByUserId(user.getId())
                .sort(Comparator.comparing(UserOrder::getOrderDate).reversed())
                ,1));
        return "user_order";
    }
}
