package com.ni.demos.file.format;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.text.StringEscapeUtils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.charset.Charset;

/**
 * @ClassName DBF
 * @Description
 * @Author zihao.ni
 * @Date 2024/4/10 16:31
 * @Version 1.0
 **/
public class DBFRead {

    public static void main(String[] args) {
        CSVParser csvWriteParser = new CSVParserBuilder()
                .withSeparator(StringEscapeUtils.unescapeJava("\\001").charAt(0))
                .withQuoteChar(StringEscapeUtils.unescapeJava("\\002").charAt(0))
                .build();

        try (DBFReader reader =
                     new DBFReader(
                             new FileInputStream("/opt/qtymtzl100066.329"),
                             Charset.forName("GBK"));
             ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter("/opt/write"))
                     .withParser(csvWriteParser)
                     .build();
        ) {
            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String[] value = new String[54];
                for (int i = 0; i < reader.getFieldCount(); i++) {
                    value[i] = row.getString(i).replace("\uE000", "");
                }
                value[53] = "你好";
                csvWriter.writeNext(value);
                csvWriter.flush();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}