package com.ni.mode.decorator.starBucks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Order {
    String customerName;

    public Order(String customerName) {
        this.customerName = customerName;
    }

    public void show() {
        System.out.printf(",消费者是%s %n", customerName);
    }
}
