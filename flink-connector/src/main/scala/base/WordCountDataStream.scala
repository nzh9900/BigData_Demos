package base

import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.{ StreamExecutionEnvironment, createTypeInformation }

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-23 12:46
 * */
object WordCountDataStream {
    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        val inputStream = env.socketTextStream("hadoop102", 9876)

        val value = inputStream
            .flatMap(_.split(","))
            .map(x => (x.split("-")(0), x.split("-")(1).toInt))
            .keyBy(_._1)

        inputStream.addSink(new SinkFunction[String] {})

        //.reduce((a, b) => if(a._1.contains("a")) (a._1, a._2 + b._2) else (a._1, a._2 + a._2 * 100))

        value.print()
        env.execute()

    }

}
