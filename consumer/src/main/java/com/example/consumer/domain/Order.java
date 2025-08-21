package com.example.consumer.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/* !!! данные типы (javax - устаревшая) некорректно работают с ENUM -> нужно использовать jakarta
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
*/
@Entity
@Table(name = "orders")
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="product_name", nullable = false)
    private String productName;

    @Column(name="bar_code", nullable = false)
    private String barCode;

    @Column(name="quantity", nullable = false)
    private int quantity;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="order_date", nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(name="status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
