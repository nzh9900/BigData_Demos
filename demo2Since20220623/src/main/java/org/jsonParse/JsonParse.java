package org.jsonParse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonParse {
    public static void main(String[] args) {
        String json = "{\"t_boolean_random\":true,\"t_char_random\":\"00f46\",\"t_char_sequence\":\"10\",\"t_varchar_random\":\"e166aa61a3\",\"t_varchar_sequence\":\"10\",\"t_string_random\":\"4b73cef9ab\",\"t_string_sequence\":\"10\",\"t_decimal_random\":847042013443862528.00,\"t_tinyint_random\":12,\"t_tinyint_sequence\":10,\"t_smallint_random\":19,\"t_smallint_sequence\":10,\"t_int_random\":27,\"t_int_sequence\":10,\"t_bigint_random\":84,\"t_bigint_sequence\":10,\"t_float_random\":15.227614D,\"t_float_sequence\":10.0,\"t_double_random\":58.54399238223535,\"t_double_sequence\":10.0,\"t_date_random\":\"2022-08-07\",\"t_time_random\":\"15:11:52.213\",\"t_timestamp_random\":\"2022-08-07T07:11:52.213\",\"t_timestamp_ltz_random\":\"2022-08-07T07:11:52.213Z\",\"t_row_random\":{\"t_boolean_random\":true,\"t_char_random\":\"7966a\",\"t_varchar_random\":\"a964ecd455\",\"t_string_random\":\"b165aabca3\",\"t_decimal_random\":193113081312774816.00,\"t_tinyint_random\":95,\"t_smallint_random\":24,\"t_int_random\":30,\"t_bigint_random\":58,\"t_float_random\":58.154152,\"t_double_random\":74.05492827396768,\"t_date_random\":\"2022-08-07\",\"t_time_random\":\"15:11:52.216\",\"t_timestamp_random\":\"2022-08-07T07:11:52.216\",\"t_timestamp_ltz_random\":\"2022-08-07T07:11:52.216Z\",\"t_row_random\":{\"t_boolean_random\":true}},\"t_array_random\":[93.87437009886479,13.500359383271837,94.48749472165038],\"t_map_random\":{\"key\":10,\"value\":20},\"t_multiset_random\":{\"a\":21},\"array_row\":[{\"aa\":990}]}";
        parseJson(json);
    }

    private static void parseJson(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject);
    }
}
