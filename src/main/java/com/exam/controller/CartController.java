package com.exam.controller;

import com.exam.entity.Carts;
import com.exam.dto.CartItemsDTO;
import com.exam.dto.CartsDTO;
import com.exam.entity.CartItems;
import com.exam.service.CartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
    CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartsDTO> getCartById(@PathVariable int cartId) {
        CartsDTO cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build(); // 404 반환
        }
        return ResponseEntity.ok(cart);
    }

//    @GetMapping("/{cartId}/items")
//    public ResponseEntity<List<CartItemsDTO>> getItemsByCartId(@PathVariable int cartId) {
//        List<CartItemsDTO> items = cartService.getItemsByCartId(cartId);
//        if (items == null || items.isEmpty()) {
//            return ResponseEntity.notFound().build(); // 404 반환
//        }
//        return ResponseEntity.ok(items);
//    }
    
    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemsDTO>> findcartItemsProducts(@PathVariable int cartId) {
        List<CartItemsDTO> items = cartService.findcartItemsProducts(cartId);
        if (items == null || items.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 반환
        }
        return ResponseEntity.ok(items);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody CartItemsDTO dto) {
    	int addItem = cartService.addItem(dto);
//    	logger.info("logger: Controller: {}, {}, {}",  cartId, productId, amount);
//    	cartService.addItem(cartId, productId, amount);
//    	logger.info("logger: Controller: {}, {}, {}",  cartId, productId, amount);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items")
    public ResponseEntity<?> updateItemAmount(@RequestBody CartItemsDTO cartItem) {
        int cartItemId = cartItem.getCartItemId();
        int amount = cartItem.getAmount();
        cartService.updateItemAmount(cartItemId, amount);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/shipping")
    public ResponseEntity<?> updateShippingStatus(@RequestBody CartItemsDTO cartItem) {
        int cartItemId = cartItem.getCartItemId();
        boolean isShipping = cartItem.isShipping();
//        System.out.println("isShipping" + isShipping);
        cartService.updateShippingStatus(cartItemId, isShipping);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Integer> removeItemFromCart(@PathVariable int cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable int cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }
}