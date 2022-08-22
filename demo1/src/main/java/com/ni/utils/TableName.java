package com.ni.utils;

public class TableName {
    private static String quoteTableName(String tableName) {
        if (tableName.contains(".")) {
            String[] split = tableName.split("\\.");
            String databaseName = split[0];
            String table = split[1];
            tableName = String.format("\"%s\".\"%s\"", databaseName, table);
        } else {
            tableName = String.format("\"%s\"", tableName);
        }
        return tableName;
    }

    public static void main(String[] args) {
        System.out.println(quoteTableName("FLINK.USER"));
    }
}
