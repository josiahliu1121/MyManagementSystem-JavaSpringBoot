package com.skytakeaway.server.mapper;

import com.skytakeaway.pojo.entity.SKU;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SKUMapper {
    @Insert("INSERT INTO sku (sku_name, spu_id, is_available, price, weight, description, sku_default_img, sku_properties_value_list, sku_sale_attr_value_list, category1_id, category2_id, category3_id) " +
            "VALUES (#{skuName}, #{spuId}, #{isAvailable}, #{price}, #{weight}, #{description}, #{skuDefaultImg}, #{skuPropertiesValueList}, #{skuSaleAttrValueList}, #{category1Id}, #{category2Id}, #{category3Id})")
    void insertSku(SKU sku);

    @Select("SELECT * FROM sku WHERE spu_id = #{spuId}")
    List<SKU> getBySpuId(Long spuId);

    @Select("SELECT * FROM sku")
    List<SKU> pageQuery();

    @Select("SELECT * FROM sku WHERE id = #{id}")
    SKU getById(Long id);

    @Update("update sku set is_available = #{newIsAvailable} where id = #{id}")
    void changeIsAvailable(int newIsAvailable, Long id);

    @Delete("delete from sku where id = #{id}")
    void deleteById(Long id);

    @Update("UPDATE sku " +
            "SET sku_name = #{skuName}, " +
            "spu_id = #{spuId}, " +
            "is_available = #{isAvailable}, " +
            "price = #{price}, " +
            "weight = #{weight}, " +
            "description = #{description}, " +
            "sku_default_img = #{skuDefaultImg}, " +
            "sku_properties_value_list = #{skuPropertiesValueList}, " +
            "sku_sale_attr_value_list = #{skuSaleAttrValueList}, " +
            "category1_id = #{category1Id}, " +
            "category2_id = #{category2Id}, " +
            "category3_id = #{category3Id} " +
            "WHERE id = #{id}")
    void updateSku(SKU sku);
}
