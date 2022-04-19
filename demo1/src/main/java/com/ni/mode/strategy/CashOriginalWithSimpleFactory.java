package com.ni.mode.strategy;

public class CashOriginalWithSimpleFactory implements CashSuperWithSimpleFactory {
    @Override
    public double getCash(double money) {
        return money;
    }
}
