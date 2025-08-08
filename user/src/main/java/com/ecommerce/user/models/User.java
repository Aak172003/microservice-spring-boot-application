package com.ecommerce.user.models;

import com.ecommerce.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
// This below annotation use when we are not defining constructors manually,
// We can define this both constructor manually, or we can use annotation to add constructor with arguments and constructor with no arguments
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this will generate a unique value fields
    private Long id;
    private String firstName;
    private  String lastName ;
    private String email;
    private String phoneNo;
    //    This is default role is CUSTOMER ,
//    but if i provide role from postman it will assign as i pass ADMIN so it will assign
    private UserRole role = UserRole.CUSTOMER;

//  create relationship between user and address

//    This code is a Java JPA (Java Persistence API) annotation configuration for a one-to-one relationship between two entities, typically used in frameworks like Hibernate or Spring Data JPA.

//    @OneToOne: This indicates a one-to-one relationship between two entities — in this case, between the current entity (let's say User) and Address. It means each User has exactly one Address, and each Address belongs to one User.
//    cascade = CascadeType.ALL: This means that any operations done on the parent entity (like persist, merge, remove) will also be cascaded to the Address. For example:
//          Saving a User will also save the Address.
//          Deleting a User will also delete the associated Address.

//    orphanRemoval = true: If the Address is disassociated from the User (i.e., user.setAddress(null)), then that Address will be automatically deleted from the database. It prevents "orphan" records.

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")

//    @JoinColumn: Specifies the foreign key column in the User table that links to the Address table.
//    name = "address_id": This is the column in the User table (or whatever entity this is in) that holds the foreign key reference to the Address table.
//    referencedColumnName = "id": This is the column in the Address table that is being referenced. Usually, it's the id primary key of the Address entity.

//    ❓ What are orphan records?
//    In the context of JPA/Hibernate and databases in general, orphan records are child records that are no longer referenced by their parent, but still exist in the database.
//    They’re essentially "left behind" when the relationship is broken, without being explicitly deleted.
    private Address address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


//    We can define this both constructor manually, or we can use annotation to add constructor with arguments and constructor with no arguments
//    This is default constructor -> This is mandatory
//    it requires when data get krna ho , so us case me user ka object bnane ke lie ek default constructor chaiye so this is used as a default constructor

//    public User() {
//    }
//
//    public User(Long id, String firstName, String lastName) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
}
