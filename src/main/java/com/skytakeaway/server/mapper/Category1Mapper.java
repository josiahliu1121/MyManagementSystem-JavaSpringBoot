package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.Category1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Category1Mapper {
    @Select("select * from category1")
    List<Category1> getAllCategory();
}
