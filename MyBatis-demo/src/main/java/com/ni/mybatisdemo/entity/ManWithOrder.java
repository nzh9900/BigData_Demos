package com.ni.mybatisdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ManWithOrder
 * @Author zihao.ni
 * @Date 2023/7/25 08:40
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ManWithOrder extends Man {
    private List<Order> orders;
}