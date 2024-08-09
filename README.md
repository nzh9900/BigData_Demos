# 项目名称

该项目是一个大数据相关工具的演示项目，包括以下工具：

- Flink
- Hadoop
- Kafka
- Kerberos
- Paimon
- S3
- Impala

## 项目介绍

该项目旨在演示如何使用大数据相关工具来处理数据。它包含了一些示例代码和配置文件，可以帮助您快速入门这些工具。

- [flink-connector](flink-connector) flink连接器
- [flink-demo](flink-demo) flink使用示例
- [flink-restful-api](flink-restful-api) 调用flink dashboard restful api 
- [flink-sql](flink-sql) flink sql使用示例
- [flink-stream-api](flink-stream-api) flink stream api使用示例
- [flink-udf](flink-udf) flink udf使用示例
- [hdfs-api](hdfs-api) hdfs api使用示例
- [hive-udf](hive-udf) hive udf使用示例
- [hive-usage](hive-usage) hive使用示例
- [iceberg-java-api](iceberg-java-api) iceberg使用示例
- [impala-jdbc-download](impala-jdbc-download) 使用jdbc下载impala查询的内容
- [jdbc-demo](jdbc-demo) 各数据库jdbc连接示例
- [kerberos-authority](kerberos-authority) kerberos认证代码示例
- [paimon-java-api](paimon-java-api) paimon使用示例
- [realtime-project](realtime-project) 实时项目
- [s3-api](s3-api) s3对象存储使用示例
- [spi](spi) java spi使用示例
- [yarn-api](yarn-api) yarn api使用示例

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
- [Kafka文档](https://kafka.apache.org/)
- [Kerberos文档](https://web.mit.edu/kerberos/)