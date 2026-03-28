package com.project.inventory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.inventory.dto.ProductRequestDTO;
import com.project.inventory.dto.ProductResponseDTO;
import com.project.inventory.entity.Product;
import com.project.inventory.exception.ResourceNotFoundException;
import com.project.inventory.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
    	Product product=new Product();
    	product.setName(dto.getName());
    	product.setPrice(dto.getPrice());
    	product.setStockQuantity(dto.getStockQuantity());
    	
    	Product saved=productRepository.save(product);
        return mapToResponseDTO(saved);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).map(this::mapToResponseDTO);
    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
    
    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product= productRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Product dont exist with " + id ));
        return mapToResponseDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));


        productRepository.deleteById(id);
    }

	@Override
	public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
		Product existing=productRepository.findById(id)
				.orElseThrow(()->new  ResourceNotFoundException("product not found with  id "+id));
		existing.setName(dto.getName());
		existing.setPrice(dto.getPrice());
		existing.setStockQuantity(dto.getStockQuantity());
		
		Product updated=productRepository.save(existing);
		return mapToResponseDTO(updated);
	}
}