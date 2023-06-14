package com.ni.demos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName LambdaDebug
 * @Description
 * @Author zihao.ni
 * @Date 2023/6/13 17:33
 * @Version 1.0
 **/
public class LambdaDebug {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, "1A"));
        users.add(new User(2, "2B"));
        users.add(new User(3, "3C"));

        for (int i = 10; i < 20; i++) {
            users.add(new User(i, i + "Z"));
        }

        List<User> collect = users.stream().map(user -> {
                    user.setId(user.getId() + 1);
                    return user;
                }).filter(user -> user.getId() > 2)
                .collect(Collectors.toList());

        System.out.println(collect);

    }


}

@Data
@AllArgsConstructor
class User {
    private int id;
    private String name;
}