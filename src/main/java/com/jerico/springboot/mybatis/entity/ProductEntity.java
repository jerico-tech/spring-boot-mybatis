package com.jerico.springboot.mybatis.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ProductEntity {
    private String id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createTime;
    private Date lastUpdateTime;
}
