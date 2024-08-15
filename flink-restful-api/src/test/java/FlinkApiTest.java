import com.ni.flink.api.FlinkApi;
import com.ni.flink.api.pojo.FlinkOverview;
import com.ni.flink.api.pojo.MemorySize;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

/**
 * @ClassName FlinkApiTest
 * @Description
 * @Author zihao.ni
 * @Date 2024/2/20 13:55
 * @Version 1.0
 **/

public class FlinkApiTest {
    private FlinkApi flinkApi;

    @Before
    public void init() {
        flinkApi = new FlinkApi("http://node24.test.com:8088/proxy/application_1723101616384_0734/");
    }

    @Test
    public void testTaskState() throws Exception {
        //String jobState = flinkApi.getJobState("0ea19b3c1867bdb54177ddc7ba191fcd");
        //System.out.println(jobState);
    }

    @Test
    public void testGetJobManagerConfig() {
        Hashtable jobManagerConfig = flinkApi.getJobManagerConfig();
        System.out.println(jobManagerConfig.get("jobmanager.memory.process.size"));
        Assert.assertNotNull(jobManagerConfig.get("jobmanager.memory.process.size"));
    }

    @Test
    public void testGetTaskManagerConfig() {
        Hashtable jobManagerConfig = flinkApi.getJobManagerConfig();
        System.out.println(jobManagerConfig.get("taskmanager.memory.process.size"));
        Assert.assertNotNull(jobManagerConfig.get("taskmanager.memory.process.size"));
    }

    @Test
    public void testGetJobManagerMemory() {
        System.out.println(flinkApi.getJobManagerMemory());
    }

    @Test
    public void testGetOverview() {
        FlinkOverview overview = flinkApi.getOverview();
        System.out.println(overview);
        Assert.assertNotNull(overview);
    }

    @Test
    public void testGetTaskManagerTotalMemory() {
        MemorySize memorySize = flinkApi.getTaskManagerTotalMemory();
        System.out.println(memorySize.getMebiBytes());
        Assert.assertNotNull(memorySize);
    }
}