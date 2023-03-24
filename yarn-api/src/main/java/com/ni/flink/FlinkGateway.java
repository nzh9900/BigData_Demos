package com.ni.flink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.configuration.SecurityOptions;
import org.apache.flink.runtime.security.SecurityConfiguration;
import org.apache.flink.runtime.security.SecurityUtils;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * @ClassName FlinkGateway
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/3/22 14:22
 * @Version 1.0
 **/
public class FlinkGateway {
    public static final String HADOOP_CONFIG = "fs.hdfs.hadoopconf";

    private Configuration configuration;

    public void init() {
        if (configuration != null) return;
        configuration = GlobalConfiguration.loadConfiguration("/Users/ni/IdeaProjects/java_flink/yarn-api/src/main/resources/flink");
        configuration.setString(HADOOP_CONFIG, "/Users/ni/IdeaProjects/java_flink/yarn-api/src/main/resources/hadoop");
    }

    public void hadoopSecurity() throws Exception {
        if (configuration.contains(SecurityOptions.KERBEROS_LOGIN_KEYTAB)) {
            SecurityUtils.install(new SecurityConfiguration(configuration));
            System.out.println(UserGroupInformation.getCurrentUser());
        }

    }

    public static void main(String[] args) throws Exception {
        FlinkGateway gateway = new FlinkGateway();
        gateway.init();
        gateway.hadoopSecurity();
    }

}