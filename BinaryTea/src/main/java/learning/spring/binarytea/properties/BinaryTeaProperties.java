package learning.spring.binarytea.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName BinaryTeaProperties
 * @Description
 * @Author zihao.ni
 * @Date 2023/5/29 22:21
 * @Version 1.0
 **/
@Data
@ConfigurationProperties(prefix = "binarytea")
public class BinaryTeaProperties {
    private boolean ready;
    private String openHours;
}