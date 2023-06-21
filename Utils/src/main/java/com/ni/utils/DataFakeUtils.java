package com.ni.utils;


import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName DataFakeUtils
 * @Description 生成数仓测试数据
 * @Author zihao.ni
 * @Date 2023/6/21 14:31
 * @Version 1.0
 **/
public class DataFakeUtils {
    public static void main(String[] args) throws IOException {
        fakeData1();
    }

    public static void fakeData1() throws IOException {
        // 投资周期
        List<String> periodList = Arrays.asList("短期", "中期", "长期");
        // 投资偏好
        List<String> investmentPreferences = Arrays.asList("货币", "混合", "权益", "固收");

        File newCustomerFile = new File("Utils/src/main/resources/customer-new.txt");
        File newHbaseFile = new File("Utils/src/main/resources/customer_hbase-new.txt");
        if (newCustomerFile.exists()) {
            newCustomerFile.delete();
        }
        if (newHbaseFile.exists()) {
            newHbaseFile.delete();
        }

        try (
                BufferedReader customerReader =
                        new BufferedReader(new FileReader("Utils/src/main/resources/customer.txt"));
                BufferedReader hbaseReader
                        = new BufferedReader(new FileReader("Utils/src/main/resources/customer_hbase.txt"));

                BufferedWriter customerWriter =
                        new BufferedWriter(new FileWriter(newCustomerFile));
                BufferedWriter hbaseWriter =
                        new BufferedWriter(new FileWriter(newHbaseFile));
        ) {
            String customerLine = null;
            while ((customerLine = customerReader.readLine()) != null) {
                String hbaseLine = hbaseReader.readLine();
                // 预期收益率
                int expectedPaybackRate = RandomUtils.getInt(1, 7);
                String appendDetails = "," + RandomUtils.randomAccessList(periodList) + "," +
                        RandomUtils.randomAccessList(investmentPreferences) + "," +
                        expectedPaybackRate;

                customerLine += appendDetails;
                hbaseLine += appendDetails;

                customerWriter.write(customerLine);
                customerWriter.newLine();
                hbaseWriter.write(hbaseLine);
                hbaseWriter.newLine();
            }
            customerWriter.flush();
            hbaseWriter.flush();
        }
    }


}