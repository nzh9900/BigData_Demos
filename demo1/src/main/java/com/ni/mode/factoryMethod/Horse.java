package com.ni.mode.factoryMethod;

public class Horse extends Animal {
    public Horse(double sellPrice) {
        this.sellPrice = sellPrice;
        System.out.printf("购买一匹马花费%s %n", sellPrice);
    }
}
