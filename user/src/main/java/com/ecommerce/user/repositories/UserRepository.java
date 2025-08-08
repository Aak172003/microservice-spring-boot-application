package com.ecommerce.user.repositories;

import com.ecommerce.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//  JpaRepository has better performance as compared to CrudRepository,
//  also it provides some jpa specific methods like save and flash
//  This JpaRepository allows us batch operations
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
