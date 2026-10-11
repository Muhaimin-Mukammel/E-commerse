package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.orderdto.*;
import com.ecomerse.usermode.entity.Cart;
import com.ecomerse.usermode.entity.CartItem;
import com.ecomerse.usermode.entity.Order;
import com.ecomerse.usermode.entity.Product;
import com.ecomerse.usermode.repository.CartRepository;
import com.ecomerse.usermode.repository.OrderRepository;
import com.ecomerse.usermode.repository.ProductRepository;
import com.ecomerse.usermode.service.OrderService;
import com.ecomerse.usermode.status.CartStatus;
import com.ecomerse.usermode.status.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,  CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public CreateOrderResponse createOrder(UUID user_id) {
        Cart cart = cartRepository.findByUser_idAndStatus(user_id, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        if(cart.getCart_items().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order cannot be created from empty cart");
        }

        Optional<Order> o = orderRepository.findBuUser_idAndStatus(user_id, OrderStatus.NOT_CONFIRMED);
        if(o.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already exists");
        }

        Order order = new Order(cart.getCart_id(), user_id, cart.getTotal_value());
        orderRepository.save(order);

        return new CreateOrderResponse("Order created Successfully", order.getOrder_id(), order.getOrderStatus(), order.getValue(), order.getCreated_at());
    }

    @Transactional
    public ConfirmOrderResponse confirmOrder(UUID user_id, UUID order_id) {
        boolean TEST_TRANSACTION_COMPLETE = true;

        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        return new ConfirmOrderResponse("Order Confirmed Successfully", order.getOrder_id(), order.getOrderStatus());
    }

    @Transactional
    public CancelOrderResponse cancelOrder(UUID user_id, UUID order_id) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        if(order.getOrderStatus() == OrderStatus.CANCELED){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already cancelled");
        }

        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        return new CancelOrderResponse("Order has been cancelled",  order.getOrder_id(), order.getOrderStatus());
    }

    @Transactional
    public AddItemToOrderResponse addItemToOrder(UUID user_id, UUID order_id, UUID product_id, int quantity) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        Cart cart = cartRepository.findById(order.getCart_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found on Order : " + order.getOrder_id()));

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        CartItem cartItem = cart.getCart_items().stream()
                .filter(temp -> temp.getProduct().getProduct_id().equals(product)).findFirst().orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cart.getCart_items().add(new CartItem(cart,  product, quantity));
        }

        CalculateTotalPriceOfCart(cart, order);

        return new AddItemToOrderResponse("Item added to Order", order.getOrder_id(), order.getValue());
    }

    @Transactional
    public RemoveItemFromOrderResponse removeItemFromOrder(UUID user_id, UUID order_id, UUID product_id, int quantity) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        Cart cart = cartRepository.findById(order.getCart_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found on Order : " + order.getOrder_id()));

        CartItem cartItem = cart.getCart_items().stream().filter(item -> item.getProduct().getProduct_id().equals(product_id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in Order"));

        int quantityAfterRemove = cartItem.getQuantity() - quantity;
        if(quantityAfterRemove < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity cannot be less than 0");
        } else if (quantityAfterRemove == 0) {
            cart.getCart_items().remove(cartItem);
        } else {
            cartItem.setQuantity(quantityAfterRemove);
        }

        CalculateTotalPriceOfCart(cart, order);
        return new RemoveItemFromOrderResponse("Item removed from Order", order.getOrder_id(), order.getValue());
    }

    @Transactional
    public ClearOrderResponse clearOrder(UUID user_id, UUID order_id) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        Cart cart = cartRepository.findById(order.getCart_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found on Order : " + order.getOrder_id()));

        cart.getCart_items().clear();
        cart.setTotal_value(BigDecimal.ZERO);
        cart.setUpdated_at(Instant.now());
        cartRepository.save(cart);

        return new ClearOrderResponse("Order has been cleared", order.getOrder_id(), order.getValue());
    }

    private void CalculateTotalPriceOfCart(Cart cart, Order order) {
        BigDecimal totalPrice = cart.getCart_items().stream()
                .map(item -> item.getProduct().getProduct_price().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotal_value(totalPrice);
        cart.setUpdated_at(Instant.now());
        cartRepository.save(cart);

        order.setValue(totalPrice);
        orderRepository.save(order);
    }
}
