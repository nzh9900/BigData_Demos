package learning.spring.binarytea.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName AopConfiguration
 * @Description
 * @Author zihao.ni
 * @Date 2023/8/2 17:33
 * @Version 1.0
 **/
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("learning.spring.binarytea.aop")
public class AopConfiguration {
}