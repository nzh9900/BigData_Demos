package table

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment


/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-25 13:54
 * */
object KafkaSource {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val tEnv = Before.init(env)
        tEnv.executeSql(
            """
              |CREATE TABLE KafkaTable (
              |  `name` String,
              |  `age` int,
              |  `ts` TIMESTAMP(3) METADATA FROM 'timestamp'
              |) WITH (
              |  'connector' = 'kafka',
              |  'topic' = 'user_demo',
              |  'properties.bootstrap.servers' = 'hadoop102:9092',
              |  'properties.group.id' = 'testGroup',
              |  'scan.startup.mode' = 'latest-offset',
              |  'format' = 'csv'
              |)""".stripMargin)
        tEnv.executeSql("select * from KafkaTable").print()

        env.execute()


    }

}
