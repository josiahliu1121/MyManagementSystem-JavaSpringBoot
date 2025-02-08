package com.skytakeaway.server.controller;

import com.skytakeaway.common.constant.JwtClaimsConstant;
import com.skytakeaway.common.properties.JwtProperties;
import com.skytakeaway.common.result.PageResult;
import com.skytakeaway.common.result.Result;
import com.skytakeaway.common.utils.JwtUtils;
import com.skytakeaway.pojo.dto.AddUserDTO;
import com.skytakeaway.pojo.dto.AssignRoleDTO;
import com.skytakeaway.pojo.dto.LoginDTO;
import com.skytakeaway.pojo.dto.UpdateUserDTO;
import com.skytakeaway.pojo.entity.User;
import com.skytakeaway.pojo.vo.AssignedRoleVO;
import com.skytakeaway.pojo.vo.UserInfoVO;
import com.skytakeaway.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User")
@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserService userService;

    @Operation(summary = "login")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO){
        User user = userService.login(loginDTO);

        //generate jwt token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtils.generateJwt(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );

        return Result.success(token);
    }

    @Operation(summary = "user info")
    @GetMapping("/info")
    public Result<UserInfoVO> userInfo(){
        UserInfoVO userInfo = userService.getUserInfo();

        return Result.success(userInfo);
    }

    @Operation(summary = "page query")
    @GetMapping("/pageQuery/{pageNumber}/{pageSize}")
    public Result<PageResult<UserInfoVO>> pageQuery(@PathVariable int pageNumber, @PathVariable int pageSize, @RequestParam String userName){
        return Result.success(userService.pageQuery(pageNumber,pageSize,userName));
    }

    @Operation(summary = "add user")
    @PutMapping("/addUser")
    public Result<Void> addUser(@RequestBody AddUserDTO addUserDTO){
        userService.addUser(addUserDTO);
        return Result.success();
    }

    @Operation(summary = "update user")
    @PostMapping("/updateUser")
    public Result<Void> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        userService.updateUser(updateUserDTO);
        return Result.success();
    }

    @Operation(summary = "get assigned role")
    @GetMapping("/getAssignedRole/{id}")
    public Result<AssignedRoleVO> getAssignedRole (@PathVariable Long id){
        return Result.success(userService.getAssignedRole(id));
    }

    @Operation(summary = "assign role")
    @PostMapping("/assignRole")
    public Result<Void> assignRole (@RequestBody AssignRoleDTO assignRoleDTO){
        userService.assignRole(assignRoleDTO);
        return Result.success();
    }

    @Operation(summary = "batch delete user")
    @DeleteMapping("/batchDelete")
    public Result<Void> batchDelete(@RequestBody List<Long> ids){
        userService.batchDelete(ids);
        return Result.success();
    }
}
