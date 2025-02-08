package com.skytakeaway.server.controller;

import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.pojo.dto.RoleDTO;
import com.skytakeaway.pojo.entity.Role;
import com.skytakeaway.server.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Role")
@RestController()
@RequestMapping("/user/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Operation(summary = "page query")
    @GetMapping("/pageQuery/{pageNumber}/{pageSize}")
    public Result<PageResult<Role>> pageQuery(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam String roleName){
        return Result.success(roleService.pageQuery(pageNumber, pageSize, roleName));
    }

    @Operation(summary = "add role")
    @PutMapping("/addRole")
    public Result<Void> addRole (@RequestBody RoleDTO roleDTO){
        roleService.addRole(roleDTO);
        return Result.success();
    }

    @Operation(summary = "update role")
    @PostMapping("/updateRole")
    public Result<Void> updateRole (@RequestBody RoleDTO roleDTO){
        roleService.updateRole(roleDTO);
        return Result.success();
    }

    @Operation(summary = "assign menu")
    @PostMapping("/assignMenu")
    public Result<Void> assignMenu (@RequestParam Long roleId, @RequestParam List<Long> menuIdList){
        roleService.assignMenu(roleId, menuIdList);
        return Result.success();
    }

    @Operation(summary = "delete role")
    @DeleteMapping("/deleteRole/{id}")
    public Result<Void> deleteRole (@PathVariable Long id){
        roleService.deleteRole(id);
        return Result.success();
    }
}
