package example.spring.webflux.with.thymeleaf.springthymeleaf.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderForm {

    private int count;

    private String productId;
}
