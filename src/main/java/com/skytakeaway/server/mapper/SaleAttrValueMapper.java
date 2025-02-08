package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.SaleAttrValue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SaleAttrValueMapper {
    @Select("select * from  sale_attr_value where spu_id = #{spuId}")
    List<SaleAttrValue> getBySpuId(Long spuId);

    @Delete("delete from sale_attr_value where spu_id = #{spuId}")
    void deleteBySpuId(Long id);

    void batchInsert(List<SaleAttrValue> saleAttrValueList);
}
