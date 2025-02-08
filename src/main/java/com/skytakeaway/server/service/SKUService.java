package com.skytakeaway.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.SkuDTO;

import java.util.List;

public interface SKUService {
    void addOrUpdateSku(SkuDTO skuDTO) throws JsonProcessingException;

    List<SkuDTO> getSkuBySpuId(Long spuId);

    PageResult<SkuDTO> pageQuery(int pageNumber, int pageSize);

    void changeIsAvailable(Long id);

    void deleteSku(Long id);
}
