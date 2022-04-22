package com.ni.mode.factoryMethod;

public class CattleFarmFactory implements AnimalFarmFactory {
    @Override
    public Animal buyNewAnimal(double price) {
        return new Cattle(price);
    }
}
