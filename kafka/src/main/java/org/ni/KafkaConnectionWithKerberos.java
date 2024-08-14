package org.ni;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaConnectionWithKerberos {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "node24.test.com:9092");
        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
        // sasl
        properties.put("sasl.mechanism", "GSSAPI");
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.kerberos.service.name", "kafka");
        properties.put(SaslConfigs.SASL_JAAS_CONFIG,
                "com.sun.security.auth.module.Krb5LoginModule required " +
                        "useKeyTab=true " +
                        "storeKey=true " +
                        "keyTab=\"/opt/idp.keytab\"" +
                        "principal=\"idp@TEST.COM\";");
        properties.put("request.timeout.ms", 6000);
        AdminClient adminClient = KafkaAdminClient.create(properties);
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        System.out.println(listTopicsResult.names().get());

    }
}