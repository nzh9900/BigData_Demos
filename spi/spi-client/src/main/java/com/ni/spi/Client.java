package com.ni.spi;

import java.io.File;
import java.nio.file.Path;
import java.util.ServiceLoader;

/**
 * @ClassName Client
 * @Description spi测试客户端
 * @Author zihao.ni
 * @Date 2023/4/7 10:21
 * @Version 1.0
 **/
public class Client {
    public static void main(String[] args) {
        Client client = new Client();

        System.out.println("======testFromDependency========");

        client.testFromDependency();

        System.out.println("======testFromRemoteJar========");

        CustomClassLoader customClassLoader = client.testFromRemoteJar();

        System.out.println("======testFromDependency========");

        //Thread.currentThread().setContextClassLoader(customClassLoader);
        client.testFromDependency();

    }

    /**
     * 获取pom中的依赖jar中的Service实现类
     */
    public void testFromDependency() {
        ServiceLoader<Service> loadedServices = ServiceLoader.load(Service.class);
        for (Service loadedService : loadedServices) {
            loadedService.print();
        }
    }

    /**
     * 程序运行时，类加载器加载其它路径下的jar
     */
    private void loadJar(CustomClassLoader classLoader, Path... paths) {
        for (Path path : paths) {
            if (path != null) {
                classLoader.addJar(path);
            }
        }
    }

    /**
     * 程序启动后，加载第三方jar中的Service实现类
     */
    public CustomClassLoader testFromRemoteJar() {
        // 加载jar包
        CustomClassLoader customClassLoader = new CustomClassLoader();
        loadJar(customClassLoader,
                new File("spi/spi-client/src/main/resources/thirdJar/spi-implements-remote-1.0-SNAPSHOT.jar").toPath()
        );

        // 查询Service的实现类
        ServiceLoader<Service> loaded = ServiceLoader.load(Service.class, customClassLoader);
        for (Service service : loaded) {
            service.print();
        }
        return customClassLoader;
    }
}