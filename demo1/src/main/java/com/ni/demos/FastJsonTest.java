package com.ni.demos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ni.entity.FlinkJobInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName FastJsonTest
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/6/25 18:12
 * @Version 1.0
 **/
public class FastJsonTest {
    private static final String FLINK_JOB_INFO =
            "{\"jid\":\"4e2279c7097e86d4331db82bbe692743\",\"name\":\"x2_1710748757562\",\"isStoppable\":false,\"state\":\"RUNNING\",\"start-time\":1710748784134,\"end-time\":-1,\"duration\":68235516,\"maxParallelism\":128,\"now\":1710817019650,\"timestamps\":{\"FAILED\":0,\"FAILING\":0,\"FINISHED\":0,\"CREATED\":1710748784251,\"RUNNING\":1710748785027,\"CANCELED\":0,\"SUSPENDED\":0,\"RECONCILING\":0,\"RESTARTING\":0,\"INITIALIZING\":1710748784134,\"CANCELLING\":0},\"vertices\":[{\"id\":\"cbc357ccb763df2852fee8c4fc7d55f2\",\"name\":\"Source: TableSourceScan(table=[[default_catalog, default_database, source_table_for_metric]], fields=[num]) -> Calc(select=[CAST(metric_result_table((metric_source_table(num) * 2))) AS num]) -> Sink: Sink(table=[default_catalog.default_database.result_table], fields=[num])\",\"maxParallelism\":128,\"parallelism\":5,\"status\":\"RUNNING\",\"start-time\":1710748794878,\"end-time\":-1,\"duration\":68224772,\"tasks\":{\"CANCELED\":0,\"RECONCILING\":0,\"DEPLOYING\":0,\"INITIALIZING\":0,\"CANCELING\":0,\"CREATED\":0,\"RUNNING\":5,\"FINISHED\":0,\"SCHEDULED\":0,\"FAILED\":0},\"metrics\":{\"read-bytes\":0,\"read-bytes-complete\":true,\"write-bytes\":0,\"write-bytes-complete\":true,\"read-records\":0,\"read-records-complete\":true,\"write-records\":0,\"write-records-complete\":true}}],\"status-counts\":{\"CANCELED\":0,\"RECONCILING\":0,\"DEPLOYING\":0,\"INITIALIZING\":0,\"CANCELING\":0,\"CREATED\":0,\"RUNNING\":1,\"FINISHED\":0,\"SCHEDULED\":0,\"FAILED\":0},\"plan\":{\"jid\":\"4e2279c7097e86d4331db82bbe692743\",\"name\":\"x2_1710748757562\",\"nodes\":[{\"id\":\"cbc357ccb763df2852fee8c4fc7d55f2\",\"parallelism\":5,\"operator\":\"\",\"operator_strategy\":\"\",\"description\":\"Source: TableSourceScan(table=[[default_catalog, default_database, source_table_for_metric]], fields=[num]) -&gt; Calc(select=[CAST(metric_result_table((metric_source_table(num) * 2))) AS num]) -&gt; Sink: Sink(table=[default_catalog.default_database.result_table], fields=[num])\",\"optimizer_properties\":{}}]}}                                                                         ";

    public static void main(String[] args) {
        //String string = parseArray("[{\"tagId\":13,\"enumIds\":[38,39,40,41,42,43]}]").toString();
        //System.out.println(string);

        FlinkJobInfo flinkJobInfo = JSON.parseObject(FLINK_JOB_INFO, FlinkJobInfo.class);
        System.out.println(flinkJobInfo);

    }


    public static JSONArray parseArray(String arrayString) {

        return JSON.parseArray(arrayString);
    }

    /**
     * 使用JSON.parseObject解析JSON ARRAY 无法解析成功
     *
     * @param arrayString
     * @return
     */
    public static JSONObject parseObjectFromArrayString(String arrayString) {
        return JSON.parseObject(arrayString);
    }
}

@Data
@AllArgsConstructor
class Student {
    private String name;
    private Integer age;
    private Student childStudent;
}