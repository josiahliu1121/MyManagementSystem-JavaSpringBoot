package com.skytakeaway.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SPU {
    private Long id;
    private String spuName;
    private String description;
    private Long category3Id;
    private Long brandId;
}
