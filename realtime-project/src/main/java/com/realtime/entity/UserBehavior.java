package com.realtime.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName UserBehavior
 * @Description 用户行为
 * @Author zihao.ni
 * @Date 2023/4/10 17:26
 * @Version 1.0
 **/
@Data
@Builder
public class UserBehavior {
    private Long userId;
    private Long itemId;
    private Integer categoryId;
    private String behavior;
    private Long timestamp;
}