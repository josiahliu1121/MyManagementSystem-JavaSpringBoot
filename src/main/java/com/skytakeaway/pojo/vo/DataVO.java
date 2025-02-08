package com.skytakeaway.pojo.vo;

import com.skytakeaway.pojo.entity.PropertiesValue;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DataVO {
    private int sexRatio;
    private List<Integer> ageRatio;
    private List<Integer> predictedPlayerCount;
    private List<Integer> reviewCount;
    private List<Integer> saleCount1;
    private List<Integer> saleCount2;
    private List<Integer> downloadCount;
}
