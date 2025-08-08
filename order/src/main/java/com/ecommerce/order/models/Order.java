package com.ecommerce.order.models;

import com.ecommerce.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    // this will generate a unique value fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1 user can have multiple order -> Many order tot one user
//    @ManyToOne
//    @JoinColumn(name = "user_id" , nullable = false)
//    private User user;
    private String userId;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    //    1 order can have multiple orderitems
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL , orphanRemoval = true)

    private List<OrderItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
