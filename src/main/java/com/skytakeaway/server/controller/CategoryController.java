package com.skytakeaway.server.controller;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.entity.Brand;
import com.skytakeaway.pojo.entity.Category1;
import com.skytakeaway.pojo.entity.Category2;
import com.skytakeaway.pojo.entity.Category3;
import com.skytakeaway.server.service.CategoryService;
import com.skytakeaway.server.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category")
@RestController()
@RequestMapping("/goods")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Operation(summary = "get category 1")
    @GetMapping("/getCategory1")
    public Result<List<Category1>> getCategory1 (){
        return Result.success(categoryService.getCategory1());
    }

    @Operation(summary = "get category 2")
    @GetMapping("/getCategory2/{category1Id}")
    public Result<List<Category2>> getCategory2 (@PathVariable Long category1Id){
        return Result.success(categoryService.getCategory2(category1Id));
    }

    @Operation(summary = "get category 3")
    @GetMapping("/getCategory3/{category2Id}")
    public Result<List<Category3>> getCategory3 (@PathVariable Long category2Id){
        return Result.success(categoryService.getCategory3(category2Id));
    }
}
