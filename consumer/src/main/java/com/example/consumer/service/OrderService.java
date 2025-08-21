package com.example.consumer.service;

import com.example.consumer.service.dto.OrderDto;
import com.example.consumer.domain.Order;

public interface OrderService {
    Order save(OrderDto clientDto);
}
