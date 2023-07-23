package com.ni.mybatisdemo;

import com.ni.mybatisdemo.entity.Man;
import com.ni.mybatisdemo.mapper.ManMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName ManMapperTest
 * @Description
 * @Author zihao.ni
 * @Date 2023/7/21 08:14
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisDemoApplication.class)
public class ManMapperTest {
    @Resource
    private ManMapper manMapper;

    @Test
    public void testSelectById() {
        Man man = manMapper.getManById(1);
        System.out.println(man);
    }

    @Test
    public void testInsert() {
        Man man = new Man(null, "李斯", 40, "q12ascxa");
        manMapper.insert(man);
    }

    @Test
    public void testDelete() {
        manMapper.delete(1);
    }

    @Test
    public void testSelectAllAsMap() {
        Map<String, Man> longManMap = manMapper.selectAllAsMap();
        System.out.println(longManMap.get("李斯"));
    }
}