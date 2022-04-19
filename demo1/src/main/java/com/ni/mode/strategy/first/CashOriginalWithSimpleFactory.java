package com.ni.mode.strategy.first;

public class CashOriginalWithSimpleFactory implements CashSuperWithSimpleFactory {
    @Override
    public double getCash(double money) {
        return money;
    }
}
