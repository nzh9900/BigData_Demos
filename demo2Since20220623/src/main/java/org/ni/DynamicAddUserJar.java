package org.ni;

import io.github.interestinglab.waterdrop.flink.BaseFlinkSink;
import io.github.interestinglab.waterdrop.plugin.Plugin;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class DynamicAddUserJar {
    private static StreamExecutionEnvironment ENV;
    private static ClassLoader CLASSLOADER = Thread.currentThread().getContextClassLoader();

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        ENV = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(ENV, settings);

        URL url1 = new URL("file://tmp/plugin-flink-sink-kafka-2.0.4-2.11.12.jar");
        URL url2 = new URL("file://home/ni/IdeaProjects/waterdrop-2.0.4/waterdrop-core/target/waterdrop-core-2.0.4-2.11.12.jar");

        loadJar(url1);
        loadJar(url2);
        registerPlugin(url1);
        registerPlugin(url2);
        spiRegister();


        tEnv.executeSql("CREATE TABLE KafkaTable (\n" +
                "  `user_id` BIGINT,\n" +
                "  `item_id` BIGINT,\n" +
                "  `behavior` STRING,\n" +
                "  `ts` TIMESTAMP(3) METADATA FROM 'timestamp'\n" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'user_behavior',\n" +
                "  'properties.bootstrap.servers' = 'localhost:9092',\n" +
                "  'properties.group.id' = 'testGroup',\n" +
                "  'scan.startup.mode' = 'earliest-offset',\n" +
                "  'format' = 'csv'\n" +
                ")");

        tEnv.executeSql("select * from KafkaTable");


    }

    private static void spiRegister() throws ClassNotFoundException {
        ServiceLoader<BaseFlinkSink> plugins =
                ServiceLoader.load(BaseFlinkSink.class);
        if (plugins.iterator().hasNext() == false) {
            throw new RuntimeException("can not load class :" + BaseFlinkSink.class.getName());
        }

        String name = "io.github.interestinglab.waterdrop.flink.sink.KafkaTable";
        for (Iterator<BaseFlinkSink> it = plugins.iterator(); it.hasNext(); ) {
            try {
                Plugin<?> plugin = it.next();
                if (StringUtils.equalsIgnoreCase(plugin.getClass().getSimpleName(), name)) {
                    System.out.println("true");
                }
            } catch (ServiceConfigurationError e) {
                // Iterator.next() may throw ServiceConfigurationError,
                // but maybe caused by a not used plugin in this job
                System.out.println("Error when load plugin: " + name + "\n" + e);
            }
        }
    }

    private static void loadJarNew(URL jarUrl) {
        CLASSLOADER = new URLClassLoader(
                new URL[]{jarUrl}, CLASSLOADER
        )
        ;
    }

    private static void loadJar(URL jarUrl) {
        //从URLClassLoader类加载器中获取类的addURL方法
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        // 获取方法的访问权限
        boolean accessible = method.isAccessible();
        // 获取系统类加载器
        URLClassLoader classLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        try {
            //修改访问权限为可写
            if (accessible == false) {
                method.setAccessible(true);
            }
            //jar路径加入到系统url路径里
            method.invoke(classLoader, jarUrl);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
            Thread.currentThread().setContextClassLoader(classLoader);
        }
    }


    public static void registerPlugin(URL pathUrl) {
        Configuration configuration;
        try {
            configuration =
                    (Configuration) Objects.requireNonNull(ReflectionUtils.getDeclaredMethod(StreamExecutionEnvironment.class,
                            "getConfiguration")).orElseThrow(() -> new RuntimeException("can't find " +
                            "method: getConfiguration")).invoke(ENV);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String> jars = configuration.get(PipelineOptions.JARS);
        if (jars == null) {
            jars = new ArrayList<>();
        }
        jars.add(pathUrl.toString());
        configuration.set(PipelineOptions.JARS, jars);
        List<String> classpath = configuration.get(PipelineOptions.CLASSPATHS);
        if (classpath == null) {
            classpath = new ArrayList<>();
        }
        classpath.add(pathUrl.toString());
        configuration.set(PipelineOptions.CLASSPATHS, classpath);
    }
}
