package learning.spring.binarytea.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName NumberAop
 * @Description
 * @Author zihao.ni
 * @Date 2023/8/2 17:40
 * @Version 1.0
 **/
@Component
@Aspect
@Slf4j
@Order(1)
public class AccessTimeAspect {
    @Pointcut("target(learning.spring.binarytea.controller.demo.AopController)")
    public void pointCut() {
    }


    @Before("pointCut() && args(message)")
    public void addMessage(StringBuilder message) {
        log.info("accessed aspect");
        message.append("---").append(System.currentTimeMillis());
    }
}