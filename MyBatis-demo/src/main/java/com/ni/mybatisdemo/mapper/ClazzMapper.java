package com.ni.mybatisdemo.mapper;

import com.ni.mybatisdemo.entity.Clazz;

/**
 * @ClassName ClassMapper
 * @Description
 * @Author zihao.ni
 * @Date 2023/7/24 22:06
 * @Version 1.0
 **/
public interface ClazzMapper {
    Clazz selectById(long id);
}