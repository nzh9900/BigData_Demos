package com.ni.dataStreamApi.killJobOnYarn;

import org.apache.flink.api.common.JobID;
import org.apache.flink.client.cli.CliArgsException;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.util.FlinkException;
import org.apache.flink.yarn.YarnClusterClientFactory;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.hadoop.yarn.api.records.ApplicationId;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class StopYarnJob {
    public static void main(String[] args) throws FlinkException, CliArgsException, ExecutionException, InterruptedException {
        //String hadoop_home = System.getProperty("HADOOP_HOME");
        //System.out.println("hadoop_home = " + hadoop_home);

        String hadoopHome = System.getProperty("HADOOP_HOME");
        if (hadoopHome == null) {
            System.setProperty("HADOOP_HOME", "/usr/lib/hadoop");
        }
        String configurationDirectory = "/etc/flink/conf";
        String appId = "application_1647875528040_0031";
        String jobid = "cf3a1471bba8ad0aa7ac363cb0978d27";
        String savePoint = "hdfs://nameservice1/flink-savepoints5";
        //获取flink的配置
        Configuration flinkConfiguration = GlobalConfiguration.loadConfiguration(
                configurationDirectory);
//        Configuration flinkConfiguration = new Configuration();
        flinkConfiguration.set(YarnConfigOptions.APPLICATION_ID, appId);
        YarnClusterClientFactory clusterClientFactory = new YarnClusterClientFactory();
        ApplicationId applicationId = clusterClientFactory.getClusterId(flinkConfiguration);
        if (applicationId == null) {
            throw new FlinkException(
                    "No cluster id was specified. Please specify a cluster to which you would like to connect.");
        }

        YarnClusterDescriptor clusterDescriptor = clusterClientFactory
                .createClusterDescriptor(
                        flinkConfiguration);
        ClusterClient<ApplicationId> clusterClient = clusterDescriptor.retrieve(
                applicationId).getClusterClient();

        JobID jobID = parseJobId(jobid);
        CompletableFuture<String> completableFuture = clusterClient.stopWithSavepoint(
                jobID,
                true,
                savePoint);

        String savepoint = completableFuture.get();
        System.out.println(savepoint);
    }

    private static JobID parseJobId(String jobIdString) throws CliArgsException {
        if (jobIdString == null) {
            throw new CliArgsException("Missing JobId");
        }

        final JobID jobId;
        try {
            jobId = JobID.fromHexString(jobIdString);
        } catch (IllegalArgumentException e) {
            throw new CliArgsException(e.getMessage());
        }
        return jobId;
    }
}