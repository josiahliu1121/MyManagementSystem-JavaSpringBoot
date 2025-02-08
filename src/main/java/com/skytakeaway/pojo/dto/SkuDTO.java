package com.skytakeaway.pojo.dto;

import com.skytakeaway.pojo.entity.SkuPropertiesValue;
import com.skytakeaway.pojo.entity.SkuSaleAttrValue;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SkuDTO implements Serializable {
    private Long id;
    private String skuName;
    private Long spuId;
    private int isAvailable; // 0 disable, 1 enable
    private int price;
    private int weight;
    private String description;
    private String skuDefaultImg;
    //store this two list as JSON
    private List<SkuPropertiesValue> skuPropertiesValueList;
    private List<SkuSaleAttrValue> skuSaleAttrValueList;
    private Long category1Id;
    private Long category2Id;
    private Long category3Id;
}
