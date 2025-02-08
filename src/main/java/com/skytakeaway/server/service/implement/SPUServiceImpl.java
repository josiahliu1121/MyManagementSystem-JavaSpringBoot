package com.skytakeaway.server.service.implement;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skytakeaway.common.constant.MessageConstant;
import com.skytakeaway.common.exception.SpuUsedException;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.pojo.dto.SaleAttrDTO;
import com.skytakeaway.pojo.dto.SpuDTO;
import com.skytakeaway.pojo.entity.*;
import com.skytakeaway.server.mapper.*;
import com.skytakeaway.server.service.SPUService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SPUServiceImpl implements SPUService {
    @Autowired
    private SPUMapper spuMapper;
    @Autowired
    private SaleAttrMapper saleAttrMapper;
    @Autowired
    private SaleAttrValueMapper saleAttrValueMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SKUMapper skuMapper;

    @Override
    public PageResult<SPU> pageQuery(int page, int pageSize, Long category3Id) {
        PageHelper.startPage(page, pageSize);

        List<SPU> spuList = spuMapper.pageQuery(category3Id);

        PageInfo<SPU> pageInfo = new PageInfo<>(spuList);

        // Build the PageResult (custom object to encapsulate data and metadata)
        return new PageResult<>(
                pageInfo.getList(),          // Data
                pageInfo.getTotal()         // Total records
        );
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttr() {
        return BaseSaleAttr.getBaseSaleAttrList();
    }

    @Override
    public List<SaleAttrDTO> getSaleAttrList(Long spuId) {
        List<SaleAttr> saleAttrList = saleAttrMapper.getBySpuId(spuId);
        List<SaleAttrValue> saleAttrValueList = saleAttrValueMapper.getBySpuId(spuId);
        List<SaleAttrDTO> result = new ArrayList<>();

        // Group saleAttrValueList by baseSaleAttrId for efficient lookup
        Map<Long, List<SaleAttrValue>> saleAttrValueMap = saleAttrValueList.stream()
                .collect(Collectors.groupingBy(SaleAttrValue::getBaseSaleAttrId));

        // Transform saleAttrList to SaleAttrDTO
        saleAttrList.forEach(saleAttr -> {
            SaleAttrDTO saleAttrDTO = new SaleAttrDTO();
            BeanUtils.copyProperties(saleAttr, saleAttrDTO);

            // Get the value list for the current baseSaleAttrId
            List<SaleAttrValue> saleAttrValues = saleAttrValueMap.getOrDefault(saleAttr.getBaseSaleAttrId(), new ArrayList<>());
            saleAttrDTO.setSaleAttrValueList(saleAttrValues);

            result.add(saleAttrDTO);
        });

        return result;
    }

    @Override
    public List<SpuImage> getSpuImage(Long spuId) {
        return spuImageMapper.getBySpuId(spuId);
    }

    @Override
    @Transactional
    public void updateSpu(SpuDTO spuDTO) {
        //update spu -> set to new data
        SPU spu = new SPU();
        BeanUtils.copyProperties(spuDTO, spu);
        spuMapper.updateById(spu);

        //update sale attr -> delete and add
        saleAttrMapper.deleteBySpuId(spuDTO.getId());

        List<SaleAttr> saleAttrList = spuDTO.getSpuSaleAttrList().stream()
                .map(source -> {
                    SaleAttr saleAttr = new SaleAttr();
                    BeanUtils.copyProperties(source, saleAttr);
                    return saleAttr;
                })
                .collect(Collectors.toList());
        saleAttrMapper.batchInsert(saleAttrList);

        //update sale attr value -> delete and add
        saleAttrValueMapper.deleteBySpuId(spuDTO.getId());

        List<SaleAttrValue> saleAttrValueList = new ArrayList<>();
        spuDTO.getSpuSaleAttrList().forEach(saleAttrDTO -> {
            saleAttrValueList.addAll(saleAttrDTO.getSaleAttrValueList());
        });
        saleAttrValueMapper.batchInsert(saleAttrValueList);

        //update spu image -> delete and add
        spuImageMapper.deleteBySpuId(spuDTO.getId());

        spuImageMapper.batchInsert(spuDTO.getSpuImageList());
    }

    @Override
    @Transactional
    public void addSpu(SpuDTO spuDTO) {
        //insert spu (get back id)
        SPU spu = new SPU();
        BeanUtils.copyProperties(spuDTO, spu);
        spuMapper.insertSpu(spu);

        //insert sale attr (set spu id before insert)
        List<SaleAttr> saleAttrList = spuDTO.getSpuSaleAttrList().stream()
                .map(source -> {
                    SaleAttr saleAttr = new SaleAttr();
                    BeanUtils.copyProperties(source, saleAttr);
                    return saleAttr;
                })
                .collect(Collectors.toList());
        saleAttrList.forEach(saleAttr -> saleAttr.setSpuId(spu.getId()));

        saleAttrMapper.batchInsert(saleAttrList);

        //insert sale attr value (set spu id before insert)
        List<SaleAttrValue> saleAttrValueList = new ArrayList<>();
        spuDTO.getSpuSaleAttrList().forEach(saleAttrDTO -> {
            saleAttrValueList.addAll(saleAttrDTO.getSaleAttrValueList());
        });
        saleAttrValueList.forEach(saleAttrValue -> saleAttrValue.setSpuId(spu.getId()));

        saleAttrValueMapper.batchInsert(saleAttrValueList);

        //insert spu image (set spu id before insert)

        spuDTO.getSpuImageList().forEach(spuImage -> spuImage.setSpuId(spu.getId()));


        spuImageMapper.batchInsert(spuDTO.getSpuImageList());
    }

    @Override
    public void deleteSpu(Long id) {
        List<SKU> skuList = skuMapper.getBySpuId(id);

        if(skuList.isEmpty()){
            spuMapper.deleteById(id);
        }else {
            throw new SpuUsedException(MessageConstant.SPU_USED);
        }
    }

}
