package com.project.inventory.controller;


import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.inventory.dto.ProductRequestDTO;
import com.project.inventory.dto.ProductResponseDTO;
import com.project.inventory.service.ProductService;
import com.project.inventory.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //  Create Product
    @PostMapping
    public ApiResponse<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO dto) {
         ProductResponseDTO product = productService.createProduct(dto);
         return new ApiResponse<>(true,"product created successfully",product);
    }

    //  Get All Products (Pagination)
    @GetMapping
    public ApiResponse<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
         Page<ProductResponseDTO> products = productService.getAllProducts(page, size);
         return new ApiResponse<>(true,"product fetch succesfully ",products);
    }

    //Get Product By Id
    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO  product = productService.getProductById(id);
        return new ApiResponse<>(true,"product fetch succesfully",product);
    }

    //  Update Product
    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDTO dto
    ) {
        ProductResponseDTO product = productService.updateProduct(id, dto);
        return new ApiResponse<>(true,"product updated succesfully" ,product);
        
    }

    //  Delete Product
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ApiResponse<>(true,"product deleted succesfully",null);
    }
}
