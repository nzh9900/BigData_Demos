package table

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.SqlDialect
import org.apache.flink.table.catalog.hive.HiveCatalog


/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-25 14:22
 * */
object HiveSource {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val tEnv = Before.init(env)
        val name = "localhive"
        val defaultDatabase = "mockdata"
        val hiveConfPath = "/opt/module/hive/conf/"
        //val hadoopConfPath = "D:\\IdeaProjects\\java_flink\\flink_connector\\src\\main\\resources\\hadoop_conf_dir"
        // 向量化读取
        //tEnv.getConfig.getConfiguration.setString("table.exec.hive.fallback-mapred-reader", "true")

        val hiveCatalog = new HiveCatalog(name, defaultDatabase, hiveConfPath)
        tEnv.registerCatalog(name,hiveCatalog)
        tEnv.useCatalog(name)
        tEnv.useDatabase(defaultDatabase)
        tEnv.getConfig.setSqlDialect(SqlDialect.HIVE)

        //tEnv.executeSql("create database test_11")
        tEnv.executeSql(
            """
              |select * from mockdata.customer
              |""".stripMargin).print()
        /*tEnv.executeSql(
            """
              |CREATE TABLE mykafka (name String, age Int) WITH (
              |   'connector.type' = 'kafka',
              |   'connector.version' = 'universal',
              |   'connector.topic' = 'user_demo',
              |   'connector.properties.bootstrap.servers' = 'hadoop102:9092',
              |   'format.type' = 'csv',
              |   'update-mode' = 'append'
              |)
              |""".stripMargin)*/
        env.execute()
    }
}
