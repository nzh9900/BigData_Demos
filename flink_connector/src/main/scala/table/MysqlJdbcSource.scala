package table

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-24 16:44
 * */
object MysqlJdbcSource {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val settings = EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build()
        val tEnv = StreamTableEnvironment.create(env, settings)
        tEnv.executeSql(
            """CREATE TABLE sensorTable (
              |     name String,
              |     ts BigInt,
              |     temp double
              | ) WITH (
              |  'connector' = 'jdbc',
              |  'url' = 'jdbc:mysql://localhost:3306/demo',
              |  'table-name' = 'sensor_table',
              |  'username' = 'root',
              |  'password' = '123321'
              |)""".stripMargin)
        tEnv.executeSql("select * from sensorTable").print()
        env.execute()
    }

}
