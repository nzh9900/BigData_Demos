package org.mock.order.utils;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.List;
import java.util.Properties;

/**
 * @ClassName KafkaUtils
 * @Description
 * @Author zihao.ni
 * @Date 2024/1/8 15:39
 * @Version 1.0
 **/
public class KafkaUtils {
    public static void sendToKafka(String bootstrapServers,
                                   String topic,
                                   List<String> messageList) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        try (KafkaProducer kafkaProducer = new KafkaProducer(props)) {

        }

    }
}