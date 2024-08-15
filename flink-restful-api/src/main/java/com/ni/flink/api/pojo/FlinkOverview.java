package com.ni.flink.api.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @since 2024/8/15 17:23
 **/
@Data
public class FlinkOverview {
    @JSONField(name = "taskmanagers")
    private Integer taskManagers;
    @JSONField(name = "slots-total")
    private Integer slotsTotal;
    @JSONField(name = "slots-available")
    private Integer slotsAvailable;
    @JSONField(name = "jobs-running")
    private Integer jobsRunning;
    @JSONField(name = "jobs-finished")
    private Integer jobsFinished;
    @JSONField(name = "jobs-cancelled")
    private Integer jobsCancelled;
    @JSONField(name = "jobs-failed")
    private Integer jobsFailed;
    @JSONField(name = "flink-version")
    private String flinkVersion;
    @JSONField(name = "flink-commit")
    private String flinkCommit;
}