package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.SPU;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SPUMapper {

    @Select("select * from spu where category3_id = #{category3Id} order by id ASC")
    List<SPU> pageQuery(Long category3Id);

    @Update("update spu set spu_name=#{spuName}, description=#{description}, category3_id=#{category3Id}, brand_id=#{brandId} where id=#{id}")
    void updateById(SPU spu);

    @Insert("INSERT INTO spu (spu_name, description, category3_id, brand_id) VALUES (#{spuName}, #{description}, #{category3Id}, #{brandId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertSpu(SPU spu);

    @Select("select * from spu where brand_id = #{brandId}")
    SPU selectByBrandId(Long brandId);

    @Delete("delete from spu where id = #{id}")
    void deleteById(Long id);
}
