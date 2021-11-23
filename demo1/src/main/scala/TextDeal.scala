import java.io.{ BufferedReader, FileReader }
import scala.collection.mutable

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-17 15:00
 * */
object TextDeal {
    def main(args: Array[String]): Unit = {
        val tagFileReader = new FileReader("D:\\IdeaProjects\\java_flink\\demo1\\src\\main\\resources\\人群标签.txt")
        val tagsBufferReader = new BufferedReader(tagFileReader)
        var line = ""
        val tags = new mutable.HashSet[String]()

        //while(true)  {
        //    println(line)
        //    val tagsInLine =
        //        line
        //            .split("\\|")(1)
        //            .split(",")
        //    tagsInLine.foreach(tags.add)
        //}
        println(tags.mkString(","))
    }
}
