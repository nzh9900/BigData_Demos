package com.ni.demos;

public interface ManInterface {
    default void print() {
        System.out.println("interface print");
    }
}
