package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.model.Role;
import com.imdifoods.imdifoodswebcommerce.model.User;
import com.imdifoods.imdifoodswebcommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;


public class DashboardControllerTest {

    private DashboardController controller;
    private UserRepository userRepo;
    private SecurityContext securityContext;
    private Authentication authentication;
    private DefaultOAuth2User defaultOAuth2User;
    private User user;

    @BeforeEach
    public void setup() {
        userRepo = mock(UserRepository.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        defaultOAuth2User = mock(DefaultOAuth2User.class);
        user = new User();
        Role role = Role.builder().role("USER").build();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.addRole(role);

        controller = new DashboardController();
        controller.userRepo = userRepo;

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void testDisplayDashboardForAdminUser() {
        when(authentication.getPrincipal()).thenReturn(defaultOAuth2User);
        when(defaultOAuth2User.getAttribute("email")).thenReturn("lmdifoodstaff@example.com");
        when(defaultOAuth2User.getAttribute("name")).thenReturn("Test User");

        Model model = mock(Model.class);
        controller.displayDashboard(model);

        verify(model).addAttribute("userRole", "ADMIN");
        verify(model).addAttribute("userDetails", "Test User");
    }

    @Test
    public void testDisplayDashboardForRegularUser() {
        when(authentication.getPrincipal()).thenReturn(defaultOAuth2User);
        when(defaultOAuth2User.getAttribute("email")).thenReturn("test@example.com");
        when(defaultOAuth2User.getAttribute("name")).thenReturn("Test User");

        Model model = mock(Model.class);
        controller.displayDashboard(model);

        verify(model).addAttribute("userRole", "USER");
        verify(model).addAttribute("userDetails", "Test User");
    }


}
