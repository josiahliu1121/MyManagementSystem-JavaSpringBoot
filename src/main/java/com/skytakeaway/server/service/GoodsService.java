package com.skytakeaway.server.service;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.entity.Brand;

import java.util.List;

public interface GoodsService {
    PageResult<Brand> pageQuery(int pageNumber, int pageSize);

    void addBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteBrand(Long id);

    List<Brand> getBrandList();
}
