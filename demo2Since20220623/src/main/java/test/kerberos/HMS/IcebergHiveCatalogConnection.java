package test.kerberos.HMS;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.iceberg.Schema;
import org.apache.iceberg.Table;
import org.apache.iceberg.catalog.Namespace;
import org.apache.iceberg.catalog.TableIdentifier;
import org.apache.iceberg.hive.HiveCatalog;
import org.apache.iceberg.types.Type;
import org.apache.iceberg.types.Types;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * iceberg hivecatalog ,连接使用kerberos认证的HMS
 */
public class IcebergHiveCatalogConnection {
    public static void main(String[] args) {
        //init("local", "thrift://dev-new-cdh2.lab.com:9083");
        kerberosConnection();
    }



    public static HiveCatalog init(String mode, String uri) {
        Configuration conf = new Configuration();
        if ("local".equals(mode)) {
            //conf.addResource("hadoop_conf/core-site.xml");
            //conf.addResource("hadoop_conf/hdfs-site.xml");
            //conf.addResource("hadoop_conf/yarn-site.xml");
        } else {
            //conf = HadoopUtils.getInstance().getConfiguration();
        }


        HiveCatalog hiveCatalog = new HiveCatalog();
        hiveCatalog.setConf(conf);
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("uri", uri);
        hiveCatalog.initialize("hive", properties);
        return hiveCatalog;

    }

    public static boolean isConnection(HiveCatalog hiveCatalog) {
        Boolean isConnection = false;
        try {
            hiveCatalog.listNamespaces();
            isConnection = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isConnection;
    }

    public static void getSchema(HiveCatalog hiveCatalog) {
        List columnList = new ArrayList<>();
        Table table = hiveCatalog.loadTable(TableIdentifier.of(Namespace.of("datalake"), "oracle_result"));
        Schema schema = table.schema();
        List<Types.NestedField> columns = schema.columns();
        for (Types.NestedField column : columns) {
            HashMap<String, Object> fieldMap = new HashMap<>();
            String name = column.name();
            Type type = column.type();
            boolean contains = schema.identifierFieldNames().contains(name);
            fieldMap.put("fieldName", name);
            fieldMap.put("fieldType", type);
            fieldMap.put("isPrimaryKey", contains);
            columnList.add(fieldMap);
        }
        System.out.println(columnList);
    }

    public static void getDatabases(HiveCatalog hiveCatalog) {
        List<Namespace> namespaces = hiveCatalog.listNamespaces();
        for (Namespace namespace : namespaces) {
            System.out.println(namespace.toString());
        }
        //System.out.println(namespaces);
//        for (Namespace namespace : namespaces) {
//            System.out.println(namespace);
//        }
    }

    public static void getTables(HiveCatalog hiveCatalog) {
        List<TableIdentifier> tables = hiveCatalog.listTables(Namespace.of("datalake"));
//        for (TableIdentifier table : tables) {
//            System.out.println(table.name());
//        }
        System.out.println(tables);
    }


    private static void kerberosConnection() {
        System.setProperty("java.security.krb5.conf", "/home/ni/IdeaProjects/java_flink/demo2Since20220623/src/main/resources/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");
        System.setProperty("HADOOP_JAAS_DEBUG", "true");


        HiveConf conf = new HiveConf();
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

        HiveCatalog hiveCatalog = new HiveCatalog();
        hiveCatalog.setConf(conf);
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("uri", "thrift://dev-new-cdh2.lab.com:9083");
        hiveCatalog.initialize("hive", properties);
        System.out.println(hiveCatalog.listTables(Namespace.of("default")));
    }
}
