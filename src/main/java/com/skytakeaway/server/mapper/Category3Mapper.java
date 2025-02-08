package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.Category3;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Category3Mapper {
    @Select("select * from category3 where category2_id = #{category2Id}")
    List<Category3> getByCategory2Id(Long category2Id);
}
