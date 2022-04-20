package com.ni.mode.decorator.starBucks;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Espresso extends CoffeeOrder {
    Order order;

    public Espresso(Order order) {
        this.order = order;
    }


    @Override
    public void show() {
        System.out.print("意大利浓咖啡");
        order.show();
    }
}
