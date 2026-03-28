package com.project.inventory.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.inventory.dto.OrderRequestDTO;
import com.project.inventory.dto.OrderResponseDTO;
import com.project.inventory.service.OrderService;
import com.project.inventory.util.ApiResponse;

@RestController
public class OrderController {
	
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	@PostMapping
	public ApiResponse<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO dto ){
		OrderResponseDTO responseDTO = orderService.placeOrder(dto);
		return new  ApiResponse<>(true,"order places successfully",responseDTO);
	}

}
