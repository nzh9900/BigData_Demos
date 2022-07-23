package test.kerberos.HMS;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.thrift.TException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KerberosWithHiveMetastoreClient {
    private static final String USER_NAME = "user.name";
    // Some Hive Metastore properties

    public static void main(String[] args) {
        KerberosWithHiveMetastoreClient test = new KerberosWithHiveMetastoreClient();
        try {
            test.testHiveColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testHiveColumn() throws TException {

        System.setProperty("java.security.krb5.conf", "/home/ni/IdeaProjects/java_flink/demo2Since20220623/src/main/resources/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");
        System.setProperty("HADOOP_JAAS_DEBUG", "true");


        HiveConf conf = new HiveConf(this.getClass());
        conf.set("hive.metastore.uris", "thrift://10.24.65.120:9083");
        conf.set("hadoop.security.authentication", "kerberos");
        //todo 默认为true
        conf.set("hive.metastore.execute.setugi", "true");
        conf.set("hive.security.authorization.enabled", "false");
        conf.set("hive.metastore.sasl.enabled", "true");
        conf.set("hive.metastore.kerberos.principal", "hive/dev-new-cdh2.lab.com@RUISDATA.COM");
        conf.set("hive.server2.authentication.kerberos.principal", "hive/dev-new-cdh2.lab.com@RUISDATA.COM");
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab("octopus@RUISDATA.COM", "/home/ni/IdeaProjects/java_flink/demo2Since20220623/src/main/resources/octopus.keytab");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        HiveMetaStoreClient client = new HiveMetaStoreClient(conf);
        List<String> aDefault = client.getAllDatabases();
        System.out.println(aDefault);
    }
}
