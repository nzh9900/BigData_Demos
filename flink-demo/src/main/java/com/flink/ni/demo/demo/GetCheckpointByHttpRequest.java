package com.flink.ni.demo.demo;

import com.flink.ni.demo.common.Constants;
import com.xiaoleilu.hutool.http.HttpUtil;


/**
 * @ClassName GetCheckpointByHttp
 * @Description
 * @Author zihao.ni
 * @Date 2024/3/6 14:16
 * @Version 1.0
 **/
public class GetCheckpointByHttpRequest {
    public static void main(String[] args) {
        String host = "ni";
        int port = 8190;
        String jobId = "1d0a0dcc6f1184510b47bbda13df384d";

        GetCheckpointByHttpRequest client = new GetCheckpointByHttpRequest();
        String clusterUrl = Constants.HTTP + client.getClusterUrl(host, port);
        String checkpointInfo = client.getCheckpointInfo(clusterUrl, jobId);

        System.out.println(checkpointInfo);
    }

    public String getClusterUrl(String host, int port) {
        return host + Constants.COLON + port;
    }

    public String getCheckpointInfo(String prefixUrl, String jobId) {
        return HttpUtil.get(prefixUrl + Constants.JOBS + Constants.SLASH + jobId + Constants.CHECKPOINTS);
    }
}