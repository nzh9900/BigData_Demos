package com.ni;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SqoopLog
 * @Description
 * @Author zihao.ni
 * @Date 2024/3/15 11:36
 * @Version 1.0
 **/
@Slf4j
public class MessageFromSqoopLog {
    public static void main(String[] args) throws FileNotFoundException {
        MessageFromSqoopLog messageFromSqoopLog = new MessageFromSqoopLog();
        messageFromSqoopLog.getLog();
    }

    public void getLog() throws FileNotFoundException {
        List<String> errorMessage = new ArrayList<>();
        SqoopExecInfo sqoopExecInfo = new SqoopExecInfo();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/ni/IdeaProjects/Work_Demos/demo1/src/main/resources/sqoop.log")));
        bufferedReader.lines().forEach(line -> {
            //log.info(line);
            if (line.contains("Exception:") || line.contains("Exception :")
                    || line.contains("ERROR tool.BaseSqoopTool: Error parsing arguments")
                    || line.contains("errno=")) {
                errorMessage.add(line);
            }

            if (line.contains("Transferred")) {
                log.info("------- Transferred -------");
                log.info(line);
                String transferredLog = StringUtils.substringAfter(line, "Transferred");
                sqoopExecInfo.setTimeConsumption(StringUtils.substringBetween(transferredLog, "in", "seconds"));
                log.info(sqoopExecInfo.getTimeConsumption());
                sqoopExecInfo.setAverageFlow(StringUtils.substringBetween(transferredLog, "(", ")"));
                log.info(sqoopExecInfo.getAverageFlow());
            }
            if (line.contains("Retrieved")) {
                log.info("------- Retrieved -------");
                log.info(line);
                String retrievedLog = StringUtils.substringAfter(line, "Retrieved");
                sqoopExecInfo.setTotalRecords(StringUtils.substringBetween(retrievedLog, " ", "records"));
                log.info(sqoopExecInfo.getTotalRecords());
            }
            if (line.contains("mapreduce.ExportJobBase: Exported") && line.contains("records")) {
                log.info("------- Exported -------");
                log.info(line);
                String retrievedLog = StringUtils.substringAfter(line, "Exported");
                sqoopExecInfo.setTotalRecords(StringUtils.substringBetween(retrievedLog, " ", "records"));
                log.info(sqoopExecInfo.getTotalRecords());
            }
        });
        log.info("==================================");
        log.info(sqoopExecInfo.getTotalRecords());
        log.info(sqoopExecInfo.getAverageFlow());
        log.info(sqoopExecInfo.getTimeConsumption());
        log.info(sqoopExecInfo.getRecordWriteSpeed());
        log.info(sqoopExecInfo.toString());

    }

    @Data
    public class SqoopExecInfo {
        /**
         * 任务总计耗时
         */
        private String timeConsumption;

        /**
         * 任务平均流量
         */
        private String averageFlow;

        /**
         * 记录写入速度
         */
        private String recordWriteSpeed;

        /**
         * 读出记录总数
         */
        private String totalRecords;

        @Override
        public String toString() {
            return "任务总计耗时      : " + timeConsumption.trim() + " secsonds\n" +
                    "任务平均流量      : " + averageFlow.trim() + "\n" +
                    "记录写入速度      : " + (int) Math.ceil(Double.parseDouble(totalRecords.trim()) / Double.parseDouble(timeConsumption.trim())) + " rec/secs \n" +
                    "读出记录总数      : " + totalRecords.trim() + " rec";
        }

    }


}