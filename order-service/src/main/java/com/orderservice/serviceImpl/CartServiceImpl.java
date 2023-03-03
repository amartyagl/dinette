package com.orderservice.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.orderservice.model.Cart;

import com.orderservice.repository.CartRepository;
import com.orderservice.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addItemToCart(Cart cart) {
        Cart addCartItem = cartRepository.save(cart);
        return addCartItem;
    }

    @Override
    public Cart updateCartItem(long cartId, Cart cart) {
        Optional<Cart> cartFromDb = cartRepository.findById(cartId);
        Cart updateCart = null;
        if (cartFromDb.isPresent()) {
            Cart CartFromRepo = cartFromDb.get();
            CartFromRepo.setQuantity(cart.getQuantity());
            CartFromRepo.setValue(cart.getValue());
            CartFromRepo.setFood(cart.getFood());
            updateCart = cartRepository.save(CartFromRepo);
        }

        return updateCart;
    }

    @Override
    public List<Cart> getCart() {
        return cartRepository.findAll();
    }

    @Override
    public Cart findById(long cartId) {
        return cartRepository.findById(cartId).get();
    }

    @Override
    public void deleteCartItem(long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public List<Cart> getAllItemsFromCart(long cartId) {
        List<Cart> items = cartRepository.getCart(cartId);
        return items;
    }

    @Override
    public List<Cart> getCartByEmail(String emailId) {
        List<Cart> getByEmail = cartRepository.findByEmailId(emailId.toLowerCase());

        return getByEmail;
    }

    @Override
    public void deleteByEmail(String emailId) {
        cartRepository.deleteByEmailId(emailId);

    }

}
