package com.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.orderservice.model.Cart;
import com.orderservice.service.CartService;
import com.orderservice.serviceImpl.SequenceGeneratorService;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    SequenceGeneratorService service;

    /*
     * This method is used to add the cart items and it will be saved in sequence.
     */
    @PostMapping(value = "addCart")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        cart.setCartId(service.getSequenceNumber(Cart.SEQUENCE_NAME));
        return new ResponseEntity<Cart>(cartService.addItemToCart(cart), HttpStatus.OK);
    }

    /*
     * This method is used to update the cart items like quantity, food.
     */
    @PutMapping(value = "cart/{cartId}")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart, @PathVariable long cartId) {
        return new ResponseEntity<Cart>(cartService.updateCartItem(cartId, cart), HttpStatus.OK);

    }

    /*
     * This method is used to get the cart list.
     */
    @GetMapping(value = "cart")
    public ResponseEntity<List<Cart>> getFromCart() {
        return new ResponseEntity<List<Cart>>(cartService.getCart(), HttpStatus.OK);
    }

    /*
     * This method is used to get the cart items by putting the card id.
     */
    @GetMapping(value = "cart/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable long cartId) {
        return new ResponseEntity<Cart>(cartService.findById(cartId), HttpStatus.OK);
    }

    /*
     * This method is used to delete the cart items by putting cart id.
     */
    @DeleteMapping(value = "cart/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable long cartId) {
        cartService.deleteCartItem(cartId);
        return new ResponseEntity<String>("CartItem deleted Successfully!!", HttpStatus.OK);
    }

    @DeleteMapping(value = "cartbyEmail/{emailId}")
    public ResponseEntity<String> deleteCart(@PathVariable String emailId) {
        cartService.deleteByEmail(emailId);
        return new ResponseEntity<String>("CartItem deleted Successfully!!", HttpStatus.OK);
    }

    @GetMapping(value = "food/{cartId}")
    public ResponseEntity<List<Cart>> getFromFood(@PathVariable long cartId) {
        return new ResponseEntity<>(cartService.getAllItemsFromCart(cartId), HttpStatus.OK);
    }

    @GetMapping(value = "cartByEmail/{emailId}")
    public ResponseEntity<List<Cart>> getCartFromEmail(@PathVariable String emailId) {
        return new ResponseEntity<>(cartService.getCartByEmail(emailId), HttpStatus.OK);
    }
}
