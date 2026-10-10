package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.cartdto.*;
import com.ecomerse.usermode.entity.Cart;
import com.ecomerse.usermode.entity.CartItem;
import com.ecomerse.usermode.entity.Product;
import com.ecomerse.usermode.repository.CartRepository;
import com.ecomerse.usermode.repository.ProductRepository;
import com.ecomerse.usermode.service.CartService;
import com.ecomerse.usermode.status.CartStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,  ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public AddItemToCartResponse addItemToCart(UUID user_id, UUID product_id, Integer quantity) {

        Cart cart = cartRepository.findByUser_idAndStatus(user_id, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        CartItem existingItem = cart.getCart_items().stream()
                .filter(item -> item.getProduct().getProduct_id().equals(product_id))
                .findFirst().orElse(null);

        if(existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }else {
            CartItem newCartItem = new CartItem(cart, product, quantity);
            cart.getCart_items().add(newCartItem);
        }

        CalculateTotalPriceOfCart(cart);
        cart.setUpdated_at(Instant.now());

        cartRepository.save(cart);

        return new AddItemToCartResponse("Item Added to Cart Successfully", cart.getCart_id(), cart.getTotal_value());
    }

    @Transactional
    public RemoveItemFromCartResponse removeItemFromCart(UUID user_id, UUID product_id, Integer quantity) {
        Cart cart = cartRepository.findByUser_idAndStatus(user_id, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        CartItem targetitem = cart.getCart_items().stream().filter(item -> item.getProduct().getProduct_id().equals(product_id)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found in the cart"));

        if(targetitem.getQuantity() > quantity) {
            targetitem.setQuantity(targetitem.getQuantity() - quantity);
        } else  {
            cart.getCart_items().remove(targetitem);
            targetitem.setCart(null);
        }

        CalculateTotalPriceOfCart(cart);
        cart.setUpdated_at(Instant.now());

        cartRepository.save(cart);

        return new RemoveItemFromCartResponse("Item Removed from Cart Successfully", cart.getCart_id(), cart.getTotal_value());
    }

    @Transactional(readOnly = true)
    public GetCartResponse getCartByCartId(UUID user_id) {
        Cart cart = cartRepository.findByUser_idAndStatus(user_id, CartStatus.ACTIVE)
                .orElse(null);

        if(cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }

        List<GetCartResponse.CartItemResponse> itemResponse = cart.getCart_items().stream()
                .map(item -> new GetCartResponse.CartItemResponse(
                        item.getProduct().getProduct_id(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getProduct_status(),
                        item.getProduct().getProduct_price(),
                        null)).toList();

        return new GetCartResponse(
                "Successful",
                cart.getCart_id(),
                cart.getStatus(),
                cart.getCreated_at(),
                cart.getUpdated_at(),
                itemResponse,
                cart.getTotal_value(),
                "BDT"
        );
    }

    @Transactional
    public CreateCartResponse createCart(UUID user_id) {
        Cart cart = cartRepository.findByUser_idAndStatus(user_id, CartStatus.ACTIVE)
                .orElse(null);
        if(cart != null) {
            return new CreateCartResponse(
                    "Cart already present",
                    cart.getCart_id(),
                    cart.getStatus(),
                    cart.getCreated_at()
            );
        }

        Cart newCart = new Cart(user_id);
        cartRepository.save(newCart);

        return new CreateCartResponse(
                "Cart created successfully",
                cart.getCart_id(),
                cart.getStatus(),
                cart.getCreated_at()
        );
    }

    @Transactional
    public ClearCartResponse clearCart(UUID user_id) {
        Cart cart =  cartRepository.findByUser_idAndStatus(user_id, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.getCart_items().clear();
        cart.setUpdated_at(Instant.now());
        cart.setTotal_value(BigDecimal.ZERO);

        cartRepository.save(cart);

        return new ClearCartResponse("Cart cleared successfully", cart.getCart_id(), cart.getTotal_value());
    }

    private void CalculateTotalPriceOfCart(Cart cart) {
        BigDecimal totalPrice = cart.getCart_items().stream()
                .map(item -> item.getProduct().getProduct_price().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotal_value(totalPrice);
    }
}
