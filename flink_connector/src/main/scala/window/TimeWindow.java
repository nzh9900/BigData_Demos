package window;

import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

public class TimeWindow {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> socketStream = env.socketTextStream("hadoop102", 9876, ',', 10);
        socketStream.timeWindowAll(Time.seconds(16));
    }
}
