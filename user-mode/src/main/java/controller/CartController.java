package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/cart")
public class CartController {

    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public ResponseEntity<PlaceOrderResponse> placeOrder(
            @Validated @RequestBody PlaceOrderRequest request) {

    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.DELETE)
    public ResponseEntity<CancelOrderResponse> cancelOrder() {

    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ResponseEntity<ViewCartResponse> viewCart(
            @Validated @RequestBody ViewCartRequest request) {

    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public ResponseEntity<EditCartResponse> editCart(
            @Validated @RequestBody EditCartRequest request
    ) {

    }


}
