package com.ni.dataStreamApi.connectors;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.planner.plan.nodes.physical.stream.StreamExecIncrementalGroupAggregate;
import org.apache.flink.util.ArrayUtils;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class FlinkStreamKafaka {
    private static final String TOPICS = "topics";
    private static final String ROWTIME_FIELD = "rowtime.field";
    private static final String WATERMARK_VAL = "watermark";
    private static final String SCHEMA = "schema";
    private static final String SOURCE_FORMAT = "format.type";
    private static final String GROUP_ID = "group.id";
    private static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    private static final String OFFSET_RESET = "offset.reset";
    private static final String insertQuery = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";
    private Map<String, String> types;


    public static void main(String[] args) throws Exception {
        HashMap<String, String> types = new HashMap<String, String>();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "hadoop102:9092");
        props.setProperty("group.id", "test");
        //Object schemaInfo = JSONObject.parse(, Feature.OrderedField);
        //getJsonType(types, (JSONObject) schemaInfo);

        //DataStreamSource<String> source = env.addSource(new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), props));
        ArrayList<String> strings = new ArrayList<>();
        strings.add("{\"table\":\"OGGTEST.STUDENT\",\"op_type\":\"I\",\"op_ts\":\"2021-09-10 13:59:56.998153\",\"current_ts\":\"2021-09-10T14:00:03.960000\",\"pos\":\"00000000000000003776\",\"primary_keys\":[\"ID\"],\"after\":{\"ID\":1,\"NAME\":\"BOB\",\"AGE\":24,\"ADDRESS\":\"Shanghai\",\"CREATE_TIME\":\"2021-09-10 13:59:57.000000000\",\"UPDATE_TIME\":null,\"TEST\":null}}");
        DataStreamSource<String> source = env.fromCollection(strings);
        SingleOutputStreamOperator<String> resultStream = source.map(new MapFunction<String, String>() {
            @Override
            public String map(String line) throws Exception {
                Ogg oggBean = JSONObject.parseObject(line, Ogg.class, Feature.OrderedField);
                String query = null;
                switch (oggBean.getOp_type()) {
                    case "I":
                        String table = oggBean.getTable();
                        HashMap<String, Object> after = oggBean.getAfter();
                        StringBuilder columns = new StringBuilder();
                        StringBuilder values = new StringBuilder();

                        for (Map.Entry<String, Object> entry : after.entrySet()) {
                            columns.append(entry.getKey().toLowerCase());
                            String type = types.get(entry.getKey());
                            Object value = entry.getValue();
                            if (value == null) {
                                values.append("NULL,");
                            } else {
                                switch (type) {
                                    case "String":
                                        values.append("\"").append(value).append("\"");
                                        break;
                                    case "Int":
                                    case "Long":
                                    case "BigDecimal":
                                        values.append(value);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        query = String.format(
                                INSERT_QUERY,
                                table,
                                String.join(",", columns),
                                values);
                        break;
                    case "U":
                        break;
                    case "D":
                        break;
                    default:
                        break;
                }
                return query;
            }
        });
        resultStream.print();


        env.execute();
    }

    private static void getJsonType(Map<String, String> types, JSONObject json) {

        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                types.put(key, "String");
            } else if (value instanceof Integer) {
                types.put(key, "Int");
            } else if (value instanceof Long) {
                types.put(key, "Long");
            } else if (value instanceof BigDecimal) {
                types.put(key, "BigDecimal");
            }
        }
    }
}
