package com.project.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.inventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
