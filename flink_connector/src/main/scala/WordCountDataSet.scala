import org.apache.flink.api.common.functions.FilterFunction
import org.apache.flink.api.scala.{ ExecutionEnvironment, createTypeInformation }
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-23 10:47
 * */
object WordCountDataSet {
    def main(args: Array[String]): Unit = {
        val env = ExecutionEnvironment.getExecutionEnvironment
        val inpath = "D:\\IdeaProjects\\java_flink\\flink_connector\\src\\main\\resources\\wordcount.txt"
        val inputDS = env.readTextFile(inpath)


        val resultDS = inputDS
            .flatMap(_.split(","))
            .map((_, 1))
            .groupBy(0).sum(1)

        resultDS.print()



    }


}
