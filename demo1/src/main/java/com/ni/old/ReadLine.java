package com.ni.old;

import org.apache.commons.codec.binary.StringUtils;

import java.io.*;

public class ReadLine {
    public static void main(String[] args) throws IOException {
        readTable("C:\\Users\\93172\\Desktop\\新建文件夹\\没有分区的表.txt");

    }


    private static void readLineReplace() throws IOException {
        File file = new File("D:\\IdeaProjects\\java_flink\\demo1\\src\\main\\resources\\执行镜像表创建命令：create table if not exists `ddw_prod_tmp");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("镜像表创建成功")) {
                System.out.println(line.replace("镜像表创建成功", ""));
            }
        }
    }

    private static void readNoPrivilegeTables(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = "";
        String line1 = "";
        String line2 = "";
        int lineCount = 0;
        while ((line = bufferedReader.readLine()) != null) {
            lineCount += 1;
            if (line.contains("org.apache.hive.service.cli.HiveSQLException: Error while compiling statement: FAILED: SemanticException No valid privileges") && !line.contains("Caused")) {
                System.out.println(line2);
            }
            line2 = line1;
            line1 = line;
        }
    }

    private static void readTable(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split("`");
            String tableName = split[1] + split[2] + split[3] +",";
            System.out.print(tableName);
        }
    }
}
