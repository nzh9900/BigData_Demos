package com.ni.mode.decorator.starBucks;

public class Client {
    //星巴克咖啡订单项目（咖啡馆）：
    //咖啡种类/单品咖啡：Espresso(意大利浓咖啡)、ShortBlack、LongBlack(美式咖啡)、Decaf(无因咖啡)
    //
    //调料：Milk、Soy(豆浆)、Chocolate
    //
    //要求在扩展新的咖啡种类时，具有良好的扩展性、改动方便、维护方便
    //
    //使用OO 的来计算不同种类咖啡的费用: 客户可以点单品咖啡，也可以单品咖啡+调料组合。
    //————————————————
    //版权声明：本文为CSDN博主「码ming」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    //原文链接：https://blog.csdn.net/weixin_46168350/article/details/111054323

    public static void main(String[] args) {
        //意大利浓咖啡+豆浆
        Order order1 = new Order("Nick");
        CoffeeOrder coffeeOrder = new CoffeeOrder(order1);
        Espresso espresso = new Espresso(coffeeOrder);
        Soy soy = new Soy(espresso);

        //soy.show();
        soy.show();
    }


}
