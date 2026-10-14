package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.orderdto.*;
import com.ecomerse.usermode.entity.*;
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

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,  CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
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

        Order order = new Order(cart.getCart_id(), user_id);

        for(CartItem cartItem : cart.getCart_items()){
            Product p = cartItem.getProduct();
            OrderItem oi = new OrderItem(
                    order,
                    p.getProduct_id(),
                    p.getName(),
                    p.getProduct_price(),
                    cartItem.getQuantity()
            );
            order.addItem(oi);
        }

        order.recalculateValue();
        orderRepository.save(order);

        return new CreateOrderResponse("Order created Successfully", order.getOrder_id(), order.getOrderStatus(), order.getValue(), order.getCreated_at());
    }

    @Override
    @Transactional
    public ConfirmOrderResponse confirmOrder(UUID user_id, UUID order_id) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setOrderPaymentStatus(OrderPaymentStatus.PENDING_PAYMENT);
        orderRepository.save(order);

        return new ConfirmOrderResponse("Order Confirmed Successfully", order.getOrder_id(), order.getOrderStatus());
    }

    @Override
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

    @Override
    @Transactional
    public AddItemToOrderResponse addItemToOrder(UUID user_id, UUID order_id, UUID product_id, int quantity) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        OrderItem orderitem = order.getOrder_items().stream().filter(i -> i.getProduct_id().equals(product_id)).findFirst()
                .orElse(null);

        if(orderitem != null){
            orderitem.setQuantity(orderitem.getQuantity() + quantity);
        } else {
            OrderItem orderitem1 = new OrderItem(
                    order,
                    product.getProduct_id(),
                    product.getName(),
                    product.getProduct_price(),
                    quantity
            );
        }

        order.recalculateValue();
        orderRepository.save(order);

        return new AddItemToOrderResponse("Item added to Order", order.getOrder_id(), order.getValue());
    }

    @Override
    @Transactional
    public RemoveItemFromOrderResponse removeItemFromOrder(UUID user_id, UUID order_id, UUID product_id, int quantity) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        OrderItem orderItem = order.getOrder_items().stream().filter(item -> item.getProduct_id().equals(product_id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in the Order"));

        int quantityAfterRemove = orderItem.getQuantity() - quantity;
        if(quantityAfterRemove < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity cannot be less than 0");
        } else if (quantityAfterRemove == 0) {
            order.getOrder_items().remove(orderItem);
        } else {
            orderItem.setQuantity(quantityAfterRemove);
        }

        order.recalculateValue();
        orderRepository.save(order);

        return new RemoveItemFromOrderResponse("Item removed from Order", order.getOrder_id(), order.getValue());
    }

    @Override
    @Transactional
    public ClearOrderResponse clearOrder(UUID user_id, UUID order_id) {
        Order order = orderRepository.findByOrder_idAndUser_id(order_id, user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getOrderStatus() != OrderStatus.NOT_CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Order is " + order.getOrderStatus() + " and cannot be modified");
        }

        order.getOrder_items().clear();
        order.recalculateValue();
        orderRepository.save(order);

        return new ClearOrderResponse("Order has been cleared", order.getOrder_id(), order.getValue());
    }
}
