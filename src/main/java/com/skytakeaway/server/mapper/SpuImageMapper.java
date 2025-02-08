package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.SpuImage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpuImageMapper {
    @Select("select * from spu_image where spu_id = #{spuId}")
    List<SpuImage> getBySpuId(Long spuId);

    @Delete("delete from spu_image where spu_id = #{spuId}")
    void deleteBySpuId(Long spuId);

    void batchInsert(List<SpuImage> spuImageList);
}
