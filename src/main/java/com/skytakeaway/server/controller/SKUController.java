package com.skytakeaway.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.dto.SkuDTO;
import com.skytakeaway.server.service.SKUService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Tag(name = "sku")
@RestController
@RequestMapping("/goods/sku")
public class SKUController {
    @Autowired
    private SKUService skuService;

    @Operation(summary = "add or update sku")
    @PutMapping("/addOrUpdateSku")
    public Result<Void> addOrUpdateSku(@RequestBody SkuDTO skuDTO) throws JsonProcessingException {
        skuService.addOrUpdateSku(skuDTO);
        return Result.success();
    }

    @Operation(summary = "get sku by spu id")
    @GetMapping("/getSku/{spuId}")
    public Result<List<SkuDTO>> getSkuBySpuId(@PathVariable Long spuId){
        return Result.success(skuService.getSkuBySpuId(spuId));
    }

    @Operation(summary = "page query")
    @GetMapping("/pageQuery/{pageNumber}/{pageSize}")
    public Result<PageResult<SkuDTO>> skuPageQuery(@PathVariable int pageNumber, @PathVariable int pageSize){
        return Result.success(skuService.pageQuery(pageNumber, pageSize));
    }

    @Operation(summary = "change sku status")
    @PostMapping("/changeIsAvailable/{id}")
    public Result<Void> changeIsAvailable(@PathVariable Long id){
        skuService.changeIsAvailable(id);
        return Result.success();
    }

    @Operation(summary = "delete sku")
    @DeleteMapping("/deleteSku/{id}")
    public Result<Void> deleteSku (@PathVariable Long id){
        skuService.deleteSku(id);
        return Result.success();
    }
}
