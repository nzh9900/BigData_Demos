package com.ni.mode.strategy.first;

public class CashFactoryWithSimpleFactory {

    public static void main(String[] args) {
        CashSuperWithSimpleFactory cs = getCs("满300减20");
        System.out.println(cs.getCash(200));
    }

    public static CashSuperWithSimpleFactory getCs(String mode) {
        CashSuperWithSimpleFactory cs = null;
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
        return cs;
    }
}
