package com.skytakeaway.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.dto.SkuDTO;
import com.skytakeaway.pojo.vo.DataVO;
import com.skytakeaway.server.service.DataService;
import com.skytakeaway.server.service.SKUService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Tag(name = "data")
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @Operation(summary = "get data")
    @GetMapping("/initialData")
    public Result<DataVO> initialData (){
        return Result.success(dataService.initialData());
    }
}
