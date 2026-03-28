package com.project.inventory.service;


import org.springframework.data.domain.Page;

import com.project.inventory.dto.ProductRequestDTO;
import com.project.inventory.dto.ProductResponseDTO;
import com.project.inventory.entity.Product;

public interface ProductService {

	ProductResponseDTO createProduct(ProductRequestDTO dto);

    Page<ProductResponseDTO> getAllProducts(int page, int size);

    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);
    void deleteProduct(Long id);
}
