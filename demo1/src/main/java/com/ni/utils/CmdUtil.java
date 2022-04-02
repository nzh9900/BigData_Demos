package com.ni.utils;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CmdUtil {

    public static final Logger logger = LoggerFactory.getLogger(CmdUtil.class);

    private volatile static CmdUtil util;

    public static CmdUtil getInstance() {
        if (null == util) {
            synchronized (CmdUtil.class) {
                if (util == null) {
                    util = new CmdUtil();
                }
            }
        }
        return util;
    }

    public void execuShell(String... cmd) throws Exception {
        if (cmd.length == 0) {
            logger.warn("Command empty.");
            return;
        }
        logger.info("Execute script: {}", cmd[0]);
        Process process = null;
        BufferedReader bufferedReader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            if (process.exitValue() != 0) {
                bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder stringBuffer = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line).append("\n");
                }
                String errorMessage = stringBuffer.toString();
                logger.error("Execute command error: {}", errorMessage);
                throw new Exception("Execute command error: " + errorMessage);
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (process != null) {
                process.destroy();
            }
        }

    }

    public Pair<Integer, String> executeProcess(String... cmd) {
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
        return new Pair<Integer, String>(exitValue, outputString.toString());
    }

    public Pair<Integer, String> executeProcessAndPutSomething(String inputMessage, String... cmd) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(cmd);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        StringBuilder outputString = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        OutputStream outputStream;
        Process start = null;
        BufferedWriter bufferedWriter = null;
        int exitValue = -1;
        try {
            //启动进程
            start = processBuilder.start();
            //获取输入流
            inputStream = start.getInputStream();
            //获取输出流并写入
            outputStream = start.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(inputMessage);
            bufferedWriter.flush();
            bufferedWriter.close();
            //转成字符输入流
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int len = -1;
            char[] c = new char[1024];
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
            }
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
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Pair<Integer, String>(exitValue, outputString.toString());
    }
}
