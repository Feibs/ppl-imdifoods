package com.imdifoods.imdifoodswebcommerce.repository;

import com.imdifoods.imdifoodswebcommerce.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
