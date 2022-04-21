package com.ni.mode.proxy;

import lombok.Getter;

@Getter
public class AppleCompany implements Iphone {


    @Override
    public void sellPhone(Double sellPrice) {
        System.out.printf("苹果公司售出了一台Iphone，售价%s", sellPrice);
    }

}
