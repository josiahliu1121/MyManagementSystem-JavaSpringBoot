package com.skytakeaway.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SKU {
    private Long id;
    private String skuName;
    private Long spuId;
    private int isAvailable; // 0 disable, 1 enable
    private int price;
    private int weight;
    private String description;
    private String skuDefaultImg;
    private String skuPropertiesValueList; //this is json in database
    private String skuSaleAttrValueList; //this is json in database
    private Long category1Id;
    private Long category2Id;
    private Long category3Id;
}
