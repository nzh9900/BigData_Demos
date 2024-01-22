package org.mock.order.entity;

public enum GoodsType {

    /**
     * 商品类型
     */
    // 手机
    PHONE(1, "手机"),
    // 电脑
    COMPUTER(2, "电脑"),
    // 电视
    TV(3, "电视"),
    // 电脑配件
    COMPUTER_PARTS(4, "电脑配件"),
    // 手机配件
    PHONE_PARTS(5, "手机配件"),
    // 电视配件
    TV_PARTS(6, "电视配件"),
    // 电脑软件
    COMPUTER_SOFTWARE(7, "电脑软件"),
    // 手机软件
    PHONE_SOFTWARE(8, "手机软件"),
    // 电脑游戏
    COMPUTER_GAME(9, "电脑游戏");

    private final int code;
    private final String description;

    GoodsType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "GoodsType{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }

    public static GoodsType randomGetOne() {
        return values()[(int) (Math.random() * values().length)];
    }
}
