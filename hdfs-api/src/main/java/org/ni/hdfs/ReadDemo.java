package org.ni.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * @ClassName DemoA
 * @Description
 * @Author zihao.ni
 * @Date 2024/1/29 11:25
 * @Version 1.0
 **/
public class ReadDemo {
    public static void main(String[] args) throws IOException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        configuration.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
        configuration.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

        // kerberos
        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab(args[2], args[3]);
        System.out.println(UserGroupInformation.getCurrentUser().getUserName());

        // 配置在集群上运行
        FileSystem fs = FileSystem.get(configuration);

        fs.copyToLocalFile(new Path(args[0]),
                new Path(args[1]));
        // 3 关闭资源
        fs.close();
    }
}