package com.skytakeaway.pojo.dto;

import com.skytakeaway.pojo.entity.SaleAttrValue;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaleAttrDTO implements Serializable {
    private Long id;
    private Long spuId;
    private Long baseSaleAttrId;
    private String saleAttrName;
    private List<SaleAttrValue> saleAttrValueList;
}
