import com.ni.flink.api.FlinkAPI;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName FlinkApiTest
 * @Description
 * @Author zihao.ni
 * @Date 2024/2/20 13:55
 * @Version 1.0
 **/

public class FlinkApiTest {
    private FlinkAPI flinkApi;

    @Before
    public void init() {
        flinkApi = new FlinkAPI("node24.test.com:8088/proxy/application_1695812289210_76798");
    }

    @Test
    public void testTaskState() throws Exception {
        //String jobState = flinkApi.getJobState("0ea19b3c1867bdb54177ddc7ba191fcd");
        //System.out.println(jobState);
    }
}