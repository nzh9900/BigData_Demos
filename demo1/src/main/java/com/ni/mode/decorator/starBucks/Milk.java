package com.ni.mode.decorator.starBucks;

public class Milk extends CoffeeOrder {
    Order order;

    public Milk(Order order) {
        this.order = order;
    }

    @Override
    public void show() {
        System.out.print("加牛奶");
        super.show();
    }
}
