package com.ni.mode.decorator;

public class Tshits extends Clothes{
    public Tshits(String name) {
        super(name);
    }

    public Tshits() {
    }

    @Override
    public void show() {
        System.out.print("Tshit ");
        super.show();
    }
}
