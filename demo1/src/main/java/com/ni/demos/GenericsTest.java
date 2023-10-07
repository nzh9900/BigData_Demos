package com.ni.demos;

/**
 * @ClassName GenericsTest
 * @Description
 * @Author zihao.ni
 * @Date 2023/10/7 17:39
 * @Version 1.0
 **/
public class GenericsTest {
    public static void main(String[] args) {
        Car<String> stringCar = new Car<>("HJK");
        System.out.println(stringCar.getGas());

        Car<Integer> intCar = Car.build(213);
        System.out.println(intCar.getGas());
    }
}

class Car<GAS> {
    private GAS gas;

    public Car(GAS gas) {
        this.gas = gas;
    }

    public GAS getGas() {
        return gas;
    }

    public void setGas(GAS gas) {
        this.gas = gas;
    }

    public static <GAS> Car<GAS> build(GAS gas) {
        return new Car<>(gas);
    }
}

class Gas {

}

class Diesel extends Gas {

}

class Electricity extends Gas {

}