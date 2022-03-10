package com.ni;

import java.io.*;
import java.util.ArrayList;

public class IOTest {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\IdeaProjects\\java_flink\\demo1\\src\\main\\resources\\人群标签.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int rowsReadIn = 0;
        ArrayList<String> contentList = new ArrayList<>();
        int rowsWriteOut = 0;
        boolean stopFlag = true;
        while ((line = reader.readLine()) != null && stopFlag) {
            ++rowsReadIn;
            if (rowsReadIn >= 1) {
                contentList.add(line);
                ++rowsWriteOut;
            }
            if (rowsWriteOut >= 10) {
                stopFlag = false;
            }
        }
        System.out.println(contentList.size());
        for (String linea : contentList) {
            System.out.println(linea + "\t");

        }
    }
}
