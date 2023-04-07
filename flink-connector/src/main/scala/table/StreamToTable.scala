package table

import org.apache.flink.streaming.api.scala.{ StreamExecutionEnvironment, createTypeInformation }
import org.apache.flink.table.api._

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-26 15:46
 * */
object StreamToTable {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val tEnv = Before.init(env)
        val dataStream = env.readTextFile("D:\\IdeaProjects\\java_flink\\flink_connector\\src\\main\\resources\\wordcount.txt")
        val resultStream = dataStream
            .flatMap(_.split(","))
            .map((_, 1))
            .keyBy(value => value._1)
            .sum(1)
            .map(value => new WordCount(value._1, value._2 * 100))
        //tEnv.registerDataStream("t1", resultStream)
        //tEnv.createTemporaryView("demo.t1", resultStream)
        tEnv.registerDataStream("demo.t1",resultStream)
        val table = tEnv.scan("demo.t1")
        table.select($"word", $"count").execute().print()
        env.execute()

    }

    case class WordCount(word: String, count: Int)
}
