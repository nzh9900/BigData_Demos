package com.ni.mybatisdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Class
 * @Description 班级
 * @Author zihao.ni
 * @Date 2023/7/24 22:04
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {
    private long id;
    private String name;
    private int numberOfPeople;
}