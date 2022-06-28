package org.ni;

import io.github.interestinglab.waterdrop.flink.BaseFlinkSink;
import io.github.interestinglab.waterdrop.plugin.Plugin;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.factories.Factory;
import org.ni.classloader.CustomClassLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DynamicAddUserJarWithSeatunnelSqlType {
    private static StreamExecutionEnvironment ENV;
    private static CustomClassLoader CLASSLOADER = new CustomClassLoader();

    private static String connectorType = "kafka";

    private static File file = new File("/tmp/plugin-flink-sink-kafka-2.0.4-2.11.12.jar");


    public static void main(String[] args) throws ClassNotFoundException {
        ENV = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(ENV, settings);


        final Configuration executionEnvConfiguration;
        try {
            executionEnvConfiguration =
                    (Configuration) Objects.requireNonNull(ReflectionUtils.getDeclaredMethod(StreamExecutionEnvironment.class,
                            "getConfiguration")).orElseThrow(() -> new RuntimeException("can't find " +
                            "method: getConfiguration")).invoke(ENV);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        registerJars(executionEnvConfiguration);
        registerWaterdropClass("KafkaStreamTable");

        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(CLASSLOADER);
            tEnv.executeSql("CREATE TABLE KafkaTable (\n" +
                    "  `user_id` BIGINT,\n" +
                    "  `item_id` BIGINT,\n" +
                    "  `behavior` STRING\n" +
                    ") WITH (\n" +
                    "  'connector' = 'kafka',\n" +
                    "  'topic' = 'user_behavior',\n" +
                    "  'properties.bootstrap.servers' = 'localhost:9092',\n" +
                    "  'properties.group.id' = 'testGroup',\n" +
                    "  'scan.startup.mode' = 'earliest-offset',\n" +
                    "  'format' = 'csv'\n" +
                    ")");

            tEnv.executeSql("select * from KafkaTable").print();
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }

    }

    private static void registerWaterdropClass(String name) throws ClassNotFoundException {
        if (name.split("\\.").length != 1) {
            // canonical class name
            Class<Plugin<?>> pluginClass = (Class<Plugin<?>>) Class.forName(name);
            //return pluginClass.getDeclaredConstructor().newInstance();
        }
        ServiceLoader<Plugin<?>> plugins = null;

        plugins = ServiceLoader
                .load((Class<Plugin<?>>) Class.forName(String.valueOf(BaseFlinkSink.class)));

        for (Iterator<Plugin<?>> it = plugins.iterator(); it.hasNext(); ) {
            try {
                Plugin<?> plugin = it.next();
                if (StringUtils.equalsIgnoreCase(plugin.getClass().getSimpleName(), name)) {
                    System.out.println(plugin.getClass());
                }
            } catch (ServiceConfigurationError e) {
                // Iterator.next() may throw ServiceConfigurationError,
                // but maybe caused by a not used plugin in this job
                System.out.println("Error when load plugin: " + name + "\n" + e);
            }
        }
        throw new ClassNotFoundException("Plugin class not found by name :[" + name + "]");
    }

    private static void registerJars(Configuration configuration) {
        Iterator<Factory> factories = ServiceLoader.load(Factory.class).iterator();
        while (factories.hasNext()) {
            Factory factory = factories.next();
            if (factory.factoryIdentifier().equals(connectorType)) {
                System.out.println("connector " + connectorType + " exist");
                return;
            }
        }

        CLASSLOADER.addJar(file.toPath());

        List<String> jars = configuration.get(PipelineOptions.JARS);
        jars = jars == null ? new ArrayList<String>() : jars;

        List<String> classpaths = configuration.get(PipelineOptions.CLASSPATHS);
        classpaths = classpaths == null ? new ArrayList<String>() : classpaths;

        jars.add(file.toURI().toString());
        classpaths.add(file.toURI().toString());

        configuration.set(PipelineOptions.JARS, jars);
        configuration.set(PipelineOptions.CLASSPATHS, classpaths);
    }
}
