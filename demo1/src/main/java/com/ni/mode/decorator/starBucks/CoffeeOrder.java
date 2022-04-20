package com.ni.mode.decorator.starBucks;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CoffeeOrder extends Order {
    Order coffee;

    public CoffeeOrder(Order order) {
        this.coffee = order;
    }

    public void component(Order coffeeOrder) {
        this.customerName = coffeeOrder.customerName;
        this.coffee = coffeeOrder;
    }

    @Override
    public void show() {
        System.out.print("咖啡订单");
        coffee.show();
    }
}
