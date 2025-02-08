package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.SaleAttr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SaleAttrMapper {
    @Select("select * from sale_attr where spu_id = #{spuId}")
    List<SaleAttr> getBySpuId(Long spuId);

    @Delete("delete from sale_attr where spu_id = #{spuId}")
    void deleteBySpuId(Long spuId);

    void batchInsert(List<SaleAttr> saleAttrList);
}
