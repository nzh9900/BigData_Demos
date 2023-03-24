package com.ni.yarn;

import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;

/**
 * @ClassName YarnGateWay
 * @Description yarn 客户端api的使用
 * @Author zihao.ni
 * @Date 2023/3/22 13:21
 * @Version 1.0
 **/
public class YarnGateway {
    private YarnClient yarnClient;

    public void init() {
        if (yarnClient != null) return;

        YarnConfiguration conf = new YarnConfiguration();
        conf.addResource("hadoop/core-site.xml");
        conf.addResource("hadoop/hdfs-site.xml");
        conf.addResource("hadoop/yarn-site.xml");

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



        yarnClient = YarnClient.createYarnClient();
        yarnClient.init(conf);
        yarnClient.start();
    }

    public ApplicationReport getApplication(String applicationId) {
        if (yarnClient == null) {
            init();
        }
        ApplicationReport applicationReport;
        try {
            applicationReport = yarnClient.getApplicationReport(ApplicationId.fromString(applicationId));
        } catch (YarnException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return applicationReport;
    }

    public static void main(String[] args) {
        YarnGateway yarnGateWay = new YarnGateway();
        yarnGateWay.init();
        System.out.println(yarnGateWay.getApplication("application_1667201981464_44227"));
    }
}