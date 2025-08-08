package com.ecommerce.user.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


// So the Address is associated with user
@Data
@NoArgsConstructor
@Entity
@Table(name = "addresses_table")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
