package com.ni.mode.decorator;

public class Client {
    public static void main(String[] args) {
        Person aa = new Person("aa");
        Tshits tshits = new Tshits();
        Shoes shoes = new Shoes();
        tshits.component(aa);
        shoes.component(tshits);
        //tshits.show();
        shoes.show();
    }
}
