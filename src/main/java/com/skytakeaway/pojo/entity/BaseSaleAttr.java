package com.skytakeaway.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseSaleAttr {
    private Long id;
    private String name;

    public static List<BaseSaleAttr> getBaseSaleAttrList(){
        List<BaseSaleAttr> result = new ArrayList<>();

        result.add(new BaseSaleAttr(1L, "color"));
        result.add(new BaseSaleAttr(2L, "version"));
        result.add(new BaseSaleAttr(3L, "size"));

        return result;
    }
}
