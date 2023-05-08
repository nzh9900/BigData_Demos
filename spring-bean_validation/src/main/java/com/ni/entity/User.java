package com.ni.entity;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @ClassName User
 * @Author zihao.ni
 * @Date 2023/4/28 09:59
 * @Version 1.0
 **/
@Data
public class User {
    @NotEmpty(message = "用户名称不能为空")
    private String name;
    @Min(value = 0, message = "年龄必须大于等于0")
    private Integer age;
}