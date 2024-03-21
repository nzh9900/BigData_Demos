package com.ni.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName FlinkVertex
 * @Description
 * @Author zihao.ni
 * @Date 2024/3/19 11:04
 * @Version 1.0
 **/
@Data
public class FlinkVertex {
    private String id;
    private String name;
    private int maxParallelism;
    private int parallelism;
    private String status;
    @JSONField(name = "start-time")
    private long startTime;
    @JSONField(name = "end-time")
    private long endTime;
    private int duration;
}