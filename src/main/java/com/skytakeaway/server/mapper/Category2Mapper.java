package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.Category2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Category2Mapper {
    @Select("select * from category2 where category1_id = #{category1Id}")
    List<Category2> getByCategory1Id(Long category1Id);
}
