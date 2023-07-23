package com.ni.mybatisdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Man
 * @Description
 * @Author zihao.ni
 * @Date 2023/7/21 07:43
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class Man {
    private Long id;
    private String name;
    private int age;
    private String telephone;
}