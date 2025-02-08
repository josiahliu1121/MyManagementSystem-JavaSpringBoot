package com.skytakeaway.pojo.vo;

import com.skytakeaway.pojo.entity.PropertiesValue;
import lombok.Data;

import java.util.List;

@Data
public class PropertiesVO {
    private Long id;
    private String propName;
    private Long categoryId;
    private Long categoryLevel;
    private List<PropertiesValue> propValueList;
}
