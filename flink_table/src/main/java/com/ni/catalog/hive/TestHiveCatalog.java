package com.ni.catalog.hive;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;

import java.util.Arrays;

public class TestHiveCatalog {

    public static void main(String[] args) throws Exception {
        StreamTableEnvironment tEnv = init();
        setHiveCatalog(tEnv);
        setIcebergCatalog(tEnv);
        for (String arg : args) {
            switch (arg) {
                case "hive":
                    getSomeHiveInformation(tEnv);
                    break;
                case "icebergInsert":
                    insertIntoIcebergTable(tEnv);
                    break;
                case "createIcebergTable":
                    insertIntoNewIcebergTable(tEnv);
                    break;
                case "icebergRead":
                    break;
            }
        }
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
        tEnv.executeSql("insert into `ice_01` select * from `hiveCatalog.datalake.source`");
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
