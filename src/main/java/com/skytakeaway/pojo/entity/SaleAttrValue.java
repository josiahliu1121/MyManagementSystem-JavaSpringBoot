package com.skytakeaway.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleAttrValue {
    private Long id;
    private Long spuId;
    private Long baseSaleAttrId;
    private String saleAttrValueName;
    private String saleAttrName;
}
