package com.ni.mybatisdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName Order
 * @Author zihao.ni
 * @Date 2023/7/25 08:38
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private long id;
    private String goodsName;
    private BigDecimal price;
    private int quantity;
}