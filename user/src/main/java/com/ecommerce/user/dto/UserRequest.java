package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private  String lastName ;
    private String email;
    private String phoneNo;
    //    This is default role is CUSTOMER ,
    //    but if i provide role from postman it will assign as i pass ADMIN so it will assign
//    private UserRole role ;
    private AddressDTO address;
}

