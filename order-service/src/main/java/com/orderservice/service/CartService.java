package com.orderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.orderservice.model.Cart;

@Service
public interface CartService {

    Cart addItemToCart(Cart cart);

    Cart updateCartItem(long cartId, Cart cart);

    List<Cart> getCart();

    Cart findById(long cartId);

    void deleteCartItem(long cartId);

    List<Cart> getAllItemsFromCart(long cartId);

    List<Cart> getCartByEmail(String emailId);

    void deleteByEmail(String emailId);

}
