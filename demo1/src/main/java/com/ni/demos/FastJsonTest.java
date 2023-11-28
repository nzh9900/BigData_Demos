package com.ni.demos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public static void main(String[] args) {
        String string = parseArray("[{\"tagId\":13,\"enumIds\":[38,39,40,41,42,43]}]").toString();
        System.out.println(string);


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