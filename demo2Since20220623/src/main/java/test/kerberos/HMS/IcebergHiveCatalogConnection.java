package test.kerberos.HMS;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.iceberg.Table;
import org.apache.iceberg.catalog.Namespace;
import org.apache.iceberg.catalog.TableIdentifier;
import org.apache.iceberg.hive.HiveCatalog;

import java.io.IOException;
import java.util.HashMap;

/**
 * iceberg hivecatalog ,连接使用kerberos认证的HMS
 */
public class IcebergHiveCatalogConnection {
    public static void main(String[] args) {
        String krb5Path = "/Users/ni/IdeaProjects/java_flnk/demo2Since20220623/src/main/resources/krb5.conf";
        String servicePrincipal = "hive/dev-new-cdh2.lab.com@RUISDATA.COM";
        String principal = "octopus@RUISDATA.COM";
        String keytabPath = "/Users/ni/IdeaProjects/java_flnk/demo2Since20220623/src/main/resources/octopus.keytab";
        String uri = "thrift://dev-new-cdh2.lab.com:9083";
        HiveCatalog hiveCatalog = initConnectionUseKerberos(krb5Path, principal, servicePrincipal, keytabPath, uri);


        System.out.println(hiveCatalog.listTables(Namespace.of("datalake")));
        Table table = hiveCatalog.loadTable(TableIdentifier.of(Namespace.of("datalake"), "mysql_result"));
        System.out.println(table.location());
    }


    private static HiveCatalog initConnectionUseKerberos(String krb5Path, String principal, String servicePrincipal, String keytabPath, String uri) {
        System.setProperty("java.security.krb5.conf", krb5Path);
        System.setProperty("sun.security.krb5.debug", "true");
        System.setProperty("HADOOP_JAAS_DEBUG", "false");

        HiveConf conf = new HiveConf();
        conf.addResource("hadoop_conf-cdh6/core-site.xml");
        conf.addResource("hadoop_conf-cdh6/hdfs-site.xml");
        conf.addResource("hadoop_conf-cdh6/yarn-site.xml");
        conf.set("hadoop.security.authentication", "kerberos");
        //todo 默认为true
        conf.set("hive.metastore.execute.setugi", "true");
        conf.set("hive.security.authorization.enabled", "false");
        conf.set("hive.metastore.sasl.enabled", "true");
        conf.set("hive.metastore.kerberos.principal", servicePrincipal);
        conf.set("hive.server2.authentication.kerberos.principal", servicePrincipal);
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab(principal, keytabPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        HiveCatalog hiveCatalog = new HiveCatalog();
        hiveCatalog.setConf(conf);
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("uri", uri);
        hiveCatalog.initialize("hive", properties);
        return hiveCatalog;
    }
}
