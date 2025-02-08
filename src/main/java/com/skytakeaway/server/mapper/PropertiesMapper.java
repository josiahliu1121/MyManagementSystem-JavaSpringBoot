package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.Properties;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PropertiesMapper {
    @Select("select * from properties where category_id = #{categoryId} and category_level = #{categoryLevel}")
    List<Properties> getByCategoryIdAndLevel (Long categoryId, int categoryLevel);

    @Update("update properties set prop_name = #{propName}, category_id = #{categoryId}, category_level = #{categoryLevel} where id = #{id}")
    void updateProperties(Properties properties);

    @Insert("insert into properties (prop_name, category_id, category_level) VALUES (#{propName}, #{categoryId},#{categoryLevel})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Properties properties);

    @Delete("delete from properties where id = #{id}")
    void deleteById(Long id);
}
