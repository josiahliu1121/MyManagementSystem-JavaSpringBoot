package com.skytakeaway.server.service;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.SaleAttrDTO;
import com.skytakeaway.pojo.dto.SpuDTO;
import com.skytakeaway.pojo.entity.BaseSaleAttr;
import com.skytakeaway.pojo.entity.SPU;
import com.skytakeaway.pojo.entity.SpuImage;

import java.util.List;

public interface SPUService {
    PageResult<SPU> pageQuery(int page, int pageSize, Long category3Id);

    List<BaseSaleAttr> getBaseSaleAttr();

    List<SaleAttrDTO> getSaleAttrList(Long spuId);

    List<SpuImage> getSpuImage(Long spuId);

    void updateSpu(SpuDTO spuDTO);

    void addSpu(SpuDTO spuDTO);

    void deleteSpu(Long id);
}
