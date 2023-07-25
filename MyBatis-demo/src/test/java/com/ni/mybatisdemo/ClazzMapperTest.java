package com.ni.mybatisdemo;

import com.ni.mybatisdemo.mapper.ClazzMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName ClassMapperTest
 * @Description
 * @Author zihao.ni
 * @Date 2023/7/24 22:13
 * @Version 1.0
 **/
@SpringBootTest(classes = MyBatisDemoApplication.class)
@RunWith(SpringRunner.class)
public class ClazzMapperTest {
    @Resource
    private ClazzMapper clazzMapper;

    @Test
    public void testSelectById() {
        System.out.println(clazzMapper.selectById(1));
    }

}