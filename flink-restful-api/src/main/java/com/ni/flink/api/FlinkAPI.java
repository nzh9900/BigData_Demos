package com.ni.flink.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.http.HttpUtil;

public class FlinkAPI {
    private final static String HTTP = "http://";
    private final static String SLASH = "/";
    private final static String JOBS = "jobs/";


    private final String address;

    public FlinkAPI(String address) {
        this.address = address;
    }

    public JSONObject getJobInfo(String jobId) {
        return get(JOBS + jobId);
    }

    public String getJobState(String jobId) throws Exception {
        JSONObject jobInfo = getJobInfo(jobId);
        if (jobInfo.containsKey("state")) {
            return jobInfo.getString("state");
        } else {
            throw new Exception("get job state error:" + jobInfo.getString("errors"));
        }
    }


    public JSONObject get(String route) {
        String result = getResult(route);
        return JSON.parseObject(result);
    }

    public String getResult(String route) {
        return HttpUtil.get(HTTP + address + SLASH + route);
    }

}