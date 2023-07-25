package com.ni.mybatisdemo.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName Order
 * @Author zihao.ni
 * @Date 2023/7/25 08:38
 * @Version 1.0
 **/
@Data
public class Order {
    private long id;
    private long userId;
    private String goodsName;
    private BigDecimal price;
    private int quantity;
}