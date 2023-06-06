package com.ni.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName HiveMetastoreUsage
 * @Description 使用hive metastore
 * @Author zihao.ni
 * @Date 2023/6/5 18:17
 * @Version 1.0
 **/
public class HiveMetastoreUsage {
    public HiveMetaStoreClient hiveMetaStoreClient;

    public static void main(String[] args) throws MetaException {
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

        System.out.println(hiveMetastoreUsage.hiveMetaStoreClient.getAllDatabases());
        hiveMetastoreUsage.hiveMetaStoreClient.getTableMeta("","", Arrays.asList(""));

    }

    private void getConnection() throws MetaException {
        HiveConf conf = new HiveConf();
        conf.addResource(new Path("hive-site.xml"));
        hiveMetaStoreClient = new HiveMetaStoreClient(conf);
    }
}