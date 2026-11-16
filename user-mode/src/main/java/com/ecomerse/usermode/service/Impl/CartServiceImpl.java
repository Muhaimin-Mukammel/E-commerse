package com.ecomerse.usermode.service.Impl;

import com.ecomerse.usermode.dto.cartdto.*;
import com.ecomerse.usermode.entity.Cart;
import com.ecomerse.usermode.entity.CartItem;
import com.ecomerse.usermode.entity.Product;
import com.ecomerse.usermode.entity.UserAccount;
import com.ecomerse.usermode.repository.AccountRepository;
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

    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,  ProductRepository productRepository, AccountRepository accountRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public AddItemToCartResponse addItemToCart(UUID clientId, UUID product_id, Integer quantity) {
        Cart cart = cartRepository.findByUserIdAndStatus(getUser_id(clientId), CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product_id))
                .findFirst().orElse(null);

        if(existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }else {
            CartItem newCartItem = new CartItem(cart, product, quantity);
            cart.getCartItems().add(newCartItem);
        }

        CalculateTotalPriceOfCart(cart);
        cart.setUpdatedAt(Instant.now());

        cartRepository.save(cart);

        return new AddItemToCartResponse("Item Added to Cart Successfully", cart.getCart_id(), cart.getTotalValue());
    }

    @Transactional
    public RemoveItemFromCartResponse removeItemFromCart(UUID clientId, UUID product_id, Integer quantity) {
        Cart cart = cartRepository.findByUserIdAndStatus(getUser_id(clientId), CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        CartItem targetitem = cart.getCartItems().stream().filter(item -> item.getProduct().getProductId().equals(product_id)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found in the cart"));

        if(targetitem.getQuantity() > quantity) {
            targetitem.setQuantity(targetitem.getQuantity() - quantity);
        } else  {
            cart.getCartItems().remove(targetitem);
            targetitem.setCart(null);
        }

        CalculateTotalPriceOfCart(cart);
        cart.setUpdatedAt(Instant.now());

        cartRepository.save(cart);

        return new RemoveItemFromCartResponse("Item Removed from Cart Successfully", cart.getCart_id(), cart.getTotalValue());
    }

    @Transactional(readOnly = true)
    public GetCartResponse getCartByCartId(UUID clientId) {
        UUID user_id = getUser_id(clientId);
        Cart cart = cartRepository.findByUserIdAndStatus(user_id, CartStatus.ACTIVE)
                .orElse(null);

        if(cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }

        List<GetCartResponse.CartItemResponse> itemResponse = cart.getCartItems().stream()
                .map(item -> new GetCartResponse.CartItemResponse(
                        item.getProduct().getProductId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getProductStatus(),
                        item.getProduct().getProductPrice(),
                        null)).toList();

        return new GetCartResponse(
                "Successful",
                cart.getCart_id(),
                cart.getStatus(),
                cart.getCreatedAt(),
                cart.getUpdatedAt(),
                itemResponse,
                cart.getTotalValue(),
                "BDT"
        );
    }

    @Transactional
    public CreateCartResponse createCart(UUID clientId) {
        UUID user_id = getUser_id(clientId);
        Cart cart = cartRepository.findByUserIdAndStatus(user_id, CartStatus.ACTIVE)
                .orElse(null);
        if(cart != null) {
            return new CreateCartResponse(
                    "Cart already exists",
                    cart.getCart_id(),
                    cart.getStatus(),
                    cart.getUpdatedAt()
            );
        }

        Cart newCart = new Cart(user_id);
        cartRepository.save(newCart);

        return new CreateCartResponse(
                "Cart created successfully",
                newCart.getCart_id(),
                newCart.getStatus(),
                newCart.getCreatedAt()
        );
    }

    @Transactional
    public ClearCartResponse clearCart(UUID clientId) {
        UUID user_id = getUser_id(clientId);
        Cart cart =  cartRepository.findByUserIdAndStatus(user_id, CartStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cart.getCartItems().clear();
        cart.setUpdatedAt(Instant.now());
        cart.setTotalValue(BigDecimal.ZERO);

        cartRepository.save(cart);

        return new ClearCartResponse("Cart cleared successfully", cart.getCart_id(), cart.getTotalValue());
    }

    private void CalculateTotalPriceOfCart(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getProduct().getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalValue(totalPrice);
    }

    private UUID getUser_id(UUID clientId) {
        UserAccount user = accountRepository.findByClientId(clientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getUserId();
    }
}
