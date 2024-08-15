package com.ni.flink.api;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.ni.flink.api.exception.IllegalResponseException;
import com.ni.flink.api.pojo.*;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @ClassName FlinkApi
 * @Description 通过flink restful api获取flink运行状态
 * @Author zihao.ni
 * @Date 2024/3/6 17:16
 * @Version 1.0
 **/
public class FlinkApi {
    private static final String SLASH = "/";
    private static final String QUESTION_MARK = "?";
    private static final String JOBS = "/jobs";
    private static final String CHECKPOINTS = "/checkpoints";
    private static final String VERTICES = "/vertices";
    private static final String METRICS = "/metrics";
    private static final String JOB_MANAGER_CONFIG = "/jobmanager/config";
    private static final String OVERVIEW = "/overview";


    private static final String JOB_MANAGER_MEMORY = "jobmanager.memory.process.size";
    private static final String TASK_MANAGER_MEMORY = "taskmanager.memory.process.size";


    private final String baseUrl;
    private Hashtable<String, String> jobManagerConfig;
    private FlinkOverview flinkOverview;

    public FlinkApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 获取checkpoint信息
     *
     * @param jobId
     * @return checkpoint信息
     */
    public String getCheckpointInfo(String jobId) {
        String response = HttpUtil.get(baseUrl + JOBS + SLASH + jobId + CHECKPOINTS);
        return response;
    }

    public String stopJob(String jobId) {
        return "";
    }

    public FlinkJobInfo getJobDetail(String jobId) throws IllegalResponseException {
        String response = HttpUtil.get(baseUrl + JOBS + SLASH + jobId);
        validateJson(response);
        return JSON.parseObject(response, FlinkJobInfo.class);
    }

    public List<FlinkMetric> getMetricsByJobId(String jobId) {
        FlinkJobInfo jobDetail = this.getJobDetail(jobId);
        List<String> vertexIds = jobDetail.getVertexIds();
        Optional<List<FlinkMetric>> reduce = vertexIds.stream()
                .map(vertexId -> {
                    return this.getMetricsOfIdp(jobId, vertexId);
                })
                .reduce((pre, next) -> {
                    pre.addAll(next);
                    return pre;
                });

        return reduce.orElse(Collections.emptyList());
    }

    /**
     * example: /jobs/:jobid/vertices/:vertexid/metrics
     * example: http://node24.test.com:8088/proxy/application_1695812289210_111201/jobs/438e9e48b75de22034d09aec519afaf8/vertices/cbc357ccb763df2852fee8c4fc7d55f2/metrics
     *
     * @param jobId
     * @param vertexId
     * @return
     */
    public List<FlinkMetric> getMetricsByVertexId(String jobId, String vertexId) throws IllegalResponseException {
        String response = HttpUtil.get(baseUrl + JOBS + SLASH + jobId + VERTICES + SLASH + vertexId + METRICS);
        validateJson(response);
        return JSON.parseArray(response, FlinkMetric.class).stream()
                .peek(flinkMetric -> flinkMetric.setVertexId(vertexId))
                .collect(Collectors.toList());
    }

    public List<FlinkMetric> getMetricsOfIdp(String jobId, String vertexId) {
        List<FlinkMetric> metricList = this.getMetricsByVertexId(jobId, vertexId);
        return metricList.stream()
                .filter(FlinkMetric::containsIdpMetricGroup)
                .collect(Collectors.toList());
    }

    /**
     * http://node24.test.com:8088/proxy/application_1695812289210_111316/jobs/de71ae2a2fc90c3c9931cb5758f3b638/vertices/cbc357ccb763df2852fee8c4fc7d55f2/metrics?get=0.Calc(select=[CAST(metric_result_table((metric_source_table(num)_*_2)))_AS_num]).idp.result_table_counter,0.Calc(select=[CAST(metric_result_table((metric_source_table(num)_*_2)))_AS_num]).idp.result_table_meter
     *
     * @param jobId
     * @param vertexId
     * @param metricName
     * @return
     */
    public List<FlinkMetric> getMetric(String jobId, String vertexId, String... metricName) throws IllegalResponseException {
        String requestResponse = HttpUtil.get(baseUrl + JOBS + SLASH + jobId + VERTICES + SLASH + vertexId + METRICS + SLASH + QUESTION_MARK + "get=" + String.join(",", metricName));
        validateJson(requestResponse);
        List<MetricResponse> metricResponses = JSON.parseArray(requestResponse, MetricResponse.class);
        return metricResponses.stream().map(response -> {
            return new FlinkMetric(jobId, response.getId(), vertexId, response.getValue());
        }).collect(Collectors.toList());
    }

    /**
     * 获取JobManager的配置
     * url example for yarn:   http://node24.test.com:8088/proxy/application_1723101616384_0734/jobmanager/config
     *
     * @return
     */
    public Hashtable<String, String> getJobManagerConfig() throws IllegalResponseException {
        if (jobManagerConfig == null) {
            jobManagerConfig = new Hashtable<String, String>();
            String response = HttpUtil.get(baseUrl + JOB_MANAGER_CONFIG);
            validateJson(response);
            JSON.parseArray(response, JSONObject.class)
                    .forEach(config -> {
                        jobManagerConfig.put(
                                config.getString("key"),
                                config.getString("value"));
                    });
        }

        return jobManagerConfig;
    }

    /**
     * 获取overview
     * url example for yarn:   http://node24.test.com:8088/proxy/application_1723101616384_0734/overview
     */
    public FlinkOverview getOverview() throws IllegalResponseException {
        if (flinkOverview == null) {
            flinkOverview = new FlinkOverview();
            String response = HttpUtil.get(baseUrl + OVERVIEW);
            validateJson(response);
            flinkOverview = JSON.parseObject(response, FlinkOverview.class);
        }

        return flinkOverview;
    }

    public MemorySize getJobManagerMemory() {
        return MemorySize.parse((String) getJobManagerConfig().get(JOB_MANAGER_MEMORY));
    }

    public MemorySize getTaskManagerTotalMemory() {
        // taskManager的内存配置 X taskManager数量
        MemorySize memorySize = MemorySize.parse(getJobManagerConfig().get(TASK_MANAGER_MEMORY));
        Integer taskManagersNum = getOverview().getTaskManagers();
        return memorySize.multiply(taskManagersNum);
    }

    private void validateJson(String response) throws IllegalResponseException {
        if (!JSONValidator.from(response).validate()) {
            throw new IllegalResponseException(response);
        }
    }
}