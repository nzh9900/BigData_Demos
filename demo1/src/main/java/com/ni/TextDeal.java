package com.ni;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class TextDeal {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\IdeaProjects\\java_flink\\demo1\\src\\main\\resources\\人群标签.txt");
        FileReader fileReader = new FileReader(file);
        HashSet<String> tags = new HashSet<>();
        try (BufferedReader tagsReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = tagsReader.readLine()) != null) {
                tags.addAll(Arrays.asList(line.split("\\|")[1].split(",")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String tag : tags) {
            System.out.print(tag + ",");
        }

    }
}
