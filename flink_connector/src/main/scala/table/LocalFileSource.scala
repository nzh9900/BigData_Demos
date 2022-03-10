package table

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-25 11:19
 * */
object LocalFileSource {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val tEnv = Before.init(env)
        tEnv.executeSql(
            """CREATE TABLE sensorTable (
              |     name String,
              |     ts BigInt,
              |     temp double,
              |     dt String
              | ) PARTITIONED BY (dt) WITH (
              |  'connector' = 'filesystem',
              |  'path' = 'file:///D:/IdeaProjects/java_flink/flink_connector/src/main/resources/sensor_partitioned/',
              |  'format' = 'csv'
              |)""".stripMargin)
        tEnv.executeSql("select * from sensorTable").print()
        env.execute()
    }

}
