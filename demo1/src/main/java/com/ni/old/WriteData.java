package com.ni.old;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WriteData {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\data.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        for (int j = 0; j < 2001; j++) {
            fileOutputStream.write(("01-00889-225b-413a-962b-07ee29ab572d\n" +
                    "01-00829-a07d-45f1-97ea-7574907546fc\n" +
                    "01-00220-073f-48c3-b794-22e64a7180f9\n" +
                    "01-00700-96c5-4ff6-a5e6-711336591387\n" +
                    "01-00312-9232-4e15-b706-13a9ef623cc2\n" +
                    "01-00399-57e3-4f5e-98b5-8f4582fa33c3\n" +
                    "01-00510-14ba-450e-b0eb-a53cf300bd3a\n" +
                    "01-00168-728c-43b7-9045-241169483423\n" +
                    "01-00670-93d7-4241-b3f9-3187cd2d244b\n" +
                    "aaa\n").getBytes(StandardCharsets.UTF_8));
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
