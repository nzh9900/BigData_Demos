package com.ni.mode.factoryMethod;

// 用工厂方法模式设计畜牧场。
public class Client {
    public static void main(String[] args) {
        AnimalFarmFactory horseFarmFactory = new HorseFarmFactory();
        horseFarmFactory.buyNewAnimal(300D);
        AnimalFarmFactory cattleFarmFactory = new CattleFarmFactory();
        cattleFarmFactory.buyNewAnimal(400D);
    }
}