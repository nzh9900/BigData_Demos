package org.ni.classloader;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.ni.ReflectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MysqlCDCAndIcebergSink {
    public static void main(String[] args) {
        CustomClassLoader classloader = new CustomClassLoader();

        for (String jarPath : args) {
            System.out.println("load jar : " + jarPath);
            classloader.addJar(new File(jarPath).toPath());
        }

        ClassLoader systemClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classloader);

            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            StreamTableEnvironment tEnv = StreamTableEnvironment.create(env,
                    EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build());

            Configuration configuration =
                    (Configuration) Objects.requireNonNull(ReflectionUtils.getDeclaredMethod(StreamExecutionEnvironment.class,
                            "getConfiguration")).orElseThrow(() -> new RuntimeException("can't find " +
                            "method: getConfiguration")).invoke(env.getExecutionEnvironment());

            List<String> originalJars = configuration.get(PipelineOptions.JARS);
            List<String> originalClassPath = configuration.get(PipelineOptions.CLASSPATHS);
            if (originalJars == null) {
                originalJars = new ArrayList<>();
            }
            if (originalClassPath == null) {
                originalClassPath = new ArrayList<>();
            }
            for (String jarPath : args) {
                String jarFile = new File(jarPath).toURI().toURL().toString();
                System.out.println("set jar to pipeline: " + jarFile);
                originalJars.add(jarFile);
                originalClassPath.add(jarFile);
            }
            configuration.set(PipelineOptions.JARS, originalJars);
            configuration.set(PipelineOptions.CLASSPATHS, originalClassPath);

            tEnv.executeSql("CREATE CATALOG hive_catalog WITH (\n" +
                    "  'type'='iceberg',\n" +
                    "  'catalog-type'='hive',\n" +
                    "  'uri'='thrift://10.24.66.163:9083',\n" +
                    "  'clients'='5',\n" +
                    "  'property-version'='1',\n" +
                    "  'hive-conf-dir'='/etc/hive/conf'\t\t-- 文档中这个配置没写，但是需要配置上去，值是hive-site.xml的目录\n" +
                    ")");

            tEnv.executeSql("show catalogs").print();

            tEnv.executeSql("insert into hive_catalog.datalake.oracle_result values (10,'ss','ww')");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().setContextClassLoader(systemClassLoader);
        }
    }
}
