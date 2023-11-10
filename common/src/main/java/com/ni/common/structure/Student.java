package com.ni.common.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Student
 * @Description
 * @Author zihao.ni
 * @Date 2023/11/7 14:27
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class Student {
    private String name;
    private String className;
    private Integer age;
    private Integer score;
    private Integer type;
}