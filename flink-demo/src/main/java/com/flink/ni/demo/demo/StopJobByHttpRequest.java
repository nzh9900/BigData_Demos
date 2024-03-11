package com.flink.ni.demo.demo;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName StopJobByHttpRequest
 * @Description
 * @Author zihao.ni
 * @Date 2024/3/11 10:19
 * @Version 1.0
 **/
public class StopJobByHttpRequest {
    public static void main(String[] args) {
        //stopStandaloneFlinkJob();
        stopYarnClusterFlinkJob();
    }

    public static void stopStandaloneFlinkJob() {
        // stop flink job by http request
        String url = "10.24.68.10:8990";
        String jid = "203ad271fd2a0ed9133c1b112538a5d8";
        String requestUrl = "http://" + url + "/jobs/" + jid + "/stop";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("drain", true);
        requestBody.put("targetDirectory", "/tmp/savepoint_1");

        String post = HttpUtil.post(requestUrl, JSON.toJSONString(requestBody), 1000);
        System.out.println(post);
    }

    public static void stopYarnClusterFlinkJob() {
        // stop flink job by http request
        String url = "node24.test.com:8088/proxy/application_1695812289210_28547/#";
        String jid = "ce245e34de529a181d87839353c5cb0f";
        String requestUrl = "http://" + url + "/jobs/" + jid + "/stop";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("drain", true);
        requestBody.put("targetDirectory", "/tmp/savepoint_1");

        String post = HttpUtil.post(requestUrl, JSON.toJSONString(requestBody), 1000);
        System.out.println(post);
    }
}