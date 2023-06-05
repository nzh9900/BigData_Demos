package learning.spring.binarytea.controller.demo;

import learning.spring.binarytea.properties.BinaryTeaProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName MyController
 * @Description
 * @Author zihao.ni
 * @Date 2023/5/31 08:10
 * @Version 1.0
 **/
@RestController
public class MyController {
    @Resource
    private BinaryTeaProperties binaryTeaProperties;

    @GetMapping("/getBinargTeaProperties")
    public BinaryTeaProperties getBinaryTeaProperties() {
        return binaryTeaProperties;
    }
}