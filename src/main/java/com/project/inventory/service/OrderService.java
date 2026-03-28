package com.project.inventory.service;

import com.project.inventory.dto.OrderRequestDTO;
import com.project.inventory.dto.OrderResponseDTO;

public interface OrderService {

	OrderResponseDTO placeOrder(OrderRequestDTO dto);
}
