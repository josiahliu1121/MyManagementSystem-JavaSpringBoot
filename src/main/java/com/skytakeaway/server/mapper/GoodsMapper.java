package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("select * from brand order by id ASC")
    List<Brand> pageQuery();

    @Insert("insert into brand (name, logo_url) VALUES (#{name}, #{logoUrl})")
    void addBrand(Brand brand);

    @Update("UPDATE brand SET name = #{name}, logo_url = #{logoUrl} WHERE id = #{id}")
    void updateBrand(Brand brand);

    @Delete("delete from brand where id = #{id}")
    void deleteById(Long id);
}
