package com.ni.spi;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Path;

/**
 * @ClassName CustomClassLoader
 * @Description 自定义类加载器
 * @Author zihao.ni
 * @Date 2023/4/7 10:58
 * @Version 1.0
 **/
public class CustomClassLoader extends URLClassLoader {
    public CustomClassLoader() {
        super(new URL[0]);
    }

    public void addJar(Path jarPath) {
        try {
            URL url = jarPath.toUri().toURL();
            addURL(url);
        } catch (MalformedURLException e) {
            System.out.println("Failed to add jar to classloader. Jar: " + jarPath);
        }
    }
}