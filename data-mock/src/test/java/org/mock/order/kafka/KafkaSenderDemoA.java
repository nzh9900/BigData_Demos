package org.mock.order.kafka;

import org.junit.Test;
import org.mock.order.utils.KafkaUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName KafkaSenderDemoA
 * @Description
 * @Author zihao.ni
 * @Date 2024/2/26 11:39
 * @Version 1.0
 **/
public class KafkaSenderDemoA {
    @Test
    public void sendToKafka() {
        List<String> messageList = new ArrayList<>();
        for (int i = 1; i < 1000000; i++) {
            messageList.add(String.valueOf(i));
        }
        KafkaUtils.sendToKafka(
                "10.24.68.224:9092",
                "abcd",
                messageList,
                1000,
                Duration.ofSeconds(1)
        );
    }
}