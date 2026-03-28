package com.project.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.inventory.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
