package com.imdifoods.imdifoodswebcommerce.DTO;

import com.imdifoods.imdifoodswebcommerce.dto.UserRegisteredDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRegisteredDTOTest {

    private UserRegisteredDTO userRegisteredDTO;

    @BeforeEach
    public void setUp() {
        userRegisteredDTO = new UserRegisteredDTO();
    }

    @Test
    public void testSetName() {
        userRegisteredDTO.setName("John Doe");
        Assertions.assertEquals("John Doe", userRegisteredDTO.getName());
    }

    @Test
    public void testSetEmailId() {
        userRegisteredDTO.setEmail_id("johndoe@example.com");
        Assertions.assertEquals("johndoe@example.com", userRegisteredDTO.getEmail_id());
    }

    @Test
    public void testSetPassword() {
        userRegisteredDTO.setPassword("password123");
        Assertions.assertEquals("password123", userRegisteredDTO.getPassword());
    }

    @Test
    public void testSetRole() {
        userRegisteredDTO.setRole("admin");
        Assertions.assertEquals("admin", userRegisteredDTO.getRole());
    }
}
