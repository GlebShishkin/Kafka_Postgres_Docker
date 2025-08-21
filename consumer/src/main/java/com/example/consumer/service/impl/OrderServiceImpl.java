package com.example.consumer.service.impl;

import com.example.consumer.domain.Status;
import com.example.consumer.service.OrderService;
import com.example.consumer.service.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.consumer.domain.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.consumer.domain.repository.OrdersRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    @Override
    public Order save(OrderDto clientDto) {

        // строим объект заказа для сохранения в бд
        Order order= Order.builder()
                .productName(clientDto.getProductName())
                .barCode(clientDto.getBarCode())
                .quantity(clientDto.getQuantity())
                .price(clientDto.getPrice())
                .amount(new BigDecimal(clientDto.getQuantity()).multiply(clientDto.getPrice()))
                .orderDate(LocalDateTime.now())
                .status(Status.APPROVED)
                .build();

        ordersRepository.save(order);

        return order;
    }
}
