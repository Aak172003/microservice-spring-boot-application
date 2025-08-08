package com.ecommerce.user.dto;

import com.ecommerce.user.enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private  String lastName ;
    private String email;
    private String phoneNo;
    //    This is default role is CUSTOMER ,
    //    But if i provide role from postman it will assign as i pass ADMIN so it will assign
    private UserRole role ;

    private AddressDTO address;
}
