package com.ni.utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import kafka.utils.ZkUtils;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaKerberosConnection {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.setProperty("java.security.auth.login.config",
                "/home/ni/IdeaProjects/java_flink/demo1/src/main/resources/jaas-kafka-client.conf");
        System.setProperty("java.security.krb5.conf", "/home/ni/IdeaProjects/java_flink/demo1/src/main/resources/krb5.conf");

        Properties props = new Properties();
        props.put("bootstrap.servers", "melon.lab.com:9092");
        props.put("group.id", "test_group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // sasl
        props.put("sasl.mechanism", "GSSAPI");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.kerberos.service.name", "kafka-server");

        //@SuppressWarnings("resource")
        //KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //String topic = "test";
        //consumer.subscribe(Arrays.asList(topic));
        //while (true) {
        //    try {
        //        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        //        for (ConsumerRecord<String, String> record : records) {
        //            System.out.printf("offset = %d, partition = %d, key = %s, value = %s%n",
        //                    record.offset(), record.partition(), record.key(), record.value());
        //        }
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //}
        AdminClient client = KafkaAdminClient.create(props);
        System.out.println("=============");
        for (TopicListing listing : client.listTopics().listings().get()) {
            System.out.println(listing);
        }
        System.out.println("=============end");
        client.close();
    }

}