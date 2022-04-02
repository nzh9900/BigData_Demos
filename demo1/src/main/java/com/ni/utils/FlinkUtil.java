package com.ni.utils;


import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class FlinkUtil {
    public static void main(String[] args) {
        String type = args[0];
        String flinkHome = args[1];
        String applicationId = args[2];
        String jobId = args[3];
        String slots = args[4];
        String jobManagerMemory = args[5];
        String taskManagerMemory = args[6];
        String savepointPath = args[7];
        switch (type) {
            case "stopFlinkJobWithSavepoint":
                savepointPath = stopFlinkJobWithSavepoint(flinkHome, applicationId, jobId);
                System.out.println("savepoint路径：" + savepointPath);
                break;
            case "stopFlinkYarnSession":
                System.out.println(stopFlinkYarnSession(flinkHome, applicationId));
                break;
            case "stopFlinkJobWithoutSavepoint":
                System.out.println(stopFlinkJobWithoutSavepoint(flinkHome, applicationId, jobId));
                break;
            case "generateSavepoint":
                System.out.println(generateSavepoint(flinkHome, applicationId, jobId));
                break;
            case "startFlinkJob":
                startFlinkJob("/opt/idp/rt/", "/tmp/rt_job/kudu_sink_0317_1648110959911.config",
                        flinkHome, slots, jobManagerMemory, taskManagerMemory, "./log.log");
                break;
            case "reRestartFlinkJob":
                startFlinkJob("/opt/idp/rt/", "/tmp/rt_job/kudu_sink_0317_1648110959911.config",
                        flinkHome, slots, jobManagerMemory, taskManagerMemory, "./log.log", savepointPath);
                break;
        }
    }

    /**
     * 使用savepoint重启任务
     *
     * @param waterdropHome
     * @param configPath
     * @param flinkHome
     * @param slots
     * @param jobManagerMemory
     * @param taskManagerMemory
     * @param logPath
     * @param savepointPath
     * @return
     */
    public static void startFlinkJob(String waterdropHome, String configPath, String flinkHome, String slots,
                                     String jobManagerMemory, String taskManagerMemory, String logPath, String savepointPath) {
        String cmd = waterdropHome + "/bin/flink_restart.sh " +
                configPath + " " +
                jobManagerMemory + " " +
                taskManagerMemory + " " +
                slots + " " +
                flinkHome + " " +
                logPath + " " +
                savepointPath;
        System.out.println(cmd);
        CmdUtil.getInstance().executeProcess(cmd.split(" "));
    }

    //public static void startFlinkJobWithUserJar(FlinkParameters parameters) {
    //
    //}

    /**
     * 启动flink任务
     *
     * @param waterdropHome
     * @param configPath
     * @param flinkHome
     * @param slots
     * @param jobManagerMemory
     * @param taskManagerMemory
     * @param logPath
     * @return
     */
    public static void startFlinkJob(String waterdropHome, String configPath, String flinkHome, String slots,
                                     String jobManagerMemory, String taskManagerMemory, String logPath) {
        String cmd = waterdropHome + "/bin/flink_first_start.sh " +
                configPath + " " +
                jobManagerMemory + " " +
                taskManagerMemory + " " +
                slots + " " +
                flinkHome + " " +
                logPath + " ";

        CmdUtil.getInstance().executeProcess(cmd.split(" "));
    }


    private static String getRunJarPath() {
        return PropertyUtils.getString("runJarPath");
    }

    /**
     * 停止任务且生成savepoint
     *
     * @param flinkHome
     * @param applicationId
     * @param jobId
     * @return savepoint path
     */
    public static String stopFlinkJobWithSavepoint(String flinkHome, String applicationId, String jobId) {
        String cmdBasicForGenerateSavePoint = "%s/bin/flink stop -t yarn-per-job " + //flink home By flink version
                "-Dyarn.application.id=%s " +                                        //yarn Application id
                "--savepointPath %s " +                                              //save Path
                "%s";                                                                //flink job id

        String cmd = String.format(cmdBasicForGenerateSavePoint,
                flinkHome,
                applicationId,
                getDefaultSavePath(),
                jobId);

        Pair<Integer, String> exitCodeAndLog = CmdUtil.getInstance().executeProcess(cmd.split(" "));

        if (exitCodeAndLog.getValue0() != 0) {
            throw new RuntimeException(cmd + "命令执行失败\n执行日志:" + exitCodeAndLog.getValue1());
        }
        return getSavepointPath(exitCodeAndLog.getValue1());
    }


    /**
     * 停止yarn application
     *
     * @param flinkHome
     * @param applicationId
     * @return log
     */
    public static String stopFlinkYarnSession(String flinkHome, String applicationId) {
        String cmdBasicForStopYarnApplication = "%s/bin/flink-yarn-session -id %s";    //flink home path ; yarn Application id

        String cmd = String.format(cmdBasicForStopYarnApplication, flinkHome, applicationId);

        Pair<Integer, String> exitCodeAndLog =
                CmdUtil.getInstance().executeProcessAndPutSomething("stop", cmd.split(" "));
        if (exitCodeAndLog.getValue0() != 0) {
            throw new RuntimeException(cmd + "命令执行失败\n执行日志:" + exitCodeAndLog.getValue1());
        }
        return exitCodeAndLog.getValue1();
    }


    /**
     * 停止任务，不生成savepoint
     *
     * @param flinkHome
     * @param applicationId
     * @param jobId
     * @return
     */
    public static String stopFlinkJobWithoutSavepoint(String flinkHome, String applicationId, String jobId) {
        String cmdBasicForNotGenerateSavePoint = "%s/bin/flink cancel -t yarn-per-job " +   //flink home path
                "-Dyarn.application.id=%s " +                                               //yarn Application id
                "%s";                                                                       //flink job id

        String cmd = String.format(cmdBasicForNotGenerateSavePoint, flinkHome, applicationId, jobId);
        Pair<Integer, String> exitCodeAndLog = CmdUtil.getInstance().executeProcess(cmd.split(" "));
        if (exitCodeAndLog.getValue0() != 0) {
            throw new RuntimeException(cmd + "命令执行失败\n执行日志:" + exitCodeAndLog.getValue1());
        }
        return exitCodeAndLog.getValue1();
    }

    /**
     * 任务运行中生成savepoint
     *
     * @param flinkHome
     * @param applicationId
     * @param jobId
     * @return savepoint path
     */
    public static String generateSavepoint(String flinkHome, String applicationId, String jobId) {
        String cmdBasicForGenerateSavePoint = "%s/bin/flink savepoint -t yarn-per-job " +  //flink home path
                "-Dyarn.application.id=%s " +                                       //yarn Application id
                "%s " +                                                             //flink job id
                "%s";                                                               //save Path
        String cmd = String.format(cmdBasicForGenerateSavePoint, flinkHome, applicationId, jobId, getDefaultSavePath());
        Pair<Integer, String> exitCodeAndLog = CmdUtil.getInstance().executeProcess(cmd.split(" "));
        if (exitCodeAndLog.getValue0() != 0) {
            throw new RuntimeException(cmd + "命令执行失败\n执行日志:" + exitCodeAndLog.getValue1());
        }
        return getSavepointPath(exitCodeAndLog.getValue1());

    }

    /**
     * 获取默认savepoint路径
     *
     * @return
     */
    private static String getDefaultSavePath() {
        return PropertyUtils.getString("defaultSavepointPath",
                "hdfs://nameservice1/tmp/flink-savepoint");
    }

    /**
     * 多个版本的flink命令路径
     *
     * @param flinkVersion
     * @return FlinkCmdPath
     */
    private static String generateFlinkCmdPath(String flinkVersion) {
        return PropertyUtils.getString(flinkVersion, "flink");
    }

    public static String getSavepointPath(String log) {
        BufferedReader logReader = new BufferedReader(new StringReader(log));
        String line;
        String savepointPath = null;
        try {
            while ((line = logReader.readLine()) != null) {
                if (line.contains("Savepoint completed")) {
                    savepointPath = line.substring(line.indexOf("Path") + 6);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savepointPath;
    }
}
