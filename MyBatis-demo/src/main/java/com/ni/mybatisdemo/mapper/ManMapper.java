package com.ni.mybatisdemo.mapper;

import com.ni.mybatisdemo.entity.Man;
import com.ni.mybatisdemo.entity.ManWithOrder;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ManMapper
 * @Description
 * @Author zihao.ni
 * @Date 2023/7/21 07:47
 * @Version 1.0
 **/
public interface ManMapper {
    Man getManById(int id);

    void insert(Man man);

    void delete(long id);

    void update(Man man);

    /**
     * name作为key,man对象作为value
     *
     * @return
     */
    @MapKey("name")
    Map<String, Man> selectAllAsMap();

    List<Man> searchListWithIfCondition(String nameSearchVal, String telephoneSearchVal);

    List<Man> searchListWithWhereCondition(String nameSearchVal, String telephoneSearchVal);

    List<Man> searchListWithChoose(String nameSearchVal, String telephoneSearchVal);

    List<Man> searchListWithForeach(List<String> names);

    void insertBatch(List<Man> men);

    List<Man> selectAll();

    Man selectDetailById(int id);

    ManWithOrder selectOrderDetailsById(int userId);
}