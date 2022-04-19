package com.ni.mode.strategy;

public class CashMinusWithSimpleFactory implements CashSuperWithSimpleFactory {
    private double moneyCondition;
    private double moneyReturn;

    public CashMinusWithSimpleFactory(String moneyCondition, String moneyReturn) {
        this.moneyCondition = Double.parseDouble(moneyCondition);
        this.moneyReturn = Double.parseDouble(moneyReturn);
    }

    @Override
    public double getCash(double money) {
        Double v = money / moneyCondition;
        int i = v.intValue();
        return money - i * moneyReturn;
    }
}
