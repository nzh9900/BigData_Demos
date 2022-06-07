package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ShellTest {
    public static void main(String[] args) throws IOException {
        executeProcess("su", "ni", "-c", "whoami");
    }


    public static void executeProcess(String... cmd) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(cmd);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        StringBuilder outputString = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        Process start = null;
        int exitValue = -1;
        try {
            //启动进程
            start = processBuilder.start();
            //获取输入流
            inputStream = start.getInputStream();
            //转成字符输入流
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int len = -1;
            char[] c = new char[1024];
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
            }
            System.out.println(outputString);
            exitValue = start.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (start != null) {
                start.destroy();
            }
        }
    }
}
