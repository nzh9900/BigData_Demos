package com.ni.mode.decorator.starBucks;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Soy extends CoffeeOrder {
    Order order;

    public Soy(Order order) {
        this.order = order;
    }

    @Override
    public void show() {
        System.out.print("加豆浆");
        order.show();
    }
}
