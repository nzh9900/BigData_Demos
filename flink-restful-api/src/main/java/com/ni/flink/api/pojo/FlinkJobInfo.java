package com.ni.flink.api.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FlinkJobInfo
 * @Description
 * @Author zihao.ni
 * @Date 2024/3/19 10:58
 * @Version 1.0
 **/
@Data
public class FlinkJobInfo {
    private String jid;
    private String name;
    private boolean isStoppable;
    private String state;
    @JSONField(name = "start-time")
    private long startTime;
    @JSONField(name = "end-time")
    private long endTime;
    private long duration;
    private int maxParallelism;
    private long now;
    private List<FlinkVertex> vertices;

    public List<String> getVertexIds() {
        List<String> ids = new ArrayList<>();
        for (FlinkVertex vertex : vertices) {
            ids.add(vertex.getId());
        }
        return ids;
    }
}