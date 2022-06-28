
package org.ni.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

// TODO: maybe a unified plugin-style discovery mechanism is better.
public class CustomClassLoader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomClassLoader.class);

    public CustomClassLoader() {
        super(new URL[0]);
    }

    /*
     * If the table declared in 'create table' with connector 'xxx' and the table is not referenced in the job, namely,
     * used in the 'insert into' statement, the connector 'xxx' will not be needed by Flink.
     * So it might be ok fail to load it. If it's needed, we can see the error in Flink logs.
     *
     * Refer https://github.com/apache/incubator-seatunnel/pull/1850
     */
    public void addJar(Path jarPath) {
        try {
            this.addURL(jarPath.toUri().toURL());
        } catch (MalformedURLException e) {
            LOGGER.error("Failed to add jar to classloader. Jar: {}", jarPath, e);
        }
    }
}
