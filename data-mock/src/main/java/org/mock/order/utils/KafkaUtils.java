package org.mock.order.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.ArrayList;
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
                                   List<String> messageList,
                                   int batchSize,
                                   Duration interval) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        List<String> buffer = new ArrayList<>(batchSize);
        try (KafkaProducer kafkaProducer = new KafkaProducer(props)) {
            for (String message : messageList) {
                if (buffer.size() < batchSize) {
                    buffer.add(message);
                } else {
                    buffer.forEach(msg -> {
                        ProducerRecord<String, String> record =
                                new ProducerRecord<>(topic, msg);
                        kafkaProducer.send(record);
                    });
                    buffer.clear();
                    Thread.sleep(interval.toMillis());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}