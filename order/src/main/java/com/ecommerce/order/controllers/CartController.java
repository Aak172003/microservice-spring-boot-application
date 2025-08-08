package com.ecommerce.order.controllers;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private  final CartService cartService;

//    Spring can inject or initialize on run-time if I create constructor
//    public CartController(CartService cartService) {
//        this.cartService = cartService;
//    }



    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId , @RequestBody CartItemRequest cartItemRequest
    ){
        if(!cartService.addToCart(userId , cartItemRequest)){
            return ResponseEntity.badRequest().body("Product Out of Stock or User Not Found or Product Not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId ,@PathVariable String productId){
        boolean deleted =  cartService.deleteItemFromCart(userId , productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}
