package com.flink.ni.demo.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flink.ni.demo.checkpoint.CheckPointOverView;
import com.flink.ni.demo.common.Constants;

import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.MapperFeature.REQUIRE_SETTERS_FOR_GETTERS;


/**
 * @ClassName GetCheckpointByHttp
 * @Description
 * @Author zihao.ni
 * @Date 2024/3/6 14:16
 * @Version 1.0
 **/
public class GetCheckpointByHttpRequest {
    public static void main(String[] args) throws JsonProcessingException {
        String host = "ni";
        int port = 8190;
        String jobId = "20d9e6761cdff315752d4ed3be44c2d2";

        GetCheckpointByHttpRequest client = new GetCheckpointByHttpRequest();
        String clusterUrl = Constants.HTTP + client.getClusterUrl(host, port);

        clusterUrl = "http://node24.test.com:8088/proxy/application_1695812289210_100531/";
        String checkpointInfo = client.getCheckpointInfo(clusterUrl, jobId);
        ObjectMapper objectMapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
                .configure(READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .configure(REQUIRE_SETTERS_FOR_GETTERS, true)
                .setTimeZone(TimeZone.getDefault());
        ;
        CheckPointOverView checkPointOverView = objectMapper.readValue(checkpointInfo, CheckPointOverView.class);
        System.out.println("1." + JSON.toJSONString(checkPointOverView));
        System.out.println("2." + checkPointOverView);

        System.out.println("3." + checkpointInfo);
    }

    public String getClusterUrl(String host, int port) {
        return host + Constants.COLON + port;
    }

    public String getCheckpointInfo(String prefixUrl, String jobId) {
        return HttpUtil.get(prefixUrl + Constants.JOBS + Constants.SLASH + jobId + Constants.CHECKPOINTS);
    }
}