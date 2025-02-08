package com.skytakeaway.server.controller;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.dto.MenuDTO;
import com.skytakeaway.pojo.dto.RoleDTO;
import com.skytakeaway.pojo.entity.Role;
import com.skytakeaway.pojo.vo.MenuVO;
import com.skytakeaway.server.service.MenuService;
import com.skytakeaway.server.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Menu")
@RestController()
@RequestMapping("/user/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Operation(summary = "get all menu")
    @GetMapping("/getAllMenu")
    public Result<List<MenuVO>> getAllMenu(){
        return Result.success(menuService.getAllMenu());
    }

    @Operation(summary = "get menu by role")
    @GetMapping("/getMenu/{roleId}")
    public Result<List<MenuVO>> getMenuByRole(@PathVariable Long roleId){
        return Result.success(menuService.getByRole(roleId));
    }

    @Operation(summary = "add menu")
    @PutMapping("/addMenu")
    public Result<Void> addMenu (@RequestBody MenuDTO menuDTO){
        menuService.addMenu(menuDTO);
        return Result.success();
    }

    @Operation(summary = "add menu")
    @PostMapping("/updateMenu")
    public Result<Void> updateMenu (@RequestBody MenuDTO menuDTO){
        menuService.updateMenu(menuDTO);
        return Result.success();
    }

    @Operation(summary = "delete menu")
    @DeleteMapping("/deleteMenu/{id}")
    public Result<Void> deleteMenu (@PathVariable Long id){
        menuService.deleteMenu(id);
        return Result.success();
    }
}