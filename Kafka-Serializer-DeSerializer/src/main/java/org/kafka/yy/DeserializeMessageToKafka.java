package org.kafka.yy;

import com.comstar.cci.contract.protocol.fastcci.serialization.Serializer;
import com.comstar.cci.contract.protocol.fastcci.serialization.SerializerFactory;
import com.comstar.cnp.service.contract.data.cti.std.LightFieldMap;
import com.comstar.cnp.service.contract.data.cti.std.StdImsMsg;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName DeserializeMessageToKafka
 * @Description
 * @Author zihao.ni
 * @Date 2023/12/7 10:13
 * @Version 1.0
 **/
public class DeserializeMessageToKafka {
    public static void main(String[] args) {
        String bootstrapServers = "10.24.68.224:9092";
        String topic = "yy-test";
        handle(bootstrapServers, topic);
    }


    public static void handle(String bootstrapServers, String topic) {
        // 设置Kafka生产者的配置
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.comstar.cnp.service.contract.data.cti.std.MarketKafkaSerializer");

        // 创建Kafka生产者实例
        KafkaProducer<String, StdImsMsg> producer = new KafkaProducer<>(props);

        try {
            // 发送消息
            StdImsMsg message = new StdImsMsg();
            message.setMsgId(126756501L);
            message.setSendTime(588149569L);
            message.setMsgType("MB_ORI_PATR_BOND_QUOTE");
            message.setMsgSource("ORI-MONEY-BROKER");
            message.setComstarOrgId(null);
            message.setSourceOrgId("102138031000000250011");
            message.setComstarCategory(Byte.parseByte("1"));
            message.setComstarCode("102281294U_PATR.BRK");
            message.setSecurityKey("102281294");
            message.setSerializerCode(Byte.parseByte("3"));
            //message.setBytes(generateBody(message.getSerializerCode()), 111);
            message.setBody(generateBody());

            ProducerRecord<String, StdImsMsg> record = new ProducerRecord<>(topic, null, message);
            RecordMetadata metadata = producer.send(record).get();
            System.out.printf("Message with key %s and value %s sent to topic %s is committed to %d partitions.%n",
                    record.key(), record.value(), record.topic(), metadata.partition());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭Kafka生产者
        producer.close();
    }

    private static byte[] generateBody(byte serializerCode) {
        LightFieldMap body = new LightFieldMap();
        body.setFieldValue("CONTRIBUTOR", "PATR");
        body.setFieldValue("YTM", "3.54Y");
        body.setFieldValue("DSPLY_NAME", "22保利发展MTN903B");
        body.setFieldValue("SYMBOL", 102281294);
        body.setFieldValue("RATING", "AAA");
        body.setFieldValue("TIME", "09:46:389");
        body.setFieldValue("TRANSACTTIME", "20231201-09:46:38.389");
        body.setFieldValue("BOOK_TYPE", 1);
        body.setFieldValue("TYPE", "BOND");

        List<LightFieldMap> arrayMap = new ArrayList<>();
        LightFieldMap mapInArray1 = new LightFieldMap();
        LightFieldMap mapInArray2 = new LightFieldMap();
        arrayMap.add(mapInArray1);
        arrayMap.add(mapInArray2);

        mapInArray1.setFieldValue("OFR_SIZE", 60000000);
        mapInArray1.setFieldValue("OFR_PRICEDESC", "6000(*)");
        mapInArray1.setFieldValue("LEVEL", 2);
        mapInArray1.setFieldValue("STD_OFR_SIZE", 6000);
        mapInArray1.setFieldValue("OFR_SIZE_MULTI", 6000);
        mapInArray1.setFieldValue("OFR_YIELD", 6.23);

        mapInArray2.setFieldValue("OFR_SIZE", 50000000);
        mapInArray2.setFieldValue("OFR_SIZE_MULTI", 5000);
        mapInArray2.setFieldValue("OFR_PRICEDESC", "5000(*)");
        mapInArray2.setFieldValue("LEVEL", 1);
        mapInArray2.setFieldValue("STD_OFR_SIZE", 5000);
        mapInArray2.setFieldValue("OFR_YIELD", 3.23);

        body.setFieldMapList("OFR_BOOK", arrayMap);

        Serializer serializer = SerializerFactory.getSerializer(serializerCode);
        return serializer.writeObject(body);
    }

    private static LightFieldMap generateBody() {
        LightFieldMap body = new LightFieldMap();
        body.setFieldValue("CONTRIBUTOR", "PATR");
        body.setFieldValue("YTM", "3.54Y");
        body.setFieldValue("DSPLY_NAME", "22保利发展MTN903B");
        body.setFieldValue("SYMBOL", 102281294);
        body.setFieldValue("RATING", "AAA");
        body.setFieldValue("TIME", "09:46:389");
        body.setFieldValue("TRANSACTTIME", "20231201-09:46:38.389");
        body.setFieldValue("BOOK_TYPE", 1);
        body.setFieldValue("TYPE", "BOND");

        List<LightFieldMap> arrayMap = new ArrayList<>();
        LightFieldMap mapInArray1 = new LightFieldMap();
        LightFieldMap mapInArray2 = new LightFieldMap();
        arrayMap.add(mapInArray1);
        arrayMap.add(mapInArray2);

        mapInArray1.setFieldValue("OFR_SIZE", 60000000);
        mapInArray1.setFieldValue("OFR_PRICEDESC", "6000(*)");
        mapInArray1.setFieldValue("LEVEL", 2);
        mapInArray1.setFieldValue("STD_OFR_SIZE", 6000);
        mapInArray1.setFieldValue("OFR_SIZE_MULTI", 6000);
        mapInArray1.setFieldValue("OFR_YIELD", 6.23);

        mapInArray2.setFieldValue("OFR_SIZE", 50000000);
        mapInArray2.setFieldValue("OFR_SIZE_MULTI", 5000);
        mapInArray2.setFieldValue("OFR_PRICEDESC", "5000(*)");
        mapInArray2.setFieldValue("LEVEL", 1);
        mapInArray2.setFieldValue("STD_OFR_SIZE", 5000);
        mapInArray2.setFieldValue("OFR_YIELD", 3.23);

        body.setFieldMapList("OFR_BOOK", arrayMap);
        return body;
    }
}
