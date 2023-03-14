package com.imdifoods.imdifoodswebcommerce.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisteredDTO {

    private String name;

    private String email_id;

    private String password;

    private String role;
}