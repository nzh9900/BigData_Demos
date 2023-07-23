package com.ni.mybatisdemo;

import com.ni.mybatisdemo.entity.Man;
import com.ni.mybatisdemo.mapper.ManMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
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

    @Test
    public void testSearchValWithIf() {
        List<Man> result = manMapper.searchListWithIfCondition("王", "g");
        System.out.println(result);
    }

    @Test
    public void testSearchValWithWhere() {
        List<Man> result = manMapper.searchListWithWhereCondition("", "g");
        System.out.println(result);
    }

    @Test
    public void testSearchWithChoose() {
        List<Man> result = manMapper.searchListWithChoose("王", "");
        System.out.println(result);
    }

    @Test
    public void testSearchWithForeach() {
        List<String> names = Arrays.asList("王五", "李斯");
        List<Man> result = manMapper.searchListWithForeach(names);
        System.out.println(result);
    }

    @Test
    public void testInsertBatch() {
        Man man1 = new Man(null, "name-abc", 10, "wer456");
        Man man2 = new Man(null, "name-iop", 19, "uio890");
        List<Man> men = Arrays.asList(man1, man2);
        manMapper.insertBatch(men);
    }

    @Test
    public void testSelectAllWithInclude() {
        List<Man> men = manMapper.selectAll();
        men.forEach(System.out::println);
    }
}