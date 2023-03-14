package com.imdifoods.imdifoodswebcommerce.config;

import com.imdifoods.imdifoodswebcommerce.dto.UserRegisteredDTO;
import com.imdifoods.imdifoodswebcommerce.model.Role;
import com.imdifoods.imdifoodswebcommerce.model.User;
import com.imdifoods.imdifoodswebcommerce.repository.UserRepository;
import com.imdifoods.imdifoodswebcommerce.service.DefaultUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


import static org.mockito.Mockito.*;

@SpringBootTest
class CustomSuccessHandlerTest {

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DefaultUserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    private User user;

    private User admin;

    private UserRegisteredDTO userDTO;

    private UserRegisteredDTO adminDTO;


    @BeforeEach
    public void setUp() {
        Set<Role> roles = new HashSet<>();
        Role role = Role.builder().role("USER").build();
        roles.add(role);
        user = User.builder()
                .email("test@example.com")
                .name("test")
                .password("Dummy")
                .roles(roles)
                .build();
        Set<Role> rolesAdmin = new HashSet<>();
        Role roleAdmin = Role.builder().role("ADMIN").build();
        rolesAdmin.add(roleAdmin);
        admin = User.builder()
                .email("lmdifoodstaff@example.com")
                .name("test2")
                .password("Dummy")
                .roles(rolesAdmin)
                .build();
        userDTO = UserRegisteredDTO.builder()
                .email_id("test@example.com")
                .password("Dummy")
                .name("test")
                .role("USER")
                .build();
        adminDTO = UserRegisteredDTO.builder()
                .email_id("lmdifoodstaff@example.com")
                .password("Dummy2")
                .name("test2")
                .role("ADMIN")
                .build();
    }

    @Test
    void onAuthenticationSuccessTest() throws ServletException, IOException {
        DefaultOAuth2User userDetails = mock(DefaultOAuth2User.class);
        when(userDetails.getAttribute("email")).thenReturn("test@example.com");
        when(userDetails.getAttribute("login")).thenReturn(null);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userService.save(userDTO)).thenReturn(user);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        customSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        verify(userRepository).findByEmail("test@example.com");
        verify(userService).save(any(UserRegisteredDTO.class));
    }

    @Test
    void onAuthenticationAdminSuccessTest() throws ServletException, IOException {
        DefaultOAuth2User userDetails = mock(DefaultOAuth2User.class);
        when(userDetails.getAttribute("email")).thenReturn("lmdifoodstaff@example.com");
        when(userDetails.getAttribute("login")).thenReturn(null);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userService.save(adminDTO)).thenReturn(admin);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        customSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        verify(userRepository).findByEmail("lmdifoodstaff@example.com");
        verify(userService).save(any(UserRegisteredDTO.class));
    }
}

