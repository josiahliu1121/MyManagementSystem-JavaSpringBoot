package com.skytakeaway.server.controller;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.dto.SaleAttrDTO;
import com.skytakeaway.pojo.dto.SpuDTO;
import com.skytakeaway.pojo.entity.BaseSaleAttr;
import com.skytakeaway.pojo.entity.SPU;
import com.skytakeaway.pojo.entity.SpuImage;
import com.skytakeaway.server.service.SPUService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Tag(name = "spu")
@RestController
@RequestMapping("/goods/spu")
public class SPUController {
    @Autowired
    private SPUService spuService;

    @Operation(summary = "page query")
    @GetMapping("pageQuery/{page}/{pageSize}")
    public Result<PageResult<SPU>> pageQuery(@PathVariable int page, @PathVariable int pageSize, @RequestParam Long category3Id) {
        return Result.success(spuService.pageQuery(page, pageSize, category3Id));
    }

    @Operation(summary = "base sale attr list")
    @GetMapping("/baseSaleAttr")
    public Result<List<BaseSaleAttr>> getBaseSaleAttr(){
        return Result.success(spuService.getBaseSaleAttr());
    }

    @Operation(summary = "get sale attr")
    @GetMapping("/saleAttrList/{spuId}")
    public Result<List<SaleAttrDTO>> getSaleAttrList(@PathVariable Long spuId){
        return Result.success(spuService.getSaleAttrList(spuId));
    }

    @Operation(summary = "get spu image")
    @GetMapping("/imageList/{spuId}")
    public Result<List<SpuImage>> getSpuImage(@PathVariable Long spuId){
        return Result.success(spuService.getSpuImage(spuId));
    }

    @Operation(summary = "add spu")
    @PutMapping("/add")
    public Result<Void> addSpu(@RequestBody SpuDTO spuDTO){
        spuService.addSpu(spuDTO);
        return Result.success();
    }

    @Operation(summary = "update spu")
    @PostMapping("/update")
    public Result<Void> updateSpu(@RequestBody SpuDTO spuDTO){
        spuService.updateSpu(spuDTO);
        return Result.success();
    }

    @Operation(summary = "delete spu")
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteSpu(@PathVariable Long id){
        spuService.deleteSpu(id);
        return Result.success();
    }
}
