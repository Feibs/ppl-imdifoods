package com.imdifoods.imdifoodswebcommerce.repository;


import com.imdifoods.imdifoodswebcommerce.model.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByEmail(String emailId);
}