package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.dto.UserRegisteredDTO;
import com.imdifoods.imdifoodswebcommerce.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;



public interface DefaultUserService extends UserDetailsService{

    User save(UserRegisteredDTO userRegisteredDTO);


}