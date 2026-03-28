package com.project.inventory.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.project.inventory.dto.OrderItemRequestDTO;
import com.project.inventory.dto.OrderItemResponseDTO;
import com.project.inventory.dto.OrderRequestDTO;
import com.project.inventory.dto.OrderResponseDTO;
import com.project.inventory.entity.Order;
import com.project.inventory.entity.OrderItem;
import com.project.inventory.entity.Product;
import com.project.inventory.exception.InsufficientStockException;
import com.project.inventory.exception.ResourceNotFoundException;
import com.project.inventory.repository.OrderRepository;
import com.project.inventory.repository.ProductRepository;

public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final ProductRepository productRepository;

	public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@Override
	@Transactional
	public OrderResponseDTO placeOrder(OrderRequestDTO dto) {
		Order order = new Order();
		order.setOrderDate(LocalDateTime.now());
		order.setStatus("placed");
		List<OrderItem> orderItems = new ArrayList<>();
		for (OrderItemRequestDTO orderItemDto : dto.getItems()) {
			// fetch order
			Product product = productRepository.findById(orderItemDto.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"product with  id" + orderItemDto.getProductId() + "doesn't exist"));
			// validate stock
			if (product.getStockQuantity() < orderItemDto.getQuantity()) {
				throw new InsufficientStockException("insuffiant stock for " + product.getName());
			}
			// reduce stock
			product.setStockQuantity(product.getStockQuantity() - orderItemDto.getQuantity());
			// create orderitem
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setQuantity(orderItemDto.getQuantity());
			orderItem.setOrder(order);

			orderItems.add(orderItem);
		}
		order.setItems(orderItems);
		Order save = orderRepository.save(order);

		return mapToOrderResponseDTO(save);
	}
	
	private OrderResponseDTO mapToOrderResponseDTO(Order order) {
		OrderResponseDTO dto=new OrderResponseDTO();
		dto.setOrderDate(order.getOrderDate());
		dto.setOrderId(order.getId());
		dto.setStatus(order.getStatus());
		List<OrderItemResponseDTO> savedOrder=new ArrayList<>();
		for(OrderItem orderItem:order.getItems()) {
			OrderItemResponseDTO orderItemDto=new OrderItemResponseDTO();
			orderItemDto.setProductId(orderItem.getProduct().getId());
			orderItemDto.setProductName(orderItem.getProduct().getName());
			orderItemDto.setQuantity(orderItem.getQuantity());
			savedOrder.add(orderItemDto);
		}
		dto.setItems(savedOrder);
		return dto; 
	}

}
