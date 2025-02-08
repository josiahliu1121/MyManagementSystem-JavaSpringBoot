package com.skytakeaway.server.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.SkuDTO;
import com.skytakeaway.pojo.entity.SKU;
import com.skytakeaway.pojo.entity.SPU;
import com.skytakeaway.pojo.entity.SkuPropertiesValue;
import com.skytakeaway.pojo.entity.SkuSaleAttrValue;
import com.skytakeaway.server.mapper.SKUMapper;
import com.skytakeaway.server.service.SKUService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SKUServiceImpl implements SKUService {
    @Autowired
    private SKUMapper skuMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addOrUpdateSku(SkuDTO skuDTO) throws JsonProcessingException {
        // Serialize lists to JSON
        String propertiesJson = objectMapper.writeValueAsString(skuDTO.getSkuPropertiesValueList());
        String saleAttrJson = objectMapper.writeValueAsString(skuDTO.getSkuSaleAttrValueList());

        //convert SkuDTO to sku
        SKU sku = new SKU();
        BeanUtils.copyProperties(skuDTO,sku);
        sku.setSkuPropertiesValueList(propertiesJson);
        sku.setSkuSaleAttrValueList(saleAttrJson);

        //determine it is update or add
        if(sku.getId() != null){
            skuMapper.updateSku(sku);
        }else {
            skuMapper.insertSku(sku);
        }
    }

    @Override
    public List<SkuDTO> getSkuBySpuId(Long spuId) {
        List<SKU> skuList = skuMapper.getBySpuId(spuId);

        return skuList.stream()
                .map(sku -> {
                    try {
                        return convertSku(sku);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to convert SKU: " + sku.getId(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SkuDTO> pageQuery(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);

        List<SKU> skuList = skuMapper.pageQuery();

        PageInfo<SKU> pageInfo = new PageInfo<>(skuList);

        //convert sku to skuDTO
        List<SkuDTO> skuDTOList = pageInfo.getList().stream()
                .map(sku -> {
                    try {
                        return convertSku(sku);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to convert SKU: " + sku.getId(), e);
                    }
                })
                .collect(Collectors.toList());

        return new PageResult<>(
                skuDTOList,          // Data
                pageInfo.getTotal()         // Total records
        );
    }

    @Override
    public void changeIsAvailable(Long id) {
        //get is available form database
        SKU sku = skuMapper.getById(id);

        //update the data
        // 0 disable, 1 enable
        int newIsAvailable = (sku.getIsAvailable() == 0) ? 1 : 0;
        skuMapper.changeIsAvailable(newIsAvailable, id);
    }

    @Override
    public void deleteSku(Long id) {
        skuMapper.deleteById(id);
    }

    private SkuDTO convertSku(SKU sku) throws JsonProcessingException {
        SkuDTO skuDTO = new SkuDTO();
        BeanUtils.copyProperties(sku, skuDTO);

        // Deserialize JSON strings back into lists
        if (sku.getSkuPropertiesValueList() != null) {
            List<SkuPropertiesValue> propertiesList = objectMapper.readValue(
                    sku.getSkuPropertiesValueList(), new TypeReference<List<SkuPropertiesValue>>() {});
            skuDTO.setSkuPropertiesValueList(propertiesList);
        }

        if (sku.getSkuSaleAttrValueList() != null) {
            List<SkuSaleAttrValue> saleAttrList = objectMapper.readValue(
                    sku.getSkuSaleAttrValueList(), new TypeReference<List<SkuSaleAttrValue>>() {});
            skuDTO.setSkuSaleAttrValueList(saleAttrList);
        }

        return skuDTO;
    }
}
