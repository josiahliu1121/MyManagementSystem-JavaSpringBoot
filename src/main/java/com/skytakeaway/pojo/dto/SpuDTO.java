package com.skytakeaway.pojo.dto;

import com.skytakeaway.pojo.entity.SpuImage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SpuDTO implements Serializable {
    private Long id;
    private String spuName;
    private String description;
    private Long category3Id;
    private Long brandId;
    private List<SaleAttrDTO> spuSaleAttrList;
    private List<SpuImage> spuImageList;
}
