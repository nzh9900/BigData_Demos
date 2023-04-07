package com.ni.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName ApiTest
 * @Description jackson包测试
 * @Author zihao.ni
 * @Date 2023/3/24 18:01
 * @Version 1.0
 **/
public class ApiTest {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String USER01 = "{\"salary\":200.123,\"nickName\":\"nickName_1\"}";

    public void readValue() throws JsonProcessingException {
        User user = new User("sad", 123, 123.123D, "sada");
        System.out.println(user);
        user = objectMapper.readValue(USER01, User.class);
        System.out.println(user);
    }

    public static void main(String[] args) throws JsonProcessingException {
        ApiTest apiTest = new ApiTest();
        apiTest.readValue();

    }
}