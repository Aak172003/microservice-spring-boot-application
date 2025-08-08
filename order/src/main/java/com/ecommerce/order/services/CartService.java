package com.ecommerce.order.services;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.repositories.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;

//    Spring can inject or initialize on run-time if I create constructor ,

//    public CartService(ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
//        this.productRepository = productRepository;
//        this.cartItemRepository = cartItemRepository;
//        this.userRepository = userRepository;
//    }



    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
//
//        Look for product is existed in our database or not
//        Optional<Product> productData = productRepository.findById(cartItemRequest.getProductId());
//        if(productData.isEmpty()){
//            return  false;
//        }
//        Product product = productData.get();
//        if(product.getStockQuantity() <  cartItemRequest.getQuantity()){
//            return  false;
//        }
//
//        Optional<User> userData = userRepository.findById(Long.valueOf(userId));
//
//        if(userData.isEmpty()){
//            return  false;
//        }
//        User user = userData.get();
//
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId , cartItemRequest.getProductId());

        if(existingCartItem != null){
            //for this particular cartItem exist for user, so we need to update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()  + cartItemRequest.getQuantity());
//            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));


            cartItemRepository.save(existingCartItem);
        }else {
//          This means not exist so create a new cartItem which is associated with that particular user
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(cartItemRequest.getQuantity());
//            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItem.setPrice(BigDecimal.valueOf(10000.000));

            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
//        Look for product is existed in our database or not
//        Optional<Product> productData = productRepository.findById(productId);
//        Product productThings = productData.get();
//
//        Optional<User> userData = userRepository.findById(Long.valueOf(userId));
//        User userThings = userData.get();

        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId , productId);

//        System.out.println("user ---------------- " + userThings);
//        System.out.println("product -------------------- " + productThings);

//        if(productData.isPresent() && userData.isPresent()){
//            cartItemRepository.deleteByUserIdAndProductId(userData.get() , productData.get());
//            return true;
//        }

        if(cartItem != null){
            cartItemRepository.delete(cartItem);
//            return true;
        }
        return false;
    }
//
    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }
//
    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
