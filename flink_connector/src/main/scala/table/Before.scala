package table

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

/**
 * program: java_flink
 * description:
 * author: Ni
 * create: 2021-11-25 13:56
 * */
object Before {
    def init(env: StreamExecutionEnvironment): StreamTableEnvironment = {
        env.setParallelism(1)
        val settings = EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build()
        StreamTableEnvironment.create(env, settings)
    }
}
