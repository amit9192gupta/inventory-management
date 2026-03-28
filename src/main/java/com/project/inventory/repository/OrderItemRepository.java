package com.project.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.inventory.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
