package com.ni.mode.strategy.first;

public class CashRebateWithSimpleFactory implements CashSuperWithSimpleFactory {
    private String rate;

    @Override
    public double getCash(double money) {

        return Double.parseDouble(rate) * money;
    }

    public CashRebateWithSimpleFactory(String rate) {
        this.rate = rate;
    }
}
