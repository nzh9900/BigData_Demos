package com.ni.flink.api.pojo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName FlinkMetric
 * @Description
 * @Author zihao.ni
 * @Date 2024/3/21 16:11
 * @Version 1.0
 **/
@Data
public class FlinkMetric {
    private String id;
    private String jid;
    private String vertexId;
    private String value;

    public FlinkMetric() {
    }

    public FlinkMetric(String jid, String id, String vertexId, String value) {
        this.id = id;
        this.vertexId = vertexId;
        this.jid = jid;
        this.value = value;
    }

    public boolean containsIdpMetricGroup() {
        return StringUtils.contains(this.id,"idp");
    }
}