package com.ni.mybatisdemo.mapper;

import com.ni.mybatisdemo.entity.Man;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @ClassName ManMapper
 * @Description
 * @Author zihao.ni
 * @Date 2023/7/21 07:47
 * @Version 1.0
 **/
@Mapper
public interface ManMapper {
    Man getManById(int id);

    void insert(Man man);

    void delete(long id);

    void update(Man man);

    /**
     * name作为key,man对象作为value
     * @return
     */
    @MapKey("name")
    Map<String, Man> selectAllAsMap();
}