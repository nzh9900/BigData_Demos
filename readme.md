# 项目名称

该项目是一个大数据相关工具的演示项目，包括以下工具：

- Flink
- Hadoop
- Spark
- Kafka
- Kerberos
- Spring Boot
- Jackson
- Slf4j
- Java Spi
- Java Thread

## 项目介绍

该项目旨在演示如何使用大数据相关工具来处理数据。它包含了一些示例代码和配置文件，可以帮助您快速入门这些工具。

## 环境要求

在运行该项目之前，您需要安装以下工具：

- Java 8+

## 如何运行

1. 克隆该项目到本地：

   ```
   git clone https://github.com/nzh9900/Work_Demos.git
   ```

2. 进入项目目录：

   ```
   cd Work_Demos
   ```
3. 找到相关demo示例

4. 配置Kerberos：
   使用您的keytab文件和krb5.conf
   ```
   kinit -kt {{keytab-file}} {{principal}}
   ```

5. 参考示例代码并运行

## 示例代码

该项目包含了一些示例代码，可以帮助您快速入门这些工具。以下是一些示例代码的说明：

- `flink-connector`: flink 数据源连接器。
- `flink-demo`: flink table api常用场景下的demo示例。
- `flink-stream-api`: flink stream api常用场景下的demo示例。
- `flink-udf`: flink udf 函数示例。
- `impala download`: kerberos环境下impala下载Hive数据文件示例。
- `kerberos`: 使用Java代码进行kerberos认证。
- `spi`: Java Spi使用代码示例。
- `yarn`: Java Yarn api获取yarn上运行的任务信息示例。

## 参考资料

- [Flink文档](https://flink.apache.org/)
- [Hadoop文档](https://hadoop.apache.org/)
- [Spark文档](https://spark.apache.org/)
- [Kafka文档](https://kafka.apache.org/)
- [Kerberos文档](https://web.mit.edu/kerberos/)
- [Spring Boot文档](https://spring.io/projects/spring-boot)