package test.kerberos.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaClientWithKerberos {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        System.setProperty("java.security.krb5.conf", "/home/ni/IdeaProjects/java_flink/demo2Since20220623/src/main/resources/krb5.conf");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.kerberos.service.name", "kafka");
        props.put("sasl.mechanism", "GSSAPI");
        props.put(SaslConfigs.SASL_JAAS_CONFIG,
                "com.sun.security.auth.module.Krb5LoginModule required " +
                        "useKeyTab=true " +
                        "storeKey=true " +
                        "keyTab=\"/home/ni/IdeaProjects/java_flink/demo2Since20220623/src/main/resources/octopus.keytab\"" +
                        "principal=\"octopus@RUISDATA.COM\";");

        props.put("bootstrap.servers", "dev-new-cdh1.lab.com:9092,dev-new-cdh2.lab.com:9092,dev-new-cdh3.lab.com:9092");

        AdminClient client = KafkaAdminClient.create(props);
        ListTopicsResult listTopicsResult = client.listTopics();
        KafkaFuture<Set<String>> names = listTopicsResult.names();
        System.out.println(names.get());

    }
}
