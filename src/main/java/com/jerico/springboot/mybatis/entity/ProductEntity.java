package com.jerico.springboot.mybatis.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductEntity {
    private String id;
    private String name;
    private BigDecimal price;
}
