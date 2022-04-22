package com.ni.mode.factoryMethod;

public class HorseFarmFactory implements AnimalFarmFactory {
    @Override
    public Animal buyNewAnimal(double price) {
        return new Horse(price);
    }
}
