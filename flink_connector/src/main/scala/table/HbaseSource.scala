package table

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment


/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-25 13:09
 * */
object HbaseSource {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val tEnv = Before.init(env)
        tEnv.executeSql(
            """CREATE TABLE hTable (
              | rowkey INT,
              | cf ROW<age Int,name String,level String>,
              | PRIMARY KEY (rowkey) NOT ENFORCED
              |) WITH (
              | 'connector' = 'hbase-2.2',
              | 'table-name' = 'u_analysis_prod:demo',
              | 'zookeeper.quorum' = '10.24.69.3:2181'
              |)""".stripMargin)
        tEnv.executeSql("select rowkey,cf.age,cf.name from hTable").print()
        env.execute()
    }
}
