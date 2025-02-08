package com.skytakeaway.server.controller;

import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.dto.PropertiesDTO;
import com.skytakeaway.pojo.vo.PropertiesVO;
import com.skytakeaway.server.service.PropertiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Properties")
@RestController()
@RequestMapping("/goods")
public class PropertiesController {
    @Autowired
    private PropertiesService propertiesService;

    @Operation(summary = "get properties list")
    @GetMapping("/getPropertiesList/{category1Id}/{category2Id}/{category3Id}")
    public Result<List<PropertiesDTO>> getPropertiesList (@PathVariable Long category1Id, @PathVariable Long category2Id, @PathVariable Long category3Id){
        return Result.success(propertiesService.getPropertiesList(category1Id, category2Id, category3Id));
    }

    @Operation(summary = "save or update properties")
    @PostMapping("/saveOrUpdate")
    public Result<Void> saveOrUpdateProperties (@RequestBody PropertiesVO propertiesVO){
        propertiesService.saveOrUpdateProperties(propertiesVO);
        return  Result.success();
    }

    @Operation(summary = "delete properties")
    @DeleteMapping("/deleteProperties/{id}")
    public Result<Void> deleteProperties (@PathVariable Long id){
        propertiesService.deleteById(id);
        return Result.success();
    }
}
