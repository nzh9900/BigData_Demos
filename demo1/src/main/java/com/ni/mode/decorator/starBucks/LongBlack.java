package com.ni.mode.decorator.starBucks;

public class LongBlack extends CoffeeOrder {
    Order order;

    public LongBlack(Order order) {
        this.order = order;
    }

    @Override
    public void show() {
        System.out.print("美式咖啡");
        order.show();
    }
}
