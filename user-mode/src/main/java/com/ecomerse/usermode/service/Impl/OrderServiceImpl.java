package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.orderdto.*;
import com.ecomerse.usermode.entity.*;
import com.ecomerse.usermode.repository.AccountRepository;
import com.ecomerse.usermode.repository.CartRepository;
import com.ecomerse.usermode.repository.OrderRepository;
import com.ecomerse.usermode.repository.ProductRepository;
import com.ecomerse.usermode.service.OrderService;
import com.ecomerse.usermode.status.CartStatus;
import com.ecomerse.usermode.status.OrderPaymentStatus;
import com.ecomerse.usermode.status.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,  CartRepository cartRepository,  AccountRepository accountRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public CreateOrderResponse createOrder(UUID clientId) {
        UUID user_id = getUser_id(clientId);
        Cart cart = cartRepository.findByUserIdAndStatus(user_id, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        if(cart.getCartItems().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order cannot be created from empty cart");
        }

        Optional<Order> o = orderRepository.findByUserIdAndStatus(user_id, OrderStatus.NOT_CONFIRMED);
        if(o.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already exists");
        }

        Order order = new Order(cart.getCart_id(), user_id);

        for(CartItem cartItem : cart.getCartItems()){
            Product p = cartItem.getProduct();
            OrderItem oi = new OrderItem(
                    order,
                    p.getProductId(),
                    p.getName(),
                    p.getProductPrice(),
                    cartItem.getQuantity()
            );
            order.addItem(oi);
        }

        order.recalculateValue();
        orderRepository.save(order);

        return new CreateOrderResponse("Order created Successfully", order.getOrder_id(), order.getStatus(), order.getValue(), order.getCreatedAt());
    }

    @Override
    @Transactional
    public ConfirmOrderResponse confirmOrder(UUID clientId, UUID order_id) {
        UUID user_id = getUser_id(clientId);
        Order order = orderRepository.findByOrderIdAndUserId(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getStatus() + " and cannot be modified");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order.setOrderPaymentStatus(OrderPaymentStatus.PENDING_PAYMENT);
        orderRepository.save(order);

        return new ConfirmOrderResponse("Order Confirmed Successfully", order.getOrder_id(), order.getStatus());
    }

    @Override
    @Transactional
    public CancelOrderResponse cancelOrder(UUID clientId, UUID order_id) {
        UUID user_id = getUser_id(clientId);
        Order order = orderRepository.findByOrderIdAndUserId(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (order.getStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is " + order.getStatus() + " and cannot be modified");
        }

        if(order.getStatus() == OrderStatus.CANCELED){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        return new CancelOrderResponse("Order has been cancelled",  order.getOrder_id(), order.getStatus());
    }

    @Override
    @Transactional
    public AddItemToOrderResponse addItemToOrder(UUID clientId, UUID order_id, UUID product_id, int quantity) {
        UUID user_id = getUser_id(clientId);
        Order order = orderRepository.findByOrderIdAndUserId(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (order.getStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getStatus() + " and cannot be modified");
        }

        OrderItem orderitem = order.getOrderItems().stream().filter(i -> i.getProductId().equals(product_id)).findFirst()
                .orElse(null);

        if(orderitem != null){
            orderitem.setQuantity(orderitem.getQuantity() + quantity);
        } else {
            OrderItem orderItem1 = new OrderItem(
                    order,
                    product.getProductId(),
                    product.getName(),
                    product.getProductPrice(),
                    quantity
            );
            order.getOrderItems().add(orderItem1);
        }

        order.recalculateValue();
        orderRepository.save(order);

        return new AddItemToOrderResponse("Item added to Order", order.getOrder_id(), order.getValue());
    }

    @Override
    @Transactional
    public RemoveItemFromOrderResponse removeItemFromOrder(UUID clientId, UUID order_id, UUID product_id, int quantity) {
        UUID user_id = getUser_id(clientId);
        Order order = orderRepository.findByOrderIdAndUserId(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getStatus() + " and cannot be modified");
        }

        OrderItem orderItem = order.getOrderItems().stream().filter(item -> item.getProductId().equals(product_id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in the Order"));

        int quantityAfterRemove = orderItem.getQuantity() - quantity;
        if(quantityAfterRemove < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity cannot be less than 0");
        } else if (quantityAfterRemove == 0) {
            order.getOrderItems().remove(orderItem);
        } else {
            orderItem.setQuantity(quantityAfterRemove);
        }

        order.recalculateValue();
        orderRepository.save(order);

        return new RemoveItemFromOrderResponse("Item removed from Order", order.getOrder_id(), order.getValue());
    }

    @Override
    @Transactional
    public ClearOrderResponse clearOrder(UUID clientId, UUID order_id) {
        UUID user_id = getUser_id(clientId);
        Order order = orderRepository.findByOrderIdAndUserId(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getStatus() + " and cannot be modified");
        }

        order.getOrderItems().clear();
        order.recalculateValue();
        orderRepository.save(order);

        return new ClearOrderResponse("Order has been cleared", order.getOrder_id(), order.getValue());
    }

    private UUID getUser_id(UUID clientId) {
        UserAccount user = accountRepository.findByClientId(clientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getUserId();
    }
}
