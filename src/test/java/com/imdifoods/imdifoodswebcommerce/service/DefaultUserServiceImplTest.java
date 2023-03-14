package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.dto.UserRegisteredDTO;
import com.imdifoods.imdifoodswebcommerce.model.Role;
import com.imdifoods.imdifoodswebcommerce.model.User;
import com.imdifoods.imdifoodswebcommerce.repository.RoleRepository;
import com.imdifoods.imdifoodswebcommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DefaultUserServiceImplTest {

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private RoleRepository roleRepo;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DefaultUserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadInvalidUserByUsername(){
        String email = "aku@kamu.com";
        Mockito.when(userRepo.findByEmail(email)).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("aku@kamu.com"));
    }

    @Test
    public void testLoadUserByUsername() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        Role role = Role.builder()
                .role("USER")
                .build();
        user.addRole(role);
        Mockito.when(userRepo.findByEmail(email)).thenReturn(user);
        UserDetails userDetails = userService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), email);
        assertEquals(userDetails.getPassword(), user.getPassword());
        assertEquals(userDetails.getAuthorities().size(), user.getRoles().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    public void testSave() {
        // Given
        UserRegisteredDTO userDTO = new UserRegisteredDTO();
        userDTO.setEmail_id("test@example.com");
        userDTO.setName("Test User");
        userDTO.setPassword("password");
        Role role = Role.builder()
                .role("USER").build();
        userDTO.setRole(role.getRole());
        Mockito.when(roleRepo.findByRole("USER")).thenReturn(role);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encoded_password");
        // When
        User user = userService.save(userDTO);
        Optional<Role> firstElement = user.getRoles().stream().findFirst();
        // Then
        assertNotNull(user);
        assertEquals(user.getEmail(), userDTO.getEmail_id());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(firstElement.get(), role);
        verify(userRepo, Mockito.times(1)).save(user);
    }
}

