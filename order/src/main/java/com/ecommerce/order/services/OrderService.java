package com.ecommerce.order.services;

import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.enums.OrderStatus;
import com.ecommerce.order.models.*;
import com.ecommerce.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
//    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

//    public OrderService(CartService cartService) {
//        this.cartService = cartService;
//    }

    public Optional<OrderResponse> createOrder(String userId) {
//        Validate for cart items
        List<CartItem> cartItems = cartService.getCart(userId);   // This return  list of cart items
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
//        Validate for User
//        Optional<User> userData =  userRepository.findById(Long.valueOf(userId));


//        if(userData.isEmpty()){
//            return Optional.empty();
//        }
//        User user = userData.get();


//        Calculate Total Price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem :: getPrice)
                .reduce(BigDecimal.ZERO , BigDecimal::add);
//        Create Order

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item-> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
//        Clear the cart
        cartService.clearCart(userId);


        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                savedOrder.getCreatedAt()
        );
    }
}
