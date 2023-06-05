package learning.spring.binarytea;

import learning.spring.binarytea.properties.BinaryTeaProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootTest(classes = BinaryTeaProperties.class, properties = {
        "binarytea.ready=true",
        "binarytea.openHours=7:00-10:00"
})
class BinaryTeaApplicationTests {
    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void testPropertyBeanAvailable() {
        if (applicationContext.getBean(BinaryTeaProperties.class) == null) {

        }

    }


}
