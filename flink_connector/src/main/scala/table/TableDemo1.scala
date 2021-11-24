package table

import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{ StreamExecutionEnvironment, createTypeInformation }
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.Expressions.$
import org.apache.flink.table.api.{ EnvironmentSettings, FieldExpression, WithOperations }
import org.apache.flink.table.api.bridge.scala.{ StreamTableEnvironment, tableConversions }
import org.apache.flink.table.api._

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-23 17:22
 * */
object TableDemo1 {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)
        val settings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tEnv = StreamTableEnvironment.create(env, settings)
        val inputStream =
            env.readTextFile("D:\\IdeaProjects\\java_flink\\flink_connector\\src\\main\\resources\\wordcount.txt")
        val dataStream = inputStream.map(data => {
            val dataArray = data.split(",")
            Person(dataArray(0), dataArray(1).toInt)
        }
        )
        val dataTable = tEnv.fromDataStream[Person](dataStream)
        val selectedTable = dataTable.select($("name"), $("orderCounts")).filter($"name" === "aa")
        val selectedStream = selectedTable.toAppendStream[Person]
        selectedStream.print("table1")


        val dataTable2 = tEnv.fromDataStream(dataStream, 'name, 'orderCounts)
        val selectTable2 = dataTable2.select($"name", $"orderCounts")
        selectTable2.toAppendStream[(String, Int)].print("table2")
        env.execute()
    }

    case class Person(name: String, orderCounts: Int)
}

object TableDemo2 {
    // 统计每 10 秒中每个传感器温度值的个数
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val tableEnvSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tEnv = StreamTableEnvironment.create(env, tableEnvSettings)
        StreamTableEnvironment.create(env)
        val inputStream = env.readTextFile("D:\\IdeaProjects\\java_flink\\flink_connector\\src\\main\\resources\\sensor.txt")
        env.setParallelism(1)
        val sourceStream = inputStream
            .map(row => {
                val sensor = row.split(",")
                Sensor(sensor(0), sensor(1).toLong, sensor(2).toDouble)
            })
            .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[Sensor](Time.seconds(1)) {
                override def extractTimestamp(t: Sensor) = t.messageTime * 1000L
            })
        val sourceTable = tEnv.fromDataStream(sourceStream, 'id, 'temperature, 'messageTime.rowtime() as 'ts)
        val resultTable = sourceTable.window(Tumble over 10.seconds() on 'ts as 'tw)
            .groupBy('id, 'tw)
            .select('id, 'id.count)
        resultTable.toRetractStream[(String, Long)].print()

        env.execute()
    }

    case class Sensor(id: String, messageTime: Long, temperature: Double)
}

