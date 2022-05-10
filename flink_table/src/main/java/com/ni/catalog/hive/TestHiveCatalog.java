package com.ni.catalog.hive;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;

import java.util.Arrays;

public class TestHiveCatalog {

    public static void main(String[] args) throws Exception {
        StreamTableEnvironment tEnv = init();
        scanTableWithHiveCatalog(tEnv);
    }

    private static StreamTableEnvironment init() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        return StreamTableEnvironment.create(env, EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build());
    }

    private static void scanTableWithHiveCatalog(StreamTableEnvironment tEnv) throws Exception {
        HiveCatalog hiveCatalog =
                new HiveCatalog("hiveCatalog", "datalake", "/home/ni", "3.1.2");
        tEnv.registerCatalog("hiveCatalog", hiveCatalog);
        tEnv.useCatalog("hiveCatalog");
        Arrays.stream(tEnv.listCatalogs()).forEach(System.out::println);
        System.out.println("======================================================");
        Arrays.stream(tEnv.listDatabases()).forEach(System.out::println);
        System.out.println("======================================================");
        tEnv.useDatabase("datalake");
        Arrays.stream(tEnv.listTables()).forEach(System.out::println);
        System.out.println("======================================================");
        //tEnv.executeSql("DESCRIBE iceberg_001").print();
        System.out.println("======================================================");
        //tEnv.executeSql("select * from hiveCatalog.datalake.iceberg_001").print();
        System.out.println("===========================start===========================");
        tEnv.executeSql("drop table source");
        tEnv.executeSql("create  table if not exists source  (id int,name string) with ('connector' = 'datagen')");
        tEnv.executeSql("insert into hiveCatalog.datalake.iceberg_001 select * from source");


        //tEnv.execute("iceberg Test");
    }
}
