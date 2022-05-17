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
        System.out.println("==========================using hive catalog============================");
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
