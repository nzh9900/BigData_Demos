package com.ni.mode.proxy;

public class ProxySeller implements Iphone {
    Iphone iphone;

    public ProxySeller(Iphone iphone) {
        this.iphone = iphone;
    }

    @Override
    public void sellPhone(Double sellPrice) {
        iphone.sellPhone(100D);
        System.out.printf("中间商赚取%s %n", 100D);
    }
}
