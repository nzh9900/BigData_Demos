package com.ni.demos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName Demo01
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/9/22 14:08
 * @Version 1.0
 **/
public class Demo01 {
    public static void main(String[] args) {
        User abc = new User("ABC", 11);
        User cba = new User("CBA", 12);
        ArrayList<User> users = new ArrayList<>();
        users.add(abc);
        users.add(cba);
        Map<String, Integer> collect =
                users.stream().collect(Collectors.toMap(User::getName, User::getAge));
        System.out.println(collect);
    }

    @Data
    @AllArgsConstructor
    static
    class User {
        private String name;
        private Integer age;
    }
}