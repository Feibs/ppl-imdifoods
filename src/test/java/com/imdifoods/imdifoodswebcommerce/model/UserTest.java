package com.imdifoods.imdifoodswebcommerce.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class UserTest {

    @Test
    public void userBuilderTest() {
        Role firstRole = Role.builder()
                .role("USER").build();
        Set<Role> role = Set.of(firstRole);
        User user = User.builder()
                .name("Agus")
                .password("aku123")
                .email("agus@gmail.com")
                .roles(role)
                .build();
    }


}
