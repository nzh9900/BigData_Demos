package com.ni.catalog.hive;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;

import java.util.Arrays;

public class TestHiveCatalog {

    public static void main(String[] args) throws Exception {
        StreamTableEnvironment tEnv = init();
        //setHiveCatalog(tEnv);
        for (String arg : args) {
            switch (arg) {
                case "hive":
                    getSomeHiveInformation(tEnv);
                    break;
                case "icebergInsert":
                    setIcebergCatalog(tEnv);
                    //insertIntoIcebergTable(tEnv);
                    break;
                case "createIcebergTable":
                    insertIntoNewIcebergTable(tEnv);
                    break;
                case "icebergRead":
                    break;
                case "hudiInsert":
                    createHudiTable(tEnv);
                    break;
            }
        }
    }

    private static void createHudiTable(StreamTableEnvironment tEnv) {
        String sink_table = "CREATE TABLE hudi_users (\n" +
                "  id BIGINT,\n" +
                "  name VARCHAR(20),\n" +
                "  ts TIMESTAMP(3),\n" +
                "  `partition` VARCHAR(20),\n" +
                "  primary key(id) not enforced --必须指定uuid 主键 \n" +
                ")\n" +
                "PARTITIONED BY (`partition`)\n" +
                "with(\n" +
                "  'connector'='hudi',\n" +
                "  'path' = 'hdfs://nameservice1/user/root/hudi/hudi_test',\n" +
                "  'hoodie.datasource.write.recordkey.field' = 'id', -- 主键\n" +
                "  'write.precombine.field'= 'ts', -- 自动precombine的字段\n" +
                "  'write.tasks'= '1',\n" +
                "  'compaction.tasks'= '1',\n" +
                "  'write.rate.limit'= '2000', -- 限速\n" +
                "  'table.type' = 'MERGE_ON_READ', -- 默认COPY_ON_WRITE,可选MERGE_ON_READ\n" +
                "  'compaction.async.enabled'= 'true', -- 是否开启异步压缩\n" +
                "  'compaction.trigger.strategy'= 'num_commits', -- 按次数压缩\n" +
                "  'compaction.delta_commits'= '1', -- 默认为5\n" +
                "  'changelog.enabled'= 'true', -- 开启changelog变更\n" +
                "  'read.streaming.enabled'= 'true', -- 开启流读\n" +
                "  'read.streaming.check-interval'= '3', -- 检查间隔，默认60s\n" +
                "  'hive_sync.enable'='true', -- required，开启hive同步功能\n" +
                "  'hive_sync.mode' = 'hms', -- required, 将hive sync mode设置为hms, 默认jdbc\n" +
                "  'hive_sync.metastore.uris' = 'thrift://10.24.69.87:9083', -- metastore的端口\n" +
                "  'hive_sync.table' = 'hudi_test', -- hive新建表名\n" +
                "  'hive_sync.db' = 'datalake', -- hive新建数据库名\n" +
                "  'hive_sync.support_timestamp' = 'true' -- 兼容hive timestamp类型\n" +
                ")";




        tEnv.executeSql("create  table if not exists source (\n" +
                "  id BIGINT,\n" +
                "  name VARCHAR(20),\n" +
                "  ts TIMESTAMP(3),\n" +
                "  `partition` VARCHAR(20),\n" +
                "  primary key(id) not enforced --必须指定uuid 主键 \n" +
                ") with ('connector' = 'datagen',\n" +
                "      'rows-per-second' = '2' )");



        tEnv.executeSql(sink_table);
        tEnv.executeSql("insert into hudi_users select * from source");
        //tEnv.executeSql("CREATE TABLE datalake.hudi_test(\n" +
        //        "  uuid VARCHAR(20),\n" +
        //        "  name VARCHAR(10),\n" +
        //        "  age INT,\n" +
        //        "  ts TIMESTAMP(3),\n" +
        //        "  `partition` VARCHAR(20)\n" +
        //        ")\n" +
        //        "PARTITIONED BY (`partition`)\n" +
        //        "WITH (\n" +
        //        "  'connector' = 'hudi',\n" +
        //        "  'path' = 'hdfs://nameservice1/user/root/hudi/t1',\n" +
        //        "  'table.type' = 'MERGE_ON_READ',  --MERGE_ON_READ 方式在没生成 parquet 文件前，Hive 不会有输出\n" +
        //        "  'hive_sync.enable' = 'true',     -- Required。开启 Hive 同步功能\n" +
        //        "  'hive_sync.mode' = 'hms',         -- Required。将 hive sync mode 设置为 hms, 默认 jdbc\n" +
        //        "  'hive_sync.metastore.uris' = 'thrift://10.24.69.87:9083' -- Required。metastore 的端口\n" +
        //        ")");
        System.out.println("==============create hudi table success===================");
    }

    private static void setIcebergCatalog(StreamTableEnvironment tEnv) {
        tEnv.executeSql("CREATE CATALOG iceberg_catalog WITH (\n" +
                "  'type'='iceberg',\n" +
                "  'catalog-type'='hive',\n" +
                "  'uri'='thrift://10.24.69.87:9083',\n" +
                "  'clients'='5',\n" +
                "  'property-version'='1',\n" +
                "  'hive-conf-dir'='/etc/hive/conf'\n" +
                ")");
        tEnv.useCatalog("iceberg_catalog");
        System.out.println("==========================using iceberg_catalog============================");
        tEnv.executeSql("show databases").print();
        System.out.println("==========================showing iceberg_catalog's databases============================");
        tEnv.useDatabase("datalake");
        System.out.println(tEnv.getCurrentDatabase());
        System.out.println("==========================showing current database============================");
        tEnv.executeSql("create table if not exists ice_01 (id int,name string) ");
        System.out.println("==========================create table ice_01============================");
        System.out.println(Arrays.toString(tEnv.listTables()));
        System.out.println("==========================show tables============================");
        tEnv.executeSql("insert into `iceberg_001` select * from hiveCatalog.datalake.source");
    }

    private static void createIcebergTable(StreamTableEnvironment tEnv) throws Exception {
        tEnv.sqlQuery("create table if not exists ice_01 (id int,name string) " +
                "with ('connector'='iceberg'," +
                "'catalog-name'='hive_prod'," +
                "'catalog-database'='datalake'," +
                "'catalog-table'='iceberg_001')");

    }

    private static void insertIntoIcebergTable(StreamTableEnvironment tEnv) {
        tEnv.executeSql("create  table if not exists source (id int,name string) with ('connector' = 'datagen')");

        tEnv.executeSql("create table if not exists ice_01 (id int,name string) " +
                "with ('connector'='iceberg'," +
                "'catalog-name'='hiveCatalog'," +
                "'catalog-database'='datalake'," +
                "'catalog-table'='iceberg_001')");

        tEnv.executeSql("insert into ice_01 select * from source");

    }

    private static void insertIntoNewIcebergTable(StreamTableEnvironment tEnv) {
        tEnv.executeSql("create  table if not exists source (id int,name string) with ('connector' = 'datagen')");
        tEnv.executeSql("insert into iceberg_001 select * from source");

    }

    private static StreamTableEnvironment init() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(6 * 1000L);
        return StreamTableEnvironment.create(env, EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build());
    }

    private static void setHiveCatalog(StreamTableEnvironment tEnv) {
        HiveCatalog hiveCatalog =
                new HiveCatalog("hiveCatalog", "datalake",
                        "/etc/hive/conf", "3.1.2");
        tEnv.registerCatalog("hiveCatalog", hiveCatalog);
        tEnv.useCatalog("hiveCatalog");
        System.out.println("==========================using hive_catalog============================");
        tEnv.executeSql("show databases").print();
        System.out.println("==========================showing hive_catalog's databases============================");
    }

    private static void getSomeHiveInformation(StreamTableEnvironment tEnv) {
        System.out.println("==========================list catalogs============================");
        Arrays.stream(tEnv.listCatalogs()).forEach(System.out::println);
        System.out.println("==========================list databases============================");
        Arrays.stream(tEnv.listDatabases()).forEach(System.out::println);
        System.out.println("==========================list tables============================");
        tEnv.useDatabase("datalake");
        Arrays.stream(tEnv.listTables()).forEach(System.out::println);
    }
}
