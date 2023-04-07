package com.ni.dataStreamApi.custom.documentExample.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        String jsonString = "{\"agent_send_timestamp\":1646277158217,\"other\":{\"interface_comment\":\"other\"},\"collector_recv_timestamp\":1646277158301,\"raw_message\":\"192.25.105.187 - - [03/Mar/2022:11:12:37 +0800] \\\"GET /user/c_user/data/azkaban/ODS_O32/20220303/T_ODS_O32_THISUNITSTOCK_HV.txt HTTP/1.1\\\" 200 368878640 \\\"-\\\" \\\"-\\\"\",\"ip\":\"192.25.105.187\",\"index\":\"ops-yotta-20220303\",\"source\":\"/usr/local/nginx/logs/access.log\",\"logtype\":\"other\",\"hostname\":\"bdpt-nn2.xysec.com\",\"appname\":\"sydsj\",\"domain\":\"ops\",\"sysdj\":null,\"context_id\":\"1646277158217053482\",\"tag\":[\"sydsj_nginx_access\",\"sydsj_yy\"],\"id\":\"SYalZ37RprJf1uQF2s5UL\",\"raw_message_length\":158,\"timestamp\":1646277158217,\"sydsj\":{\"response_code\":\"200\",\"src\":{\"geo\":{\"country\":\"美国\",\"province\":\"*\",\"city\":\"*\",\"isp\":\"*\",\"latitude\":39.76505,\"ip\":\"192.25.105.187\",\"longitude\":-1.40791}},\"ident\":\"-\",\"access_request\":\"/user/c_user/data/azkaban/ODS_O32/20220303/T_ODS_O32_THISUNITSTOCK_HV.txt\",\"request_method\":\"GET\",\"req_time\":\"03/Mar/2022:11:12:37 +0800\",\"http_user_agent\":{\"os\":\"other\",\"browser\":\"Java\",\"device\":\"Spider\",\"browser_v\":\"Java8.0.191\",\"os_v\":\"other\"},\"src_ip\":\"192.25.105.187\",\"protocol\":\"HTTP/1.1\",\"uri_path\":\"/user/c_user/data/azkaban/ODS_O32/20220303/T_ODS_O32_THISUNITSTOCK_HV.txt\",\"uri_query\":\"\",\"bytes_out\":\"368878640\",\"http_referer\":\"-\",\"user\":\"-\"}}";
        Object jsonObject = JSON.parse(jsonString);
        LinkedHashMap<String, String> jsonColumns = getJsonColumns((JSONObject) jsonObject);
        for (Map.Entry<String, String> entry : jsonColumns.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " " + value + ",");
        }
    }

    public static String getColumns(Object info, String format) {
        String resultColumns = "";
        switch (format.toLowerCase()) {
            case "json":
                LinkedHashMap<String, String> columnTypes = getJsonColumns((JSONObject) info);
                //resultColumns = parseColumnTypes(columnTypes, true);
                return resultColumns;
        }
        return resultColumns;
    }

    private static LinkedHashMap<String, String> getJsonColumns(JSONObject json) {
        LinkedHashMap<String, String> columnTypes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof java.time.LocalDateTime || value instanceof java.sql.Timestamp || value instanceof java.time.OffsetDateTime) {
                columnTypes.put(key, "TIMESTAMP");
            } else if (value instanceof String) {
                columnTypes.put(key, "STRING");
            } else if (value instanceof Integer) {
                columnTypes.put(key, "INT");
            } else if (value instanceof Long) {
                columnTypes.put(key, "BIGINT");
            } else if (value instanceof BigDecimal) {
                // decimal长度统一为38，小数点后位数与样例数据相同
                String decimalString = ((BigDecimal) value).toPlainString();
                int sizeAfterPoint = (decimalString).split("\\.")[1].length();
                int size = decimalString.length() - 1;
                columnTypes.put(key, String.format("DECIMAL(%s, %s)", size, sizeAfterPoint));
            } else if (value instanceof JSONObject) {
                LinkedHashMap<String, String> row = getJsonColumns((JSONObject) value);
                String result = parseColumnTypes(row, false);
                columnTypes.put(key, String.format("ROW(%s)", result));
            } else if (value instanceof JSONArray) {
                Object obj = ((JSONArray) value).get(0);
                if (obj instanceof JSONObject) {
                    LinkedHashMap<String, String> row = getJsonColumns((JSONObject) obj);
                    String result = parseColumnTypes(row, false);
                    columnTypes.put(key, String.format("ARRAY<ROW(%s)>", result));
                } else {
                    if (obj instanceof String) {
                        columnTypes.put(key, "ARRAY<String>");
                    } else if (obj instanceof Integer) {
                        columnTypes.put(key, "ARRAY<Int>");
                    } else if (obj instanceof Long) {
                        columnTypes.put(key, "ARRAY<BIGINT>");
                    } else if (obj instanceof BigDecimal) {
                        // decimal长度统一为38，小数点后位数与样例数据相同
                        int sizeAfterDecimalPoint = ((String) obj).split("\\.")[1].length();
                        columnTypes.put(key, String.format("ARRAY<DECIMAL(38, %s)>", sizeAfterDecimalPoint));
                    }
                }
            }
        }

        return columnTypes;
    }

    private static String parseColumnTypes(LinkedHashMap<String, String> columnTypes, Boolean isNewline) {
        StringBuilder resultStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : columnTypes.entrySet()) {
            resultStringBuilder.append("`").append(entry.getKey()).append("`")
                    .append("\t").append(entry.getValue()).append(",");
            if (isNewline) {
                resultStringBuilder.append("\n");
            }
        }
        return resultStringBuilder.deleteCharAt(resultStringBuilder.lastIndexOf(",")).toString();
    }
}
