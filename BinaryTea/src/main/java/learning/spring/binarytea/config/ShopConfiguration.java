package learning.spring.binarytea.config;

import learning.spring.binarytea.properties.BinaryTeaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ShopConfiguration
 * @Description
 * @Author zihao.ni
 * @Date 2023/5/29 22:20
 * @Version 1.0
 **/
@Configuration
@EnableConfigurationProperties(BinaryTeaProperties.class)
@ConditionalOnProperty(name = "binarytea.ready", havingValue = "true")
public class ShopConfiguration {

}