package org.mock.order.entity;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName Order
 * @Description
 * @Author zihao.ni
 * @Date 2024/1/8 11:32
 * @Version 1.0
 **/
public class Order {
    private Long orderId;
    private String userName;
    private GoodsType goodsType;
    private Timestamp orderTime;
    private Long orderTimeSeries;
    private BigDecimal price;
    private Integer num;
    private BigDecimal totalPrice;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Long getOrderTimeSeries() {
        return orderTimeSeries;
    }

    public void setOrderTimeSeries(Long orderTimeSeries) {
        this.orderTimeSeries = orderTimeSeries;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userName='" + userName + '\'' +
                ", goodsType=" + goodsType.getDescription() +
                ", orderTime=" + orderTime +
                ", orderTimeSeries=" + orderTimeSeries +
                ", price=" + price +
                ", num=" + num +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}