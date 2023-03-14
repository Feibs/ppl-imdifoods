package com.imdifoods.imdifoodswebcommerce.config;

import com.imdifoods.imdifoodswebcommerce.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    @Autowired
    AuthenticationSuccessHandler successHandler;

    @Autowired
    DefaultUserService defaultUserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/", "/css/*", "/images/*").permitAll()
                .requestMatchers("/product/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/").successHandler(successHandler)
                .and().csrf().disable()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and().oauth2Login()
                .loginPage("/")
                .successHandler(successHandler);
        http.cors().disable();
        return http.build();

    }


}