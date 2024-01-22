package org.mock.order.entity;

/**
 * @ClassName UserName
 * @Description
 * @Author zihao.ni
 * @Date 2024/1/8 15:13
 * @Version 1.0
 **/
public enum UserName {
    ORACLE, DB2, HIVE, MYSQL, DORIS;

    public static UserName randomGetOne() {
        int index = (int) (Math.random() * UserName.values().length);
        return UserName.values()[index];
    }
}