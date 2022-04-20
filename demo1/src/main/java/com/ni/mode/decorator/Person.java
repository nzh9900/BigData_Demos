package com.ni.mode.decorator;

public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person() {
    }

    public void show() {
        System.out.printf("%s的衣服！%n", name);
    }
}

