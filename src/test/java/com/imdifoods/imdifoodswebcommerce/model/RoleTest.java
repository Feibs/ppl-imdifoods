package com.imdifoods.imdifoodswebcommerce.model;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class RoleTest {

    @Test
    public void roleBuilderTest() {
        Role role = Role.builder()
                .role("ADMIN")
                .build();
    }


}

