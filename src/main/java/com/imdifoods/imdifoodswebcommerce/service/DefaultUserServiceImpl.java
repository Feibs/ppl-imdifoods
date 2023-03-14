package com.imdifoods.imdifoodswebcommerce.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.imdifoods.imdifoodswebcommerce.dto.UserRegisteredDTO;
import com.imdifoods.imdifoodswebcommerce.model.Role;
import com.imdifoods.imdifoodswebcommerce.model.User;
import com.imdifoods.imdifoodswebcommerce.repository.RoleRepository;
import com.imdifoods.imdifoodswebcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class DefaultUserServiceImpl implements DefaultUserService{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

    @Override
    public User save(UserRegisteredDTO userRegisteredDTO) {
        Role role = roleRepo.findByRole(userRegisteredDTO.getRole());
        if (role == null){
            role = Role.builder()
                    .role(userRegisteredDTO.getRole()).build();
            roleRepo.save(role);
        }
        User user = new User();
        user.setEmail(userRegisteredDTO.getEmail_id());
        user.setName(userRegisteredDTO.getName());
        user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
        user.addRole(role);
        userRepo.save(user);
        return user;
    }
}
