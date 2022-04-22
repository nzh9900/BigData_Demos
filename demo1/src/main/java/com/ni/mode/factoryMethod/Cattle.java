package com.ni.mode.factoryMethod;

public class Cattle extends Animal {
    public Cattle(double sellPrice) {
        this.sellPrice = sellPrice;
        System.out.printf("购买一头牛花费%s %n", sellPrice);
    }

}
