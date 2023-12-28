package com.ni.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hadoop.hive.metastore.api.*;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.thrift.TException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName HiveMetastoreUsage
 * @Description 使用hive metastore
 * @Author zihao.ni
 * @Date 2023/6/5 18:17
 * @Version 1.0
 **/
public class HiveMetastoreUsage {
    public HiveMetaStoreClient hiveMetaStoreClient;

    public static void main(String[] args) throws TException {
        HiveMetastoreUsage hiveMetastoreUsage = new HiveMetastoreUsage();


        Configuration conf = new Configuration();
        String principal = "idp";
        String keytabFile = "/opt/idp.keytab";
        conf.set("hadoop.security.authentication", "kerberos");
        conf.set("hadoop.security.authorization", "true");
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab(principal, keytabFile);
            System.out.println("认证方式和用户: " + UserGroupInformation.getLoginUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        hiveMetastoreUsage.getConnection();

        List<FieldSchema> partitionKeys = hiveMetastoreUsage.getPartitionKeys("default", "test_888");
        System.out.println(partitionKeys.size());
        partitionKeys.forEach(fieldSchema -> System.out.println(fieldSchema.getName()));

        List<FieldSchema> fields = hiveMetastoreUsage.getFields("default", "test_888");
        hiveMetastoreUsage.printFieldDetail(fields.get(0));
    }

    /**
     * 获取连接
     *
     * @throws MetaException
     */
    private void getConnection() throws MetaException {
        HiveConf conf = new HiveConf();
        conf.addResource(new Path("/Users/ni/IdeaProjects/Work_Demos/hive_usage/src/main/resources/hive-site-cdh6.xml"));
        //conf.set("hive.metastore.uris", "thrift://node22.test.com:9083,thrift://node23.test.com:9083");
        System.out.println(System.currentTimeMillis());
        hiveMetaStoreClient = new HiveMetaStoreClient(conf);
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 建表
     *
     * @throws TException
     */
    private void createTable() throws TException {
        Table hiveTable = new Table();
        hiveTable.setDbName("default");
        hiveTable.setTableName("test_911");
        hiveTable.setTableType(TableType.MANAGED_TABLE.name());
        List<FieldSchema> PARTITION_COLUMNS = Arrays.asList(new FieldSchema("continent", "string", ""),
                new FieldSchema("country", "string", "a\nlntry\t918"));
        List<FieldSchema> DATA_COLUMNS = Arrays.asList(new FieldSchema("id", "bigint", ""),
                new FieldSchema("name", "string", ""), new FieldSchema("city", "tinyint", ""));
        hiveTable.setPartitionKeys(PARTITION_COLUMNS);

        StorageDescriptor sd = new StorageDescriptor();
        sd.setCols(DATA_COLUMNS);
        sd.setParameters(new HashMap<String, String>());
        sd.setSerdeInfo(new SerDeInfo());

        hiveTable.setSd(sd);

        hiveMetaStoreClient.createTable(hiveTable);
    }

    /**
     * 获取所有表名
     *
     * @return
     * @throws MetaException
     */
    private List<String> getAllTableNames(String dbName) throws MetaException {
        return hiveMetaStoreClient.getAllTables(dbName);
    }

    /**
     * 获取表字段
     *
     * @param dbName
     * @param tableName
     * @return
     * @throws TException
     */
    private List<FieldSchema> getFields(String dbName, String tableName) throws TException {
        return hiveMetaStoreClient.getFields(dbName, tableName);
    }

    /**
     * 获取字段信息
     *
     * @param field
     */
    private void printFieldDetail(FieldSchema field) {
        String comment = field.getComment();
        System.out.println(comment);
        System.out.println();
        for (int i = 0; i < comment.length(); i++) {
            char c = comment.charAt(i);
            System.out.println(i + ":" + ('\n' == c) + ":" + c);
        }
    }

    /**
     * 获取分区字段
     *
     * @param dbName
     * @param tableName
     * @return
     * @throws TException
     */
    private List<FieldSchema> getPartitionKeys(String dbName, String tableName) throws TException {
        Table table = hiveMetaStoreClient.getTable(dbName, tableName);
        return table.getPartitionKeys();
    }
}