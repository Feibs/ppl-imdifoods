package com.imdifoods.imdifoodswebcommerce.repository;

import com.imdifoods.imdifoodswebcommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String name);
}