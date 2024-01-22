package org.mock.order;

import org.mock.order.entity.GoodsType;
import org.mock.order.entity.Order;
import org.mock.order.entity.UserName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Mock
 * @Description
 * @Author zihao.ni
 * @Date 2024/1/8 11:38
 * @Version 1.0
 **/
public class MockData {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String[] args) {
        MockData mockData = new MockData();
        List<Order> orders = new ArrayList<>();
        // 2024-01-01 00:00:00
        long startTimeStamp = 1704038400000L;
        for (int i = 0; i < 30; i++) {
            long currentTimeStamp = startTimeStamp + i * 60000L;
            orders.addAll(mockData.batchMock(i, currentTimeStamp));
        }
        orders.forEach(System.out::println);
    }

    public List<Order> batchMock(int count, long startTimestamp) {
        List<Order> orders = new ArrayList<>();

        for (int i = 1; i < count + 1; i++) {
            long orderTimeSeries = startTimestamp + (long) (Math.random() * 60000);
            Long orderId = generateOrderId(orderTimeSeries, i);
            Timestamp orderTime = new Timestamp(orderTimeSeries);
            int num = (int) (10 * Math.random());
            BigDecimal price = BigDecimal.valueOf(1000 * Math.random());
            price = price.setScale(2, RoundingMode.HALF_UP);
            Order order = new Order();
            order.setOrderId(orderId);
            order.setUserName(UserName.randomGetOne().name());
            order.setGoodsType(GoodsType.randomGetOne());
            order.setNum(num);
            order.setPrice(price);
            order.setTotalPrice(price.multiply(BigDecimal.valueOf(num)));
            order.setOrderTimeSeries(orderTimeSeries);
            order.setOrderTime(orderTime);
            orders.add(order);
        }
        return orders;
    }

    private Long generateOrderId(long orderTimeSeries, int orderId) {
        String id = String.valueOf(orderId);
        int count = 5 - id.length();
        for (int i = 0; i < count; i++) {
            id = "0" + id;
        }
        return Long.parseLong(dateFormat.format(new Date(orderTimeSeries)) + id);
    }
}