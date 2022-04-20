package com.ni.mode.decorator.starBucks;

public class ShortBlack extends CoffeeOrder {
    Order order;

    public ShortBlack(Order order) {
        this.order = order;
    }

    @Override
    public void show() {
        System.out.print("小杯黑咖啡");
        super.show();
    }
}
