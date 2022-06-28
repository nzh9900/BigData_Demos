package org.ni.classloader;

import io.github.interestinglab.waterdrop.flink.BaseFlinkSink;
import io.github.interestinglab.waterdrop.flink.tableStream.FlinkStreamTableSource;
import io.github.interestinglab.waterdrop.plugin.Plugin;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class ServiceLoadTest {
    private static CustomClassLoader CLASSLOADER = new CustomClassLoader();

    public static void main(String[] args) {
        File jar = new File("/opt/waterdrop/connectors/flink/plugin-flink-source-kafka-2.0.4-2.11.12.jar");
        CLASSLOADER.addJar(jar.toPath());

        ServiceLoader<FlinkStreamTableSource> load = ServiceLoader.load(FlinkStreamTableSource.class, CLASSLOADER);

        String name = "KafkaStreamTable";
        for (Iterator<FlinkStreamTableSource> it = load.iterator(); it.hasNext(); ) {
            try {
                Plugin<?> plugin = it.next();
                if (StringUtils.equalsIgnoreCase(plugin.getClass().getSimpleName(), name)) {
                    System.out.println("success");
                }
            } catch (ServiceConfigurationError e) {
                // Iterator.next() may throw ServiceConfigurationError,
                // but maybe caused by a not used plugin in this job
                System.out.println("Error when load plugin: " + name + "\n" + e);
            }
        }

        if (!load.iterator().hasNext()) {
            System.out.println("empty");
        }
        for (FlinkStreamTableSource next : load) {
            System.out.println(next.getClass());
        }
    }

}
