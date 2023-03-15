package com.imdifoods.imdifoodswebcommerce.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleTest {

    @Test
    public void roleBuilderTest() {
        Role role = Role.builder()
                .role("ADMIN")
                .build();
    }


}

