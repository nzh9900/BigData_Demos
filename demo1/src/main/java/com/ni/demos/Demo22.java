package com.ni.demos;

/**
 * @ClassName Demo22
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/9/19 18:35
 * @Version 1.0
 **/
public class Demo22 {

    String username;
    String tenantName;
    String clusterName;
    public static void main(String[] args) {

    }


    private void parse(String name) {
        String[] nameList = name.split("@");
        if (nameList.length >= 2) {
            username = nameList[0];
            String[] tenantCluster = nameList[1].split("#");
            if (tenantCluster.length >= 2) {
                tenantName = tenantCluster[0];
                clusterName = tenantCluster[1];
            } else {
                tenantName = nameList[1];
                clusterName = "default_cluster";
            }
        } else {
            username = name;
            tenantName = "sys";
            clusterName = "default_cluster";
        }
    }
}