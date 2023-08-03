package learning.spring.binarytea.controller.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AopController
 * @Description
 * @Author zihao.ni
 * @Date 2023/8/2 17:49
 * @Version 1.0
 **/
@RestController()
@RequestMapping("/aop")
@Slf4j
public class AopController {

    @GetMapping("/plus")
    public Long plus(
            @RequestParam("value1") Long v1,
            @RequestParam("value2") Long v2
    ) {
        return v1 + v2;
    }

    @GetMapping("/addMessage")
    public String addMessage(@RequestParam("message") StringBuilder message
    ) {
        log.info("message: " + message);
        return message.toString();
    }
}