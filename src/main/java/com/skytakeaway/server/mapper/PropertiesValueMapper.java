package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.PropertiesValue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PropertiesValueMapper {
    @Select("select * from properties_value where prop_id = #{propId}")
    List<PropertiesValue> getByPropId(Long propId);

    @Delete("delete from properties_value where prop_id = #{propId}")
    void deleteByPropId(Long propId);

    void batchInsert(List<PropertiesValue> propValueList);
}
