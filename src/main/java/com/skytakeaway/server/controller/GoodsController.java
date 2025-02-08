package com.skytakeaway.server.controller;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.entity.Brand;
import com.skytakeaway.server.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Goods")
@RestController()
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


    @Operation(summary = "brand page query")
    @GetMapping("/pageQuery/{pageNumber}/{pageSize}")
    public Result<PageResult<Brand>> pageQuery(@PathVariable int pageNumber, @PathVariable int pageSize){
        PageResult<Brand> result = goodsService.pageQuery(pageNumber,pageSize);
        return Result.success(result);
    }

    @Operation(summary = "add brand")
    @PutMapping("/save")
    public Result<Void> addBrand(@RequestBody Brand brand){
        goodsService.addBrand(brand);
        return Result.success();
    }

    @Operation(summary = "update brand")
    @PostMapping("/update")
    public Result<Void> updateBrand(@RequestBody Brand brand){
        goodsService.updateBrand(brand);
        return Result.success();
    }

    @Operation(summary = "delete brand")
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteBrand(@PathVariable Long id){
        goodsService.deleteBrand(id);
        return Result.success();
    }

    @Operation(summary = "brand list")
    @GetMapping("/brand")
    public Result<List<Brand>> getBrandList(){
        return Result.success(goodsService.getBrandList());
    }
}
