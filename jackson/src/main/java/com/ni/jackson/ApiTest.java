package com.ni.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static void main(String[] args) throws IOException {
        ApiTest apiTest = new ApiTest();
        //apiTest.readValue();
        //readValueInAnotherWay();
        readValueFromFile("jackson/src/main/resources/json.txt");
    }


    public static void readValueInAnotherWay() throws JsonProcessingException {
        ObjectMapper objectMapper1 = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                .build();

        User user = objectMapper1.readValue(USER01, User.class);
        System.out.println(user);
    }

    public static void readValueFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper1 = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                .build();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        while (line != null) {
            User user = objectMapper1.readValue(line, User.class);
            System.out.println(user);
            line = reader.readLine();
        }
    }
}