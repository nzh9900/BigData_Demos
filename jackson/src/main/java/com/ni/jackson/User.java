package com.ni.jackson;

/**
 * @ClassName User
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/3/24 18:03
 * @Version 1.0
 **/

public class User {
    private String name;
    private int age;
    private double salary;
    private String nickName;

    public User(String name, int age, double salary, String nickName) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.nickName = nickName;
    }

    public User() {
    }

    public User(double salary, String nickName) {
        this.salary = salary;
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public String getNickName() {
        return nickName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}