package example.spring.webflux.with.thymeleaf.springthymeleaf.controller;

import example.spring.webflux.with.thymeleaf.springthymeleaf.model.Product;
import example.spring.webflux.with.thymeleaf.springthymeleaf.service.ProductService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.Date;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Value("${image.path}")
    private String imagePath;

    @PostMapping
    public Mono add(FilePart image, Product product) {
        String productImageUrl = System.currentTimeMillis() + "_" + image.filename();
        product.setCreateDate(new Date());
        product.setImgUrl(productImageUrl);
        return productService
                .add(product)
                .flatMap(product1 -> image.transferTo(new File(imagePath,productImageUrl)))
                .then(Mono.just(new RedirectView("/")));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    Mono<ResponseEntity> delete(@PathVariable("id")String productId){
        return productService.getById(productId)
                .switchIfEmpty(Mono.error(new NotFound()))
                .flatMap(product -> {
                    File file = new File(imagePath,product.getImgUrl());
                    file.delete();
                    return productService.deleteById(productId);})
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}