package org.ni.classloader;

import io.github.interestinglab.waterdrop.flink.BaseFlinkSink;
import io.github.interestinglab.waterdrop.flink.stream.FlinkStreamSink;
import io.github.interestinglab.waterdrop.flink.stream.FlinkStreamSource;
import io.github.interestinglab.waterdrop.flink.tableStream.FlinkStreamTableSink;

import java.io.File;
import java.util.Iterator;
import java.util.ServiceLoader;

public class LoadWaterdropJar {
    public static void main(String[] args) throws ClassNotFoundException {
        File jarFile = new File("/tmp/plugin-flink-sink-kafka-2.0.4-2.11.12.jar");

        CustomClassLoader classLoader = new CustomClassLoader();
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        classLoader.addJar(jarFile.toPath());

        Thread.currentThread().setContextClassLoader(classLoader);

        Class<?> clazz =
                Class.forName("io.github.interestinglab.waterdrop.flink.sink.KafkaSinkStreamTable", true, classLoader);

        System.out.println(clazz.getName());

        Iterator<BaseFlinkSink> plugins = ServiceLoader.load(BaseFlinkSink.class).iterator();
        boolean b = plugins.hasNext();
        while (b) {
            BaseFlinkSink plugin = plugins.next();
            System.out.println(plugin);
        }

    }


}
