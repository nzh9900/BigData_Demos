package com.ni.mode.strategy.second;

import com.ni.mode.strategy.first.CashMinusWithSimpleFactory;
import com.ni.mode.strategy.first.CashOriginalWithSimpleFactory;
import com.ni.mode.strategy.first.CashRebateWithSimpleFactory;
import com.ni.mode.strategy.first.CashSuperWithSimpleFactory;

public class CashContext {
    // client
    public static void main(String[] args) {
        CashContext cs = new CashContext("打8折");
        System.out.println(cs.getResult(200));
    }

    CashSuperWithSimpleFactory cs;

    public CashContext(String mode) {
        switch (mode) {
            case "原价":
                cs = new CashOriginalWithSimpleFactory();
                break;
            case "打8折":
                cs = new CashRebateWithSimpleFactory("0.8");
                break;
            case "满300减20":
                cs = new CashMinusWithSimpleFactory("300", "20");
                break;
            default:
                throw new IllegalArgumentException("模式错误");
        }
    }

    public double getResult(double money) {
        return cs.getCash(money);
    }
}
