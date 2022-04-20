package com.ni.mode.decorator;

public class Shoes extends Clothes {
    public Shoes(String name) {
        super(name);
    }

    public Shoes() {
    }

    @Override
    public void show() {
        System.out.print("shoes ");
        super.show();
    }
}
