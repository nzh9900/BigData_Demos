package com.ni.mode.proxy;

public class Client {
    //苹果公司通过苹果代理商来卖手机

    public static void main(String[] args) {
        AppleCompany appleCompany = new AppleCompany();
        ProxySeller proxySeller = new ProxySeller(appleCompany);
        proxySeller.sellPhone(1000D);
    }
}
