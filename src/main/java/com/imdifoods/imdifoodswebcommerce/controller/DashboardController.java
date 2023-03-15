package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.repository.UserRepository;
import com.imdifoods.imdifoodswebcommerce.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    DefaultUserService userService;

    @Autowired
    UserRepository userRepo;

    @GetMapping
    public String displayDashboard(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof DefaultOAuth2User user) {
            String email = user.getAttribute("email");
            String[] arrOfStr = email.split("@");
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(user.getAuthorities());
            if (arrOfStr[0].equals("lmdifoodstaff")) {
                updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                Authentication updatedAuth = new UsernamePasswordAuthenticationToken(user, user.getAttribute("at_hash"), updatedAuthorities);
                securityContext.setAuthentication(updatedAuth);
                model.addAttribute("userRole", "ADMIN");
            } else {
                updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication updatedAuth = new UsernamePasswordAuthenticationToken(user, user.getAttribute("at_hash"), updatedAuthorities);
                securityContext.setAuthentication(updatedAuth);
                model.addAttribute("userRole", "USER");
            }
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
        }

        return "dashboard";
    }


}
