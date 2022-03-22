package com.ni.dataStreamApi.custom.documentExample.socket;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ProducerUtils类:实现的是producer的
 * 1.异步发送
 * 2.异步阻塞发送
 * 3.异步回调发送
 */
public class SendKafkaMessage {
    private final static String TOPIC_NAME = "waterdrop_test";


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Producer异步发送
        producerSend();

/*        //Prducer异步阻塞发送
        producerSyncSend();

        //异步回调发送
        callBackProducerSend();*/


    }

    //############## producer异步向指定topic发送数据 ##################
    public static void producerSend() throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.24.69.3:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "0");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, Object> producer = new KafkaProducer<String, Object>(properties);
        int num = 0;
        int records = 0;
        Random random = new Random();
        while (num <= 60) {
            for (int i = 0; i < 200; i++) {
                //消息对象 ProducerRecord
                String value = "{\"id\":%s,\"name\":\"jackop\",\"gender\":\"female\",\"age\":\"2029-03-10 10:44:57\"}";
                int id = random.nextInt();
                ProducerRecord producerRecord = new ProducerRecord<String, String>(TOPIC_NAME, String.format(value, id));
                producer.send(producerRecord);
            }
            Thread.sleep(10000);
            System.out.println("sending:  " + records++);
            num++;
        }
        //所有的通道打卡都需要关闭
        producer.close();
    }

    //############## producer同步(或者叫异步阻塞)向指定topic发送数据 ##################
    public static void producerSyncSend() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.83.120");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "0");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 10; i++) {
            //消息对象 ProducerRecord
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, "key_" + i, "value_" + i);
            Future<RecordMetadata> send = producer.send(producerRecord);
            RecordMetadata recordMetadata = send.get();//发送一次你一get就把它阻塞子这里了
            System.out.println("partition:" + recordMetadata.partition() + ",offset" + recordMetadata.offset());
        }
        //所有的通道打卡都需要关闭
        producer.close();
    }

    //############## producer异步回调发送 ##################
    public static void callBackProducerSend() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.83.120");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "0");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, Object> producer = new KafkaProducer<String, Object>(properties);
        for (int i = 0; i < 10; i++) {
            //消息对象 ProducerRecord
            ProducerRecord producerRecord = new ProducerRecord<String, String>(TOPIC_NAME, "key_" + i, "value_" + i);
            producer.send(producerRecord, new Callback() { //回调:当kafka执行完成send后就会触发此函数的执行
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println("partition:" + metadata.partition() + ",offset" + metadata.offset());
                }
            });
        }
        //所有的通道打卡都需要关闭
        producer.close();
    }
}
